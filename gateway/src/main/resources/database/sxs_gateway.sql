/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : sxs_gateway

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2021-11-20 18:12:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activeorders
-- ----------------------------
CREATE TABLE IF NOT EXISTS `activeorders` (
  `activeorderId` varchar(36) NOT NULL,
  `clientId` varchar(36) NOT NULL,
  `date` varchar(15) NOT NULL,
  `quantity` int(10) NOT NULL,
  `type` varchar(4) NOT NULL,
  `executionType` varchar(6) NOT NULL,
  `executionPrice` double(10,3) NOT NULL,
  PRIMARY KEY (`activeorderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of activeorders
-- ----------------------------

-- ----------------------------
-- Table structure for listedcompanies
-- ----------------------------
CREATE TABLE IF NOT EXISTS `listedcompanies` (
  `ticker` varchar(10) NOT NULL,
  PRIMARY KEY (`ticker`),
  UNIQUE KEY `ticker` (`ticker`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of listedcompanies
-- ----------------------------
# INSERT INTO `listedcompanies` VALUES ('ADBE');
# INSERT INTO `listedcompanies` VALUES ('AMZN');
# INSERT INTO `listedcompanies` VALUES ('BAC');
# INSERT INTO `listedcompanies` VALUES ('BRK.A');
# INSERT INTO `listedcompanies` VALUES ('FB');
# INSERT INTO `listedcompanies` VALUES ('GOOG');
# INSERT INTO `listedcompanies` VALUES ('HD');
# INSERT INTO `listedcompanies` VALUES ('JNJ');
# INSERT INTO `listedcompanies` VALUES ('JPM');
# INSERT INTO `listedcompanies` VALUES ('MA');
# INSERT INTO `listedcompanies` VALUES ('MSFT');
# INSERT INTO `listedcompanies` VALUES ('NVDA');
# INSERT INTO `listedcompanies` VALUES ('PG');
# INSERT INTO `listedcompanies` VALUES ('TSLA');
# INSERT INTO `listedcompanies` VALUES ('V');
