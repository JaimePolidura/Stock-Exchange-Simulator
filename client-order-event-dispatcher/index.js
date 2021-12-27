const express = require("express");
const app = express();
const http = require('http');
const ampq = require('amqplib/callback_api');

const socketIo = require('socket.io');
const server = http.createServer(app);

const queuesListener = [
    "sxs.executed-orders.client-order-event-dispatcher",
    "sxs.error-orders.client-order-event-dispatcher"
];

const io = socketIo(server, {
    origin: "*",
});

server.listen(4000, () => {
    console.log("Server listening on port 4000");
});

io.on('connection', socket => {
    console.log("connected id: " + socket.id);
});

ampq.connect('amqp://rabbitmq', (errorConnection, connection) => {
    if (errorConnection) throw errorConnection;

    console.log("connected to rabbitmq");

    connection.createChannel((errorChannel, channel) => {
        if (errorChannel) throw errorChannel;

        queuesListener.forEach(queue => {
            channel.consume(queue, message => {
                let messageToJSON = JSON.parse(message.content.toString());

                console.log("new message from exchange "+ messageToJSON.to  +": " + message.content.toString());

                messageToJSON.to.forEach(to => {
                    io.emit(to, messageToJSON);
                });
            });
        });
    });
});
