# Stock-Exchange-Simulator
Fictitious stock market simulator where users can place buy or sell orders through a frontend. These orders are sent to the backend and then it tries to match them with other orders. If a match is found, the trade is executed and then, users are notified.

## How it works 
### Gateway
- The gateway exposes an HTTP REST API made in SpringBoot that users can use to place buy, sell or cancel orders.
- When a user places an order, the order is written in a MySQL database (just in case the exchange fails, we know what orders are still pending execution) and then it is published in a RabbitMQ queue.
- There will be one queue for every security that can be traded, for exmaple one queue for Amazon and another one for Google.

### Exchange
- For every security that can be traded, there will be at least one instance of a exchange.
- These instances can be automaticly created at gateway boot time using the kubernetes or docker-compose API.
- The exchange listens for orders that are being published in a specific RabbitMQ queue. Once it recieves an order, it tries to match them with other orders. If a match is found, the trade is executed, and a message indicating that the trade has been executed is published in a RabbitMQ queue.
