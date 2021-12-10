import {io} from "socket.io-client";

class SocketService {
    constructor() {
        this.connected = false;
        this.socket = null;
    }

    connect(onConnect){
         this.socket = io('http://localhost:4000', { transports : ['websocket'] });
    }

    onDataRecieved(){
        //TODO
    }
}

export default new SocketService();
