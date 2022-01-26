const axios = require('axios');
const express = require("express");
const app = express();
const http = require('http');
const ampq = require('amqplib/callback_api');

const socketIo = require('socket.io');
const server = http.createServer(app);

const queuesListener: string[] = [
    "sxs.exchange.events.client-order-event-dispatcher.order-error",
    "sxs.exchange.events.client-order-event-dispatcher.order-executed-buy",
    "sxs.exchange.events.client-order-event-dispatcher.order-executed-sell",
    "sxs.exchange.events.client-order-event-dispatcher.order-cancelled",
];

const gateway: string = "http://gateway:8080";

const io = socketIo(server, {
    origin: "*",
});

server.listen(4000, () => {
    console.log("Server listening on port 4000");
});

io.on('connection', socket => {
    console.log("connected id: " + socket.id);
});

io.use((socket, next) => {
    const username: string = socket.handshake.auth.username;
    const token: string = socket.handshake.auth.token;

    console.log(username);
    console.log(token);

    authenticate(socket, username, token);

    next();
});

const authenticate = (socket, username: string, token: string): void => {
    axios.get(`${gateway}/auth/isvalidtoken?username=${username}&token=${token}`)
        .then(res => {if (res.data == false) closeSocketConnection(socket)})
        .catch(err => closeSocketConnection(socket));
}

const closeSocketConnection = (socket): void => socket.disconnect(0);

ampq.connect('amqp://rabbitmq', (errorConnection, connection) => {
    if (errorConnection) throw errorConnection;

    console.log("connected to rabbitmq");

    connection.createChannel((errorChannel, channel) => {
        if (errorChannel) throw errorChannel;

        queuesListener.forEach(queue => {
            channel.consume(queue, message => {
                let messageToJSON: any = JSON.parse(message.content.toString());

                console.log("new message from exchange : " + message.content.toString());

                messageToJSON.meta.to.forEach(to => {
                    console.log(to);

                    io.emit(to, messageToJSON);
                });
            });
        });
    });
});
