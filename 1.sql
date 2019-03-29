/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost
 Source Database       : customer

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : utf-8

 Date: 03/21/2019 18:30:28 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `customers`
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(10) DEFAULT NULL,
	`phone` varchar(20) DEFAULT NULL,
	`city` varchar(20) DEFAULT NULL,
	`address` varchar(50) DEFAULT NULL,
	`company` varchar(50) DEFAULT NULL,
	`createTime` datetime DEFAULT NULL,
	`updateTime` datetime DEFAULT NULL,
	`remarks` varchar(200) DEFAULT NULL,
	`createUser` varchar(10) DEFAULT NULL,
	`isVip` varchar(10) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=43 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ROW_FORMAT=DYNAMIC COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `customers`
-- ----------------------------
BEGIN;
INSERT INTO `customers` VALUES ('29', '111111', '1', '1', '1', '1', '2019-03-19 15:18:55', '2019-03-19 15:21:51', '1', 'ws', '0'), ('30', '111', '1', '1', '1', '1', '2019-03-19 15:21:27', '2019-03-19 15:21:27', '1', 'ws', '0'), ('31', '说eeee111', '1e', '1', '1', '1', '2019-03-19 16:32:33', '2019-03-19 17:54:10', '1', 'ws', '0'), ('32', 'wf', '11', '111111', '11', '11', '2019-03-20 09:25:29', '2019-03-20 09:25:29', '11', 'ws', '0'), ('33', 'www', '121', 'w', '12', '12', '2019-03-20 09:25:46', '2019-03-20 09:25:46', '1212', 'ws', '0'), ('34', '2222', '2', '2', '2', '2', '2019-03-20 09:25:55', '2019-03-20 09:25:55', '22', 'ws', '0'), ('35', 'ss', '1', '1', '1', '1', '2019-03-20 09:26:04', '2019-03-20 09:26:04', '1', 'ws', '0'), ('36', 'wfw', '12', 'ww', '111', '1', '2019-03-20 09:26:17', '2019-03-20 09:26:17', '1', 'ws', '0'), ('37', 's', '21111', '12', '11', '1', '2019-03-20 09:26:29', '2019-03-20 09:26:29', 's', 'ws', '0'), ('38', '11', '11', '1', '1', '1', '2019-03-20 09:26:40', '2019-03-20 09:26:40', '1', 'ws', '0'), ('39', '11122212', '1', '1', '1', '1', '2019-03-20 09:26:51', '2019-03-20 17:27:28', '1', 'ws', '0'), ('40', '11ssssss', '1', '1qwww', '1', '1', '2019-03-20 09:27:02', '2008-08-08 22:23:01', '1', 'ws', '0'), ('41', 'wf', '12', 'e', '1', '1', '2019-03-20 17:35:33', '2019-03-20 17:36:04', '1', 'ws', '0'), ('42', 'z', '', 'z', '', '', '2019-03-21 11:05:04', '2019-03-21 11:05:04', '', 'ws', '0');
COMMIT;

-- ----------------------------
--  Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
	`orderTime` datetime NOT NULL,
	`product` varchar(30) DEFAULT NULL,
	`type` varchar(50) DEFAULT NULL,
	`area` float(32,0) DEFAULT NULL,
	`price` float(32,0) DEFAULT NULL,
	`totalAmount` float(32,0) DEFAULT NULL,
	`remarks` varchar(200) DEFAULT NULL,
	`id` int(20) NOT NULL AUTO_INCREMENT,
	`customerId` int(11) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=16 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ROW_FORMAT=DYNAMIC COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `orders`
-- ----------------------------
BEGIN;
INSERT INTO `orders` VALUES ('2019-03-19 15:25:32', '1', '1', '1', '1', '1', '1', '11', '29'), ('2019-03-19 16:34:29', 'c', 'c', '1', '1', '1', '1', '12', '31'), ('2019-03-19 21:09:26', 's', '1', '1', '1', '1', '1', '13', '31'), ('2019-03-20 16:30:13', '1', '1', '1', '1', '1', '1', '14', '40'), ('2019-03-20 17:44:27', 'q1', '1', '1', '1', '1', '1', '15', '41');
COMMIT;

-- ----------------------------
--  Table structure for `records`
-- ----------------------------
DROP TABLE IF EXISTS `records`;
CREATE TABLE `records` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`customerId` int(11) DEFAULT NULL,
	`content` varchar(200) DEFAULT NULL,
	`createTime` datetime DEFAULT NULL,
	`createUser` varchar(20) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=85 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ROW_FORMAT=DYNAMIC COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `records`
-- ----------------------------
BEGIN;
INSERT INTO `records` VALUES ('60', '29', '我中小城镇', '2019-03-19 15:25:12', 'ws'), ('61', '29', '长春', '2019-03-19 15:25:21', 'ws'), ('62', '29', 'd ', '2019-03-19 15:26:01', 'ws'), ('63', '31', '行', '2019-03-19 16:34:18', 'ws'), ('64', '31', '你', '2019-03-19 16:35:18', 'ws'), ('65', '32', 'a', '2019-03-19 20:24:59', 'ws'), ('66', '40', 'qq', '2019-03-20 09:27:27', 'ws'), ('67', '40', 'w', '2019-03-20 09:30:53', 'ws'), ('68', '40', 'x', '2019-03-20 09:31:39', 'ws'), ('69', '40', 's', '2019-03-20 09:32:29', 'ws'), ('70', '40', '次', '2019-03-20 16:21:05', 'ws'), ('71', '40', '的', '2019-03-20 16:21:13', 'ws'), ('72', '40', 'e', '2019-03-20 16:21:19', 'ws'), ('73', '40', '嗯嗯', '2019-03-20 16:21:26', 'ws'), ('74', '40', 'd ', '2019-03-20 16:21:30', 'ws'), ('75', '40', '  的', '2019-03-20 16:21:37', 'ws'), ('76', '40', '的', '2019-03-20 16:21:47', 'ws'), ('77', '40', 's ', '2019-03-20 16:58:37', 'ws'), ('78', '40', 'e ', '2019-03-20 17:10:51', 'ws'), ('79', '40', 'd', '2019-03-20 17:14:33', 'ws'), ('80', '39', 'c ', '2019-03-20 17:17:24', 'ws'), ('81', '39', '3', '2019-03-20 17:21:16', 'ws'), ('82', '39', 'w', '2019-03-20 17:22:18', 'ws'), ('83', '39', '22', '2019-03-20 17:27:29', 'ws'), ('84', '41', 'sc', '2019-03-20 17:36:04', 'ws');
COMMIT;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`account` varchar(20) DEFAULT NULL,
	`name` varchar(20) DEFAULT NULL,
	`password` varchar(50) DEFAULT NULL,
	`is_admin` varchar(10) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=3 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ROW_FORMAT=DYNAMIC COMMENT='' CHECKSUM=0 DELAY_KEY_WRITE=0;

-- ----------------------------
--  Records of `users`
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('1', 'wf', 'ws', 'e10adc3949ba59abbe56e057f20f883e', '1'), ('2', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
