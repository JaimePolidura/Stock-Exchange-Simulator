const ampq = require('amqplib/callback_api');

const queueIp = "rabbitmq";
const queuesToListen: string[] = [
    "sxs.events.order-error.client-order-event-dispatcher",
    "sxs.events.order-executed-buy.client-order-event-dispatcher",
    "sxs.events.order-executed-sell.client-order-event-dispatcher",
    "sxs.events.order-cancelled.client-order-event-dispatcher",
];

class QueueSubscriber {
    //Username -> callback on new message
    private readonly subscribers: { [username: string]: ((data: any) => void) };

    constructor() {
        this.subscribers = {};
        this.startListeningToQueues();
    }

    private startListeningToQueues(): void {
        ampq.connect("amqp://" + queueIp, (errorConnection, connection) => {
            console.log("connected to queue");

            connection.createChannel((errorChannel, channel) => {
                if (errorChannel) throw errorChannel;

                queuesToListen.forEach(queue => this.listenForQueue(channel, queue));
            });
        });
    }

    private listenForQueue(channel, queue: string): void {
        channel.consume(queue, message => {
            let messageToJSON: any = JSON.parse(message.content.toString());

            messageToJSON.meta.to.forEach(to => {
                let messageToJSON: any = JSON.parse(message.content.toString());

                this.subscribers[to](messageToJSON);
            });
        });
    }

    subscribe(username: string, callback: ((data: any) => void)): void {
        this.subscribers[username] = callback;
    }
}

export default new QueueSubscriber();