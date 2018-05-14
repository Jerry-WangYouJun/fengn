/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : cmtp

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2017-11-28 17:10:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for u_card_agent
-- ----------------------------
DROP TABLE IF EXISTS `u_card_agent`;
CREATE TABLE `u_card_agent` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `iccid` varchar(100) DEFAULT NULL,
  `agentid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid_index` (`iccid`)
) ENGINE=MyISAM AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_card_agent
-- ----------------------------
INSERT INTO `u_card_agent` VALUES ('40', '89860616010040477735', '19');
INSERT INTO `u_card_agent` VALUES ('41', '89860616010040477743', '14');
INSERT INTO `u_card_agent` VALUES ('42', '89860616010040477750', '14');
INSERT INTO `u_card_agent` VALUES ('43', '89860616010040477768', '14');
INSERT INTO `u_card_agent` VALUES ('44', '89860616010040477776', '14');
INSERT INTO `u_card_agent` VALUES ('45', '89860616010040477784', '14');
INSERT INTO `u_card_agent` VALUES ('46', '89860616010040477792', '14');
INSERT INTO `u_card_agent` VALUES ('47', '89860616010040477800', '14');
INSERT INTO `u_card_agent` VALUES ('48', '89860616010040477818', '14');
INSERT INTO `u_card_agent` VALUES ('49', '89860616010040477826', '14');
INSERT INTO `u_card_agent` VALUES ('50', '89860616010040477834', '14');
INSERT INTO `u_card_agent` VALUES ('51', '89860616010040477842', '14');
INSERT INTO `u_card_agent` VALUES ('52', '89860616010040477859', '14');

-- ----------------------------
-- Table structure for u_cmtp
-- ----------------------------
DROP TABLE IF EXISTS `u_cmtp`;
CREATE TABLE `u_cmtp` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `IMSI` varchar(50) DEFAULT NULL,
  `ICCID` varchar(50) DEFAULT NULL,
  `cardStatus` varchar(20) DEFAULT NULL COMMENT '卡状态',
  `gprsUsed` varchar(20) DEFAULT NULL COMMENT '使用流量',
  `gprsRest` varchar(20) DEFAULT NULL,
  `monthTotalStream` varchar(20) DEFAULT NULL COMMENT '总流量',
  `company` varchar(30) DEFAULT NULL COMMENT '公司名称',
  `company_level` varchar(20) DEFAULT NULL COMMENT '公司等级',
  `withGPRSService` varchar(1) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL COMMENT '套餐名',
  `packageDetail` varchar(80) DEFAULT NULL COMMENT '套餐描述',
  `updateTime` varchar(30) DEFAULT NULL COMMENT '开卡时间、修改时间',
  `orderStatus` varchar(10) DEFAULT NULL COMMENT '充值状态（根据操作修改订单状态，判断跳转链接）',
  `deadline` varchar(10) DEFAULT NULL COMMENT '剩余时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_C` (`ICCID`) USING BTREE,
  KEY `index_id` (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of u_cmtp
-- ----------------------------
INSERT INTO `u_cmtp` VALUES ('17', '', '861064617220041', '89860616010040477735', '正常', '0.000MB', '29.581MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('18', '', '861064617220042', '89860616010040477743', '正常', '0.000MB', '28.268MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('19', '', '861064617220043', '89860616010040477750', '正常', '0.000MB', '29.998MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('20', '', '861064617220044', '89860616010040477768', '正常', '0.000MB', '29.567MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('21', '', '861064617220045', '89860616010040477776', '正常', '0.000MB', '29.635MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('22', '', '861064617220046', '89860616010040477784', '正常', '0.000MB', '29.807MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('23', '', '861064617220047', '89860616010040477792', '正常', '0.000MB', '29.657MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('24', '', '861064617220048', '89860616010040477800', '正常', '0.000MB', '29.788MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('25', '', '861064617220049', '89860616010040477818', '正常', '0.000MB', '29.791MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('26', '', '861064617220050', '89860616010040477826', '正常', '0.000MB', '29.627MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('27', '', '861064617220051', '89860616010040477834', '正常', '0.000MB', '29.891MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('28', '', '861064617220052', '89860616010040477842', '正常', '0.000MB', '29.562MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp` VALUES ('29', '', '861064617220053', '89860616010040477859', '正常', '0.000MB', '29.356MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');

-- ----------------------------
-- Table structure for u_cmtp_temp
-- ----------------------------
DROP TABLE IF EXISTS `u_cmtp_temp`;
CREATE TABLE `u_cmtp_temp` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `IMSI` varchar(50) DEFAULT NULL,
  `ICCID` varchar(50) DEFAULT NULL,
  `userStatus` varchar(20) DEFAULT NULL COMMENT '用户状态， 暂留',
  `cardStatus` varchar(20) DEFAULT NULL COMMENT '卡状态',
  `gprsUsed` varchar(20) DEFAULT NULL COMMENT '使用流量',
  `gprsRest` varchar(20) DEFAULT NULL,
  `monthTotalStream` varchar(20) DEFAULT NULL COMMENT '总流量',
  `company` varchar(30) DEFAULT NULL COMMENT '公司名称',
  `company_level` varchar(20) DEFAULT NULL COMMENT '公司等级',
  `withGPRSService` varchar(1) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL COMMENT '套餐名',
  `packageDetail` varchar(80) DEFAULT NULL COMMENT '套餐描述',
  `updateTime` varchar(30) DEFAULT NULL COMMENT '开卡时间、修改时间',
  `orderStatus` varchar(10) DEFAULT NULL COMMENT '充值状态（根据操作修改订单状态，判断跳转链接）',
  `deadline` varchar(10) DEFAULT NULL COMMENT '剩余时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_C` (`ICCID`) USING BTREE,
  KEY `index_id` (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of u_cmtp_temp
-- ----------------------------
INSERT INTO `u_cmtp_temp` VALUES ('121', '', '861064617220041', '89860616010040477735', null, '正常', '0.000MB', '29.581MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('122', '', '861064617220042', '89860616010040477743', null, '正常', '0.000MB', '28.268MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('123', '', '861064617220043', '89860616010040477750', null, '正常', '0.000MB', '29.998MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('124', '', '861064617220044', '89860616010040477768', null, '正常', '0.000MB', '29.567MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('125', '', '861064617220045', '89860616010040477776', null, '正常', '0.000MB', '29.635MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('126', '', '861064617220046', '89860616010040477784', null, '正常', '0.000MB', '29.807MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('127', '', '861064617220047', '89860616010040477792', null, '正常', '0.000MB', '29.657MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('128', '', '861064617220048', '89860616010040477800', null, '正常', '0.000MB', '29.788MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('129', '', '861064617220049', '89860616010040477818', null, '正常', '0.000MB', '29.791MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('130', '', '861064617220050', '89860616010040477826', null, '正常', '0.000MB', '29.627MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('131', '', '861064617220051', '89860616010040477834', null, '正常', '0.000MB', '29.891MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('132', '', '861064617220052', '89860616010040477842', null, '正常', '0.000MB', '29.562MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');
INSERT INTO `u_cmtp_temp` VALUES ('133', '', '861064617220053', '89860616010040477859', null, '正常', '0.000MB', '29.356MB', '30.00MB', null, '企业实名', null, '30M(一年有效)', '30MB，流量不清零，一年有效全国通用，总流量用完即停机，可累加年套餐', '2017-11-27 16:47:31', null, '365天');

-- ----------------------------
-- Table structure for u_history
-- ----------------------------
DROP TABLE IF EXISTS `u_history`;
CREATE TABLE `u_history` (
  `iccid` varchar(50) DEFAULT NULL,
  `imsi` varchar(50) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL,
  `money` double(10,3) DEFAULT NULL,
  `update_date` varchar(20) DEFAULT NULL,
  `packageDetail` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_history
-- ----------------------------
INSERT INTO `u_history` VALUES ('89860616010040477735', null, null, '15.000', '2017-11-17 18:58:08', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477743', null, null, '15.000', '2017-11-17 16:23:39', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477750', null, null, '15.000', '2017-11-17 16:17:24', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477768', null, null, '15.000', '2017-11-17 15:55:39', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477776', null, null, '15.000', '2017-11-17 15:27:24', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477784', null, null, '15.000', '2017-11-17 15:26:19', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477792', null, null, '15.000', '2017-11-17 15:14:19', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477800', null, null, '15.000', '2017-11-17 14:59:04', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477818', null, null, '15.000', '2017-11-17 14:54:40', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477826', null, null, '15.000', '2017-11-17 14:52:57', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477834', null, null, '15.000', '2017-11-17 12:20:05', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477842', null, null, '15.000', '2017-11-17 11:38:50', null, null);
INSERT INTO `u_history` VALUES ('89860616010040477859', null, null, '15.000', '2017-11-17 10:54:46', null, null);

-- ----------------------------
-- Table structure for u_history_temp
-- ----------------------------
DROP TABLE IF EXISTS `u_history_temp`;
CREATE TABLE `u_history_temp` (
  `iccid` varchar(50) DEFAULT NULL,
  `imsi` varchar(50) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL,
  `money` double(10,3) DEFAULT NULL,
  `update_date` varchar(20) DEFAULT NULL,
  `packageDetail` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_history_temp
-- ----------------------------
INSERT INTO `u_history_temp` VALUES ('89860616010040477735', null, null, '15.000', '2017-11-17 18:58:08', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477743', null, null, '15.000', '2017-11-17 16:23:39', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477750', null, null, '15.000', '2017-11-17 16:17:24', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477768', null, null, '15.000', '2017-11-17 15:55:39', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477776', null, null, '15.000', '2017-11-17 15:27:24', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477784', null, null, '15.000', '2017-11-17 15:26:19', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477792', null, null, '15.000', '2017-11-17 15:14:19', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477800', null, null, '15.000', '2017-11-17 14:59:04', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477818', null, null, '15.000', '2017-11-17 14:54:40', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477826', null, null, '15.000', '2017-11-17 14:52:57', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477834', null, null, '15.000', '2017-11-17 12:20:05', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477842', null, null, '15.000', '2017-11-17 11:38:50', null, null);
INSERT INTO `u_history_temp` VALUES ('89860616010040477859', null, null, '15.000', '2017-11-17 10:54:46', null, null);
