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
-- Table structure for trades
-- ----------------------------

CREATE TABLE IF NOT EXISTS trades (
    `tradeId` varchar(50) NOT NULL,
    `clientId` varchar(50) NOT NULL,
    `ticker` varchar(10) NOT NULL,
    `openingPrice` double(10, 3) NOT NULL,
    `openingDate` varchar(30) NOT NULL,
    `quantity` int(10) NOT NULL,
    PRIMARY KEY (`tradeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELETE FROM trades;

INSERT INTO `trades` VALUES ('9bdec8bd-253e-4b89-9c53-aa7718276e0a', 'jaime', 'AMZN', 2351.1, '2016-12-01T01:02:03', 4);
INSERT INTO `trades` VALUES ('177fe75a-4fa8-11ec-81d3-0242ac130003', 'jaime', 'V', 92.1, '2016-12-01T01:02:03', 34);
INSERT INTO `trades` VALUES ('1eb226fa-4fa8-11ec-81d3-0242ac130003', 'jaime', 'FB', 123, '2016-12-01T01:02:03', 55);
INSERT INTO `trades` VALUES ('26719a9c-4fa8-11ec-81d3-0242ac130003', 'jaime', 'ADBE', 86, '2016-12-01T01:02:03', 15);
INSERT INTO `trades` VALUES ('2be271ae-4fa8-11ec-81d3-0242ac130003', 'jaime', 'GOOG', 2015.1, '2016-12-01T01:02:03', 5);
INSERT INTO `trades` VALUES ('b3d55dd4-503d-11ec-bf63-0242ac130002', 'jaime', 'LMT', 374, '2016-12-01T01:02:03', 11);
INSERT INTO `trades` VALUES ('34b2204a-4fa8-11ec-81d3-0212ac130003', 'jaime', 'IDT', 15.4, '2016-10-01T01:02:03', 23);

INSERT INTO `trades` VALUES ('3bdec8bd-252e-4b89-9c53-aa7718276e0a', 'juan', 'AMZN', 2351.1, '2016-12-01T01:02:03', 4);
INSERT INTO `trades` VALUES ('127fe75a-41a8-1hej-81d3-0242ac130003', 'juan', 'V', 92.1, '2016-12-01T01:02:03', 34);
INSERT INTO `trades` VALUES ('1fb226fa-ffa8-a1ec-81d3-0242ac130003', 'juan', 'FB', 123, '2016-12-01T01:02:03', 55);
INSERT INTO `trades` VALUES ('26749a9c-gfa8-12dc-81d3-0242ac130003', 'juan', 'ADBE', 86, '2016-12-01T01:02:03', 15);
INSERT INTO `trades` VALUES ('2be271ae-7fa8-15ed-81d3-0242ac130003', 'juan', 'GOOG', 2015.1, '2016-12-01T01:02:03', 5);
INSERT INTO `trades` VALUES ('b3d55dd4-a03d-61wc-bf63-0242ac130002', 'juan', 'LMT', 374, '2016-12-01T01:02:03', 11);
INSERT INTO `trades` VALUES ('34f2204a-4fa8-11ec-81d3-0212ac130003', 'juan', 'IDT', 15.4, '2016-10-01T01:02:03', 23);

-- -------------------------------------
-- Table structure for closedpositions
-- -------------------------------------

CREATE TABLE IF NOT EXISTS closedpositions(
    `closedPositionId` varchar(50) NOT NULL,
    `clientId` varchar(50) NOT NULL,
    `ticker` varchar(10) NOT NULL,
    `quantity` int(10) NOT NULL,
    `openingPrice` double(10, 3) NOT NULL,
    `openingDate` varchar(30) NOT NULL,
    `closingPrice` double(10, 3) NOT NULL,
    `closingDate` varchar(30) NOT NULL,
    PRIMARY KEY (`closedPositionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELETE FROM closedpositions;

INSERT INTO closedpositions VALUES ('34f2104a-4ff8-11ec-81d3-0211ac130003', 'jaime', 'AMZN', 4, 131, '12/12/12', 2312, '12/12/20');
INSERT INTO closedpositions VALUES ('54f2f04a-4ff8-11ec-81d3-0211ac130003', 'jaime', 'GOOG', 1, 131, '12/12/12', 2312, '12/12/20');

-- ----------------------------
-- Table structure for orders
-- ----------------------------

CREATE TABLE IF NOT EXISTS `orders` (
    `orderId` varchar(50) NOT NULL,
    `clientId` varchar(36) NOT NULL,
    `date` varchar(30) NOT NULL,
    `orderTypeId` int(2) NOT NULL ,
    `body` varchar(200) NOT NULL,
    PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELETE FROM orders;

-- -------------------------------------
-- Table structure for ordertypes
-- -------------------------------------

CREATE TABLE IF NOT EXISTS ordertypes(
    `orderTypeId` int(2) NOT NULL,
    `name` varchar(10) NOT NULL,
    PRIMARY KEY (`orderTypeId`),
    UNIQUE KEY `name` (`orderTypeId`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELETE FROM ordertypes;

INSERT INTO ordertypes VALUES (1, 'BUY');
INSERT INTO ordertypes VALUES (2, 'SELL');
