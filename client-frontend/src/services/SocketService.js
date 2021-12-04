const socketServerURL = 'http://localhost:8080/socket';

import SockJS from 'sockjs-client';

class SocketService {
    constructor() {
        this.connected = false;
        this.stompClient = null;
    }
    
    connect(url, onConnect, onNotification){
        if(this.connected){
            return;
        }

        const ws = new SockJS(socketServerURL);
        this.stompClient = Stomp.over(ws);
        const that = this;

        this.stompClient.connect({}, function(frame) {
            onConnect();

            that.connected = true;
            that.stompClient.subscribe(url_subcribe, (message) => {
                onNotification(message);
            });
        });
    }

    disconnect(){
        this.stompClient.disconnect();
    }
}

export default new SocketService();
