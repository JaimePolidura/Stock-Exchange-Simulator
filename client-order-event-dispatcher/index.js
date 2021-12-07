const express = require("express");
const app = express();
const http = require('http');

const socketIo = require('socket.io');
const server = http.createServer(app);

const io = socketIo(server, {
    origin: "*",
});

io.on('connection', socket => {
    console.log("connected");
});

server.listen(4000, () => {
    console.log("Server listening on port 4000");
});
