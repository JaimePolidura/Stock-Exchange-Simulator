
class SocketService {
    constructor() {
        this.connected = false;
    }

    connect(url, onConnect, onNotification){
        // const serverUrl = 'http://localhost:8080/socket';
        // const ws = new SockJS(serverUrl);
        // this.stompClient = Stomp.over(ws);
        // const that = this;
        //
        // this.stompClient.connect({}, function(frame) {
        //     that.stompClient.subscribe(url_subcribe, (message) => {
        //         onNotification(message);
        //     });
        // });

    }
}

export default new SocketService();
