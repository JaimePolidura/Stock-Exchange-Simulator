import queueSubscriber from "./QueueSubscriber";

const axios = require('axios');
const express = require("express");
const app = express();
const http = require('http');
const cors = require('cors');
const server = http.createServer(app);

const gateway = "http://gateway:8080";
const websiteOrigin = "http://localhost:3000";

app.use(cors({ origin : websiteOrigin } ));

server.listen(4000, () => {
    console.log("Server listening on port 4000");
});

app.get('/events', (request, response) => {
    const username = request.query.username;
    const token = request.query.token;

    if(username == undefined || token == undefined){
        returnError(response);
        return;
    }
    authenticate(response, username, token);

    response.setHeader('Content-Type', 'text/event-stream');
    response.setHeader('connection', 'keep-alive');
    response.setHeader('Cache-Control', 'no-cache');

    queueSubscriber.subscribe(username, message => {
        const dataToSend = `data: ${JSON.stringify(message)}\n\n`;

        response.write(dataToSend);
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