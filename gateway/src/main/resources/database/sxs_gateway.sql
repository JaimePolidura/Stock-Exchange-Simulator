/*Source Database       : sxs_gateway*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------

CREATE TABLE IF NOT EXISTS `orders` (
    `orderId` varchar(50) NOT NULL,
    `ticker` varchar(10) NOT NULL,
    `clientId` varchar(36) NOT NULL,
    `date` varchar(30) NOT NULL,
    `quantity` int(10) NOT NULL,
    `type` varchar(4) NOT NULL,
    `executionPrice` double(10,3) NOT NULL,
    PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELETE FROM orders;

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