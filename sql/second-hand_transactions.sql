/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : second-hand_transactions

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2020-02-03 15:34:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_time` varchar(255) NOT NULL,
  `log_des` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `l_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `message_create_time` varchar(255) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `details` varchar(255) NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `m_shop_id` (`shop_id`),
  KEY `m_user_id` (`user_id`),
  CONSTRAINT `m_shop_id` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`),
  CONSTRAINT `m_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_person` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_orders
-- ----------------------------
DROP TABLE IF EXISTS `tb_orders`;
CREATE TABLE `tb_orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_create_time` varchar(255) NOT NULL,
  `order_status` int(11) NOT NULL,
  `shop_status` int(11) NOT NULL,
  `order_number` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `buyer_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `owner_id` (`owner_id`),
  KEY `buyer_id` (`buyer_id`),
  KEY `shop_id` (`shop_id`),
  CONSTRAINT `buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `tb_person` (`user_id`),
  CONSTRAINT `owner_id` FOREIGN KEY (`owner_id`) REFERENCES `tb_person` (`user_id`),
  CONSTRAINT `shop_id` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_person
-- ----------------------------
DROP TABLE IF EXISTS `tb_person`;
CREATE TABLE `tb_person` (
  `user_id` int(255) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `pass_word` varchar(255) NOT NULL,
  `priority` int(255) NOT NULL,
  `true_name` varchar(255) NOT NULL,
  `money` int(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `addr` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(255) NOT NULL,
  `shop_img` varchar(255) DEFAULT NULL,
  `shop_des` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `put_away_time` varchar(255) DEFAULT NULL,
  `sold_out_time` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `shop_status` int(11) NOT NULL,
  `counts` int(11) unsigned zerofill NOT NULL,
  `shop_number` int(11) unsigned zerofill NOT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `type_id` FOREIGN KEY (`type_id`) REFERENCES `tb_shop_type` (`type_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_person` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_shop_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_type`;
CREATE TABLE `tb_shop_type` (
  `type` varchar(255) NOT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_waste_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_waste_book`;
CREATE TABLE `tb_waste_book` (
  `waste_id` int(11) NOT NULL AUTO_INCREMENT,
  `exchange_hour` varchar(255) NOT NULL,
  `waste_status` int(11) NOT NULL,
  `waste_price` int(11) NOT NULL,
  `waste_des` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`waste_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
