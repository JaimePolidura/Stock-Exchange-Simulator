const axios = require('axios');
const express = require("express");
const app = express();
const http = require('http');
const ampq = require('amqplib/callback_api');
const cors = require('cors');
const server = http.createServer(app);

app.use(cors({ origin : '*' } ));

const gateway = "http://gateway:8080";

const queuesListener: string[] = [
    "sxs.events.order-error.client-order-event-dispatcher",
    "sxs.events.order-executed-buy.client-order-event-dispatcher",
    "sxs.events.order-executed-sell.client-order-event-dispatcher",
    "sxs.events.order-cancelled.client-order-event-dispatcher",
];


server.listen(4000, () => {
    console.log("Server listening on port 4000");
});

app.get('/events', (request, response, next) => {
    const username = request.query.username;
    const token = request.query.token;

    if(username == undefined || token == undefined){
        returnError(response);
    }
    authenticate(response, username, token);

    response.setHeader('Content-Type', 'text/event-stream');

    ampq.connect('amqp://rabbitmq', (errorConnection, connection) => {
        if (errorConnection) throw errorConnection;

        console.log("connected to rabbitmq");

        connection.createChannel((errorChannel, channel) => {
            if (errorChannel) throw errorChannel;

            queuesListener.forEach(queue => {
                channel.consume(queue, message => {
                    let messageToJSON: any = JSON.parse(message.content.toString());

                    console.log("new message from exchange : " + messageToJSON);

                    messageToJSON.meta.to.forEach(to => {
                        if(to == username){
                            console.log(to);

                            const data = `data: ${JSON.stringify(messageToJSON)}\n\n`;
                            response.write(data);
                        }
                    });
                });
            });
        });
    });
});

const authenticate = (response, username: string, token: string): void => {
    axios.get(`${gateway}/auth/isvalidtoken?username=${username}&token=${token}`)
        .then(res => {if (res.data == false) returnError(response)})
        .catch(err => returnError(response));
}

const returnError = response => {
    response.status(403);
    response.send("Invalid format");
    response.end();
}

// io.on('connection', socket => {
//     console.log("connected id: " + socket.id);
// });
//
// io.use((socket, next) => {
//     const username: string = socket.handshake.auth.username;
//     const token: string = socket.handshake.auth.token;
//
//     authenticate(socket, username, token);
//
//     next();
// });
//
// const authenticate = (socket, username: string, token: string): void => {
//     axios.get(`${gateway}/auth/isvalidtoken?username=${username}&token=${token}`)
//         .then(res => {if (res.data == false) closeSocketConnection(socket)})
//         .catch(err => closeSocketConnection(socket));
// }
//
// const closeSocketConnection = (socket): void => socket.disconnect(0);
//
// ampq.connect('amqp://rabbitmq', (errorConnection, connection) => {
//     if (errorConnection) throw errorConnection;
//
//     console.log("connected to rabbitmq");
//
//     connection.createChannel((errorChannel, channel) => {
//         if (errorChannel) throw errorChannel;
//
//         queuesListener.forEach(queue => {
//             channel.consume(queue, message => {
//                 let messageToJSON: any = JSON.parse(message.content.toString());
//
//                 console.log("new message from exchange : " + message.content.toString());
//
//                 messageToJSON.meta.to.forEach(to => {
//                     console.log(to);
//
//                     io.emit(to, messageToJSON);
//                 });
//             });
//         });
//     });
// });
