const axios = require('axios').default;
const express = require("express");
const app = express();
const http = require('http');
const ampq = require('amqplib/callback_api');

const socketIo = require('socket.io');
const server = http.createServer(app);

const queuesListener = [
    "sxs.exchange.events.client-order-event-dispatcher.order-error",
    "sxs.exchange.events.client-order-event-dispatcher.order-executed-buy",
    "sxs.exchange.events.client-order-event-dispatcher.order-executed-sell",
    "sxs.exchange.events.client-order-event-dispatcher.order-cancelled",
];

const gateway = "http://localhost:8080";

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
    const username = socket.handshake.auth.username;
    const token = socket.handshake.auth.token;

    authenticate(socket, username, token);

    next();
});

const authenticate = (socket, username, token) => {
    axios.get(`http://gateway:8080/auth/isvalidtoken?username=${username}&token=${token}`)
        .then(res => {if (res.data == false) {closeSocketConnection(socket)}})
        .catch(err => closeSocketConnection(socket));
}

const closeSocketConnection = socket => {
    console.log("porro");

    socket.disconnect(0);
}

ampq.connect('amqp://rabbitmq', (errorConnection, connection) => {
    if (errorConnection) throw errorConnection;

    console.log("connected to rabbitmq");

    connection.createChannel((errorChannel, channel) => {
        if (errorChannel) throw errorChannel;

        queuesListener.forEach(queue => {
            channel.consume(queue, message => {
                let messageToJSON = JSON.parse(message.content.toString());

                console.log("new message from exchange "+ messageToJSON.to  +": " + message.content.toString());

                messageToJSON.meta.to.forEach(to => {
                    io.emit(to, messageToJSON);
                });
            });
        });
    });
});
