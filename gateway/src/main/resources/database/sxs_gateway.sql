/*Source Database       : sxs_gateway*/

SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS `activeorders` (
    `activeorderId` varchar(50) NOT NULL,
    `ticker` varchar(10) NOT NULL,
    `clientId` varchar(36) NOT NULL,
    `date` varchar(30) NOT NULL,
    `quantity` int(10) NOT NULL,
    `type` varchar(4) NOT NULL,
    `executionPrice` double(10,3) NOT NULL,
    `status` varchar(10) NOT NULL,
    PRIMARY KEY (`activeorderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for listedcompanies
-- ----------------------------
CREATE TABLE IF NOT EXISTS `listedcompanies` (
    `ticker` varchar(10) NOT NULL,
    `name` varchar(30) NOT NULL,
    `currencyCode` varchar(10) NOT NULL,
    `currencySymbol` varchar(5) NOT NULL,
    PRIMARY KEY (`ticker`),
    UNIQUE KEY `ticker` (`ticker`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELETE FROM listedcompanies;

INSERT INTO `listedcompanies` VALUES ('ADBE', 'Adobe', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('AMZN', 'Amazon', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('BAC', 'Bank of America', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('BRK.A', 'Berkshire Hathaway', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('DGV', 'Digital value', 'EUR', '€');
INSERT INTO `listedcompanies` VALUES ('DND', 'Dye & Durham', 'CAD', 'CAD');
INSERT INTO `listedcompanies` VALUES ('FB', 'Facebook', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('GOOG', 'Google', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('HD', 'Home Depot', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('JNJ', 'Johnson & Johnson', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('JPM', 'JP Morgan', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('LMT', 'Lockheed Martin', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('MA', 'Mastercard', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('MSFT', 'Microsoft', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('MTY', 'MTY Food', 'CAD', 'CAD');
INSERT INTO `listedcompanies` VALUES ('NVDA', 'Nvidia', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('PG', 'Procter & Gamble', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('TSLA', 'Tesla', 'USD', '$');
INSERT INTO `listedcompanies` VALUES ('V', 'Visa', 'USD', '$');

