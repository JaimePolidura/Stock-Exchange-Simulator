/*Source Database       : sxs_gateway*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for listedcompanies
-- ----------------------------
DROP TABLE IF EXISTS `listedcompanies`;
CREATE TABLE `listedcompanies` (
    `ticker` varchar(10) NOT NULL,
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`ticker`),
    UNIQUE KEY `ticker` (`ticker`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `listedcompanies` VALUES ('ADBE', 'Adobe');
INSERT INTO `listedcompanies` VALUES ('AMZN', 'Amazon');
INSERT INTO `listedcompanies` VALUES ('FB', 'Facebook');
INSERT INTO `listedcompanies` VALUES ('GOOG', 'Google');
INSERT INTO `listedcompanies` VALUES ('LMT', 'Lockheed Martin');
INSERT INTO `listedcompanies` VALUES ('V', 'Visa');
INSERT INTO `listedcompanies` VALUES ('IDT', 'IDT');
INSERT INTO `listedcompanies` VALUES ('CRM', 'SalesForce');

-- ----------------------------
-- Table structure for orders
-- ----------------------------

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
    `orderId` varchar(50) NOT NULL,
    `clientId` varchar(50) NOT NULL,
    `date` varchar(30) NOT NULL,
    `state` enum('PENDING', 'EXECUTED', 'CANCELLED', 'ERROR') NOT NULL,
    `ticker` varchar(10) NOT NULL ,

    `executedOrderType` enum('OPEN', 'CLOSED'),
    `openingPrice` double(10, 3),
    `openingDate` varchar(30),
    `closingPrice` double(10, 3),
    `closingDate` varchar(30),

    `pendingOrderType` enum('BUY', 'SELL', 'CANCEL'),
    `orderIdToCancel` varchar(50),
    `quantity` int(10),
    `priceToExecute` double(10, 3),
    `positionIdToSell` varchar(50),
    PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP VIEW IF EXISTS buy_orders;
CREATE VIEW buy_orders AS SELECT orderId, clientId, date, state, ticker, pendingOrderType, quantity, priceToExecute FROM orders WHERE pendingOrderType = 'BUY';

DROP VIEW IF EXISTS sell_orders;
CREATE VIEW sell_orders AS SELECT orderId, clientId, date, state, ticker, pendingOrderType, quantity, priceToExecute, positionIdToSell FROM orders WHERE pendingOrderType = 'SELL';

DROP VIEW IF EXISTS cancel_orders;
CREATE VIEW cancel_orders AS SELECT orderId, clientId, date, state, ticker, pendingOrderType, orderIdToCancel FROM orders WHERE pendingOrderType = 'CANCEL';

DROP VIEW IF EXISTS closed_positions;
CREATE VIEW closed_positions AS SELECT orderId, clientId, date, state, ticker, quantity, executedOrderType, openingPrice, openingDate, closingPrice, closingDate FROM orders WHERE executedOrderType = 'CLOSED';

DROP VIEW IF EXISTS open_positions;
CREATE VIEW open_positions AS SELECT orderId, clientId, date, state, ticker, quantity, executedOrderType, openingPrice, openingDate FROM orders WHERE executedOrderType = 'OPEN';

# INSERT INTO closed_positions (orderId, clientId, date, state, ticker, quantity, executedOrderType, openingPrice, openingDate, closingPrice, closingDate)
# VALUES ('1', 'jaime', '2020-02-01T00:00', 'EXECUTED', 'GOOG', 1, 'CLOSED', 10, '2019-12-21T12:03', 12, '2020-02-01T00:00');
#
# INSERT INTO closed_positions (orderId, clientId, date, state, ticker, quantity, executedOrderType, openingPrice, openingDate, closingPrice, closingDate)
# VALUES ('2', 'jaime', '2020-02-01T00:00', 'EXECUTED', 'AMZN', 2, 'CLOSED', 10, '2018-02-01T00:00', 12, '2021-02-01T00:00');
#
# INSERT INTO closed_positions (orderId, clientId, date, state, ticker, quantity, executedOrderType, openingPrice, openingDate, closingPrice, closingDate)
# VALUES ('3', 'jaime', '2020-02-01T00:00', 'EXECUTED', 'IDT', 2, 'CLOSED', 10, '2015-02-01T00:00', 12, '2016-02-01T00:00');
#
# INSERT INTO open_positions (orderId, clientId, date, state, ticker, quantity, executedOrderType, openingPrice, openingDate)
# VALUES ('4', 'jaime', '2020-02-01T00:00', 'EXECUTED', 'IDT', 2, 'OPEN', 10, '2017-02-01T00:00');
#
# INSERT INTO open_positions (orderId, clientId, date, state, ticker, quantity, executedOrderType, openingPrice, openingDate)
# VALUES ('5', 'jaime', '2020-02-01T00:00', 'EXECUTED', 'IDT', 2, 'OPEN', 10, '2021-02-01T00:00');

# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('34f2104a-4ff8-11ec-81d3-0211ac130003', 'jaime', '2016-12-01T01:02:03', 'EXECUTED', 'AMZN', 4, 131, 'OPEN', '2016-12-01T01:02:03');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('171fe75a-4fa8-11ec-81d3-0242ac130003', 'jaime', '2016-12-01T01:02:03', 'EXECUTED', 'V', 34, 92.1, 'OPEN', '2016-12-01T01:02:02');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('427fe75a-4fa8-11ec-81d3-0242ac130003', 'jaime', '2016-12-01T01:02:03', 'EXECUTED', 'FB', 123, 234, 'OPEN', '2016-12-01T01:02:02');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('26749a9c-gfa8-12dc-81d3-0242ac130003', 'jaime', '2016-12-01T01:02:03', 'EXECUTED', 'ADBE', 15, 86, 'OPEN', '2016-12-01T01:02:02');
INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('2be271ae-7fa8-15ed-81d3-0242ac130003', 'jaime', '2016-12-01T01:02:03', 'EXECUTED', 'GOOG', 5, 2015.1, 'OPEN', '2016-12-01T01:02:2');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('b3d55dd4-a03d-61wc-bf63-0242ac130002', 'jaime', '2016-12-01T01:02:03', 'EXECUTED', 'LMT', 11, 374, 'OPEN', '2016-12-01T01:02:02');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('34f2204a-4fa8-11ec-81d3-0212ac130003', 'jaime', '2016-12-01T01:02:03', 'EXECUTED', 'IDT', 23, 15.4, 'OPEN', '2016-12-01T01:02:01');

# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('35f2104a-4ff8-11ec-81d3-0211ac130003', 'juan', '2016-12-01T01:02:03', 'EXECUTED', 'AMZN', 4, 131, 'OPEN', '2016-12-01T01:02:03');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('171fe75a-4fa8-11ec-81d3-0242ac120003', 'juan', '2016-12-01T01:02:03', 'EXECUTED', 'V', 34, 92.1, 'OPEN', '2016-12-01T01:02:03');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('427fe65a-4fa8-11ec-81d3-0242ac110003', 'juan', '2016-12-01T01:02:03', 'EXECUTED', 'FB', 123, 234, 'OPEN', '2016-12-01T01:02:03');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('26249a9c-gfa8-12dc-81d3-0242ac170003', 'juan', '2016-12-01T01:02:03', 'EXECUTED', 'ADBE', 15, 86, 'OPEN', '2016-12-01T01:02:03');
INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('3be271ae-7fa8-15ed-81d3-0242ac130003', 'juan', '2016-12-01T01:02:03', 'EXECUTED', 'GOOG', 5, 2015.1, 'OPEN', '2016-12-01T01:02:03');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('bhdd5dd4-a03d-61wc-bf63-0242ac130002', 'juan', '2016-12-01T01:02:03', 'EXECUTED', 'LMT', 11, 374, 'OPEN', '2016-12-01T01:02:03');
# INSERT INTO orders (orderId, clientId, date, state ,ticker, quantity, openingPrice, executedOrderType, openingDate) VALUES ('34f2214a-4fb8-11ec-81d3-0212ac130003', 'juan', '2016-12-01T01:02:03', 'EXECUTED', 'IDT', 23, 15.4, 'OPEN', '2016-12-01T01:02:03');