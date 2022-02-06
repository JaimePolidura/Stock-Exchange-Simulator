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

INSERT INTO listedcompanies (ticker, name) VALUES ('AVGO', 'Broadcom');
INSERT INTO listedcompanies (ticker, name) VALUES ('KO', 'Cocacola');
INSERT INTO listedcompanies (ticker, name) VALUES ('UNH', 'United health');
INSERT INTO listedcompanies (ticker, name) VALUES ('PG', 'Procter & Gamble');
INSERT INTO listedcompanies (ticker, name) VALUES ('BABA', 'Alibaba');
INSERT INTO listedcompanies (ticker, name) VALUES ('TMO', 'Thermo Fisher');
INSERT INTO listedcompanies (ticker, name) VALUES ('GOOG', 'Google');
INSERT INTO listedcompanies (ticker, name) VALUES ('CSCO', 'Cisco');
INSERT INTO listedcompanies (ticker, name) VALUES ('NKE', 'Nike');
INSERT INTO listedcompanies (ticker, name) VALUES ('XOM', 'Exxon mobile');
INSERT INTO listedcompanies (ticker, name) VALUES ('MA', 'Mastercard');
INSERT INTO listedcompanies (ticker, name) VALUES ('PFE', 'Pfizer');
INSERT INTO listedcompanies (ticker, name) VALUES ('MSFT', 'Microsoft');
INSERT INTO listedcompanies (ticker, name) VALUES ('BAC', 'Bank of America');
INSERT INTO listedcompanies (ticker, name) VALUES ('V', 'Visa');
INSERT INTO listedcompanies (ticker, name) VALUES ('ADBE', 'Adobe');
INSERT INTO listedcompanies (ticker, name) VALUES ('NVDIA', 'Nvdia');
INSERT INTO listedcompanies (ticker, name) VALUES ('PEP', 'Pepsi');
INSERT INTO listedcompanies (ticker, name) VALUES ('TSLA', 'Tesla');
INSERT INTO listedcompanies (ticker, name) VALUES ('CRM', 'Salesforce');
INSERT INTO listedcompanies (ticker, name) VALUES ('AMZN', 'Amazon');
INSERT INTO listedcompanies (ticker, name) VALUES ('HD', 'Home depot');
INSERT INTO listedcompanies (ticker, name) VALUES ('VZ', 'Verizon');
INSERT INTO listedcompanies (ticker, name) VALUES ('FB', 'Facebook');
INSERT INTO listedcompanies (ticker, name) VALUES ('CVX', 'Chevbron');
INSERT INTO listedcompanies (ticker, name) VALUES ('JNJ', 'Johnson & Johson');
INSERT INTO listedcompanies (ticker, name) VALUES ('AAPL', 'Apple');
INSERT INTO listedcompanies (ticker, name) VALUES ('WMT', 'Waltmart');
INSERT INTO listedcompanies (ticker, name) VALUES ('JPM', 'JP Morgan');

-- ----------------------------
-- Table structure for orders
-- ----------------------------

CREATE TABLE IF NOT EXISTS `orders` (
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