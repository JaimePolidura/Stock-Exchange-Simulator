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

INSERT INTO `trades` VALUES ('9bdec8bd-253e-4b89-9c53-aa7718276e0a', 'jaime', 'AMZN', 2351.1, '2016-12-01T01:02:03', 4);
INSERT INTO `trades` VALUES ('177fe75a-4fa8-11ec-81d3-0242ac130003', 'jaime', 'V', 92.1, '2016-12-01T01:02:03', 34);
INSERT INTO `trades` VALUES ('1eb226fa-4fa8-11ec-81d3-0242ac130003', 'jaime', 'FB', 123, '2016-12-01T01:02:03', 55);
INSERT INTO `trades` VALUES ('26719a9c-4fa8-11ec-81d3-0242ac130003', 'jaime', 'ADBE', 86, '2016-12-01T01:02:03', 15);
INSERT INTO `trades` VALUES ('2be271ae-4fa8-11ec-81d3-0242ac130003', 'jaime', 'GOOG', 2015.1, '2016-12-01T01:02:03', 5);
INSERT INTO `trades` VALUES ('66f73bkc-2gB8-51ec-81l3-1242dz130143', 'jaime', 'CRM', 150, '2016-12-01T01:02:03', 15);
INSERT INTO `trades` VALUES ('b3d55dd4-503d-11ec-bf63-0242ac130002', 'jaime', 'LMT', 374, '2016-12-01T01:02:03', 11);
INSERT INTO `trades` VALUES ('34b2204a-4fa8-11ec-81d3-0242ac130003', 'jaime', 'IDT', 6.31, '2016-12-01T01:02:03', 43);
INSERT INTO `trades` VALUES ('34b2204a-4fa8-11ec-81d3-0242ac230003', 'jaime', 'IDT', 12.3, '2016-12-01T01:02:03', 32);
INSERT INTO `trades` VALUES ('34b2204a-4fa8-11ec-81d3-0212ac130003', 'jaime', 'IDT', 15.4, '2016-12-01T01:02:03', 23);

INSERT INTO `trades` VALUES ('9bdec8bd-252e-4b89-9c53-aa7718276e0a', 'juan', 'AMZN', 2351.1, '2016-12-01T01:02:03', 4);
INSERT INTO `trades` VALUES ('177fe75a-41a8-11ec-81d3-0242ac130003', 'juan', 'V', 92.1, '2016-12-01T01:02:03', 34);
INSERT INTO `trades` VALUES ('1eb226fa-ffa8-11ec-81d3-0242ac130003', 'juan', 'FB', 123, '2016-12-01T01:02:03', 55);
INSERT INTO `trades` VALUES ('26719a9c-gfa8-11ec-81d3-0242ac130003', 'juan', 'ADBE', 86, '2016-12-01T01:02:03', 15);
INSERT INTO `trades` VALUES ('2be271ae-7fa8-11ec-81d3-0242ac130003', 'juan', 'GOOG', 2015.1, '2016-12-01T01:02:03', 5);
INSERT INTO `trades` VALUES ('66f73bkc-1gB8-51ec-81l3-1242dz130143', 'juan', 'CRM', 150, '2016-12-01T01:02:03', 15);
INSERT INTO `trades` VALUES ('b3d55dd4-a03d-11ec-bf63-0242ac130002', 'juan', 'LMT', 374, '2016-12-01T01:02:03', 11);
