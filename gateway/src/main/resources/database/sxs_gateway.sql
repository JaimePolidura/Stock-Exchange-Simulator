/*Source Database       : sxs_gateway*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for listedcompanies
-- ----------------------------
CREATE TABLE IF NOT EXISTS `listedcompanies` (
    `ticker` varchar(10) NOT NULL,
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`ticker`),
    UNIQUE KEY `ticker` (`ticker`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELETE FROM listedcompanies;

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
    `closingPrice` double(10, 3),
    `closingDate` varchar(30),

    `pendingOrderType` enum('BUY', 'SELL', 'CANCEL'),
    `orderIdToCancel` varchar(50),
    `quantity` int(10),
    `priceToExecute` double(10, 3),
    `positionIdToSell` varchar(50),
    PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP VIEW IF EXISTS open_positions;
CREATE VIEW open_positions AS SELECT orderId, clientId, ticker, quantity, openingPrice, date AS openingDate FROM orders WHERE executedOrderType = 'OPEN';

DROP VIEW IF EXISTS closed_positions;
CREATE VIEW closed_positions AS SELECT positionId, clientId, ticker, quantity, openingPrice, date AS openingDate, closingPrice, closingDate, status FROM orders WHERE executedOrderType = 'CLOSED';

DROP VIEW IF EXISTS buy_orders;
CREATE VIEW buy_orders AS SELECT orderId, clientId, date, type, state, ticker, quantity, priceToExecute FROM orders WHERE type = 'BUY';

DROP VIEW IF EXISTS sell_orders;
CREATE VIEW sell_orders AS SELECT orderId, clientId, date, type, state, ticker, quantity, priceToExecute, positionIdToSell FROM orders WHERE type = 'SELL';

DROP VIEW IF EXISTS cancel_orders;
CREATE VIEW cancel_orders AS SELECT orderId, clientId, date, type, state, ticker, orderIdToCancel FROM orders WHERE type = 'CANCEL';

INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('34f2104a-4ff8-11ec-81d3-0211ac130003', 'jaime', 'AMZN', 4, 131, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('171fe75a-4fa8-11ec-81d3-0242ac130003', 'jaime', 'V', 34, 92.1, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('427fe75a-4fa8-11ec-81d3-0242ac130003', 'jaime', 'FB', 123, 234, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('26749a9c-gfa8-12dc-81d3-0242ac130003', 'jaime', 'ADBE', 15, 86, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('2be271ae-7fa8-15ed-81d3-0242ac130003', 'jaime', 'GOOG', 5, 2015.1, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('b3d55dd4-a03d-61wc-bf63-0242ac130002', 'jaime', 'LMT', 11, 374, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('34f2204a-4fa8-11ec-81d3-0212ac130003', 'jaime', 'IDT', 23, 15.4, '2016-12-01T01:02:03', 'OPEN');

INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('35f2104a-4ff8-11ec-81d3-0211ac130003', 'juan', 'AMZN', 4, 131, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('171fe75a-4fa8-11ec-81d3-0242ac120003', 'juan', 'V', 34, 92.1, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('427fe65a-4fa8-11ec-81d3-0242ac110003', 'juan', 'FB', 123, 234, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('26249a9c-gfa8-12dc-81d3-0242ac170003', 'juan', 'ADBE', 15, 86, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('3be271ae-7fa8-15ed-81d3-0242ac130003', 'juan', 'GOOG', 5, 2015.1, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('bhdd5dd4-a03d-61wc-bf63-0242ac130002', 'juan', 'LMT', 11, 374, '2016-12-01T01:02:03', 'OPEN');
INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, status) VALUES ('34f2214a-4fb8-11ec-81d3-0212ac130003', 'juan', 'IDT', 23, 15.4, '2016-12-01T01:02:03', 'OPEN');

INSERT INTO positions (positionId, clientId, ticker, quantity, openingPrice, openingDate, closingPrice, closingDate, status) VALUES
    ('34f2104a-4ff8-11ec-81d4-0211ac130003', 'jaime', 'AMZN', 4, 131, '12/12/12', 2312, '12/12/20', 'CLOSED');
INSERT INTO positions VALUES ('34f2104a-4ff8-11ec-81d3-0211ac130002', 'juan', 'AMZN', 4, 131, '12/12/12', 2312, '12/12/20', 'CLOSED');