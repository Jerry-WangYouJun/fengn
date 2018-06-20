/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : cmtp

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-06-20 17:29:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for a_agent
-- ----------------------------
DROP TABLE IF EXISTS `a_agent`;
CREATE TABLE `a_agent` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  `renew` decimal(10,2) DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `creatdate` varchar(20) DEFAULT NULL,
  `parentId` int(10) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `telphone` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for a_role
-- ----------------------------
DROP TABLE IF EXISTS `a_role`;
CREATE TABLE `a_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(20) DEFAULT NULL,
  `roleName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for a_user
-- ----------------------------
DROP TABLE IF EXISTS `a_user`;
CREATE TABLE `a_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userNo` varchar(50) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  `roleid` int(10) NOT NULL,
  `agentid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for card_agent
-- ----------------------------
DROP TABLE IF EXISTS `card_agent`;
CREATE TABLE `card_agent` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `iccid` varchar(100) DEFAULT NULL,
  `agentid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid_index` (`iccid`)
) ENGINE=MyISAM AUTO_INCREMENT=111310 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for card_detail
-- ----------------------------
DROP TABLE IF EXISTS `card_detail`;
CREATE TABLE `card_detail` (
  `id` int(10) NOT NULL,
  `gprs` int(10) DEFAULT NULL,
  `gprsused` double(10,3) DEFAULT NULL,
  `gprsrest` double(10,3) DEFAULT NULL,
  `sus` int(10) DEFAULT NULL,
  `susused` int(10) DEFAULT NULL,
  `susrest` int(10) DEFAULT NULL,
  `packageId` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cmcc_card_agent
-- ----------------------------
DROP TABLE IF EXISTS `cmcc_card_agent`;
CREATE TABLE `cmcc_card_agent` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `iccid` varchar(100) DEFAULT NULL,
  `agentid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid_index` (`iccid`)
) ENGINE=MyISAM AUTO_INCREMENT=147820 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cmtp
-- ----------------------------
DROP TABLE IF EXISTS `cmtp`;
CREATE TABLE `cmtp` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cardcode` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `IMSI` varchar(50) DEFAULT NULL,
  `ICCID` varchar(50) DEFAULT NULL,
  `userStatus` varchar(20) DEFAULT NULL,
  `cardStatus` varchar(20) DEFAULT NULL,
  `gprsUsed` decimal(20,5) DEFAULT NULL,
  `messageUsed` varchar(5) DEFAULT NULL,
  `openDate` varchar(30) DEFAULT NULL,
  `withMessageService` varchar(1) DEFAULT NULL,
  `withGPRSService` varchar(1) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL,
  `monthTotalStream` varchar(20) DEFAULT NULL,
  `updateTime` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `apiCode` varchar(5) DEFAULT NULL,
  `flag` varchar(5) DEFAULT '0' COMMENT '判断是否可以继续充值',
  PRIMARY KEY (`id`),
  KEY `INDEX_C` (`ICCID`),
  KEY `index_id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=154314 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cmtptest
-- ----------------------------
DROP TABLE IF EXISTS `cmtptest`;
CREATE TABLE `cmtptest` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cardcode` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `IMSI` varchar(50) DEFAULT NULL,
  `ICCID` varchar(50) DEFAULT NULL,
  `userStatus` varchar(20) DEFAULT NULL,
  `cardStatus` varchar(20) DEFAULT NULL,
  `gprsUsed` decimal(20,5) DEFAULT NULL,
  `messageUsed` varchar(5) DEFAULT NULL,
  `openDate` varchar(30) DEFAULT NULL,
  `withMessageService` varchar(1) DEFAULT NULL,
  `withGPRSService` varchar(1) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL,
  `monthTotalStream` varchar(20) DEFAULT NULL,
  `updateTime` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `apiCode` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=60015 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cmtp_temp
-- ----------------------------
DROP TABLE IF EXISTS `cmtp_temp`;
CREATE TABLE `cmtp_temp` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cardcode` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `IMSI` varchar(50) DEFAULT NULL,
  `ICCID` varchar(50) DEFAULT NULL,
  `userStatus` varchar(20) DEFAULT NULL,
  `cardStatus` varchar(20) DEFAULT NULL,
  `gprsUsed` decimal(20,5) DEFAULT NULL,
  `messageUsed` varchar(5) DEFAULT NULL,
  `openDate` varchar(30) DEFAULT NULL,
  `withMessageService` varchar(1) DEFAULT NULL,
  `withGPRSService` varchar(1) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL,
  `monthTotalStream` varchar(20) DEFAULT NULL,
  `updateTime` varchar(30) DEFAULT NULL,
  `apiCode` varchar(5) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `flag` varchar(5) DEFAULT '0' COMMENT '判断是否可以充值',
  PRIMARY KEY (`id`),
  KEY `INDEX_T` (`ICCID`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1044307 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `iccid` varchar(50) DEFAULT NULL,
  `imsi` varchar(50) DEFAULT NULL,
  `package_id` int(11) DEFAULT NULL,
  `money` double(10,3) DEFAULT NULL,
  `update_date` varchar(20) DEFAULT NULL,
  `orderNo` varchar(50) DEFAULT NULL,
  `packageType` varchar(20) DEFAULT NULL,
  `packageDetail` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for history_copy
-- ----------------------------
DROP TABLE IF EXISTS `history_copy`;
CREATE TABLE `history_copy` (
  `iccid` varchar(50) DEFAULT NULL,
  `imsi` varchar(50) DEFAULT NULL,
  `package_id` int(11) DEFAULT NULL,
  `money` double(10,3) DEFAULT NULL,
  `update_date` varchar(20) DEFAULT NULL,
  `orderNo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for history_copy_copy
-- ----------------------------
DROP TABLE IF EXISTS `history_copy_copy`;
CREATE TABLE `history_copy_copy` (
  `iccid` varchar(50) DEFAULT NULL,
  `imsi` varchar(50) DEFAULT NULL,
  `package_id` int(11) DEFAULT NULL,
  `money` double(10,3) DEFAULT NULL,
  `update_date` varchar(20) DEFAULT NULL,
  `orderNo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mlb_cmcc_card
-- ----------------------------
DROP TABLE IF EXISTS `mlb_cmcc_card`;
CREATE TABLE `mlb_cmcc_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(50) DEFAULT NULL,
  `simid` varchar(20) DEFAULT NULL,
  `sim` varchar(50) DEFAULT NULL,
  `packagename` varchar(30) DEFAULT NULL,
  `bootstate` varchar(10) DEFAULT NULL,
  `expiretime` varchar(30) DEFAULT NULL,
  `oddtime` varchar(20) DEFAULT NULL,
  `amountusagedata` double(15,5) DEFAULT NULL,
  `flowleftvalue` double(15,5) DEFAULT NULL,
  `monthusagedata` double(15,5) DEFAULT NULL,
  `totalmonthusageflow` double(15,5) DEFAULT NULL,
  `createTime` varchar(30) DEFAULT NULL,
  `holdName` varchar(30) DEFAULT NULL,
  `packagePeriodSrc` varchar(30) DEFAULT NULL,
  `activeTime` varchar(30) DEFAULT NULL,
  `remark` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=134036 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mlb_cmcc_card_copy
-- ----------------------------
DROP TABLE IF EXISTS `mlb_cmcc_card_copy`;
CREATE TABLE `mlb_cmcc_card_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(50) DEFAULT NULL,
  `simid` int(20) DEFAULT NULL,
  `sim` varchar(50) DEFAULT NULL,
  `packagename` varchar(30) DEFAULT NULL,
  `bootstate` varchar(10) DEFAULT NULL,
  `expiretime` varchar(30) DEFAULT NULL,
  `oddtime` varchar(20) DEFAULT NULL,
  `amountusagedata` double(15,5) DEFAULT NULL,
  `flowleftvalue` double(15,5) DEFAULT NULL,
  `monthusagedata` double(15,5) DEFAULT NULL,
  `totalmonthusageflow` double(15,5) DEFAULT NULL,
  `createTime` varchar(30) DEFAULT NULL,
  `holdName` varchar(30) DEFAULT NULL,
  `packagePeriodSrc` varchar(30) DEFAULT NULL,
  `activeTime` varchar(30) DEFAULT NULL,
  `remark` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=134036 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mlb_unicom_card
-- ----------------------------
DROP TABLE IF EXISTS `mlb_unicom_card`;
CREATE TABLE `mlb_unicom_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(30) DEFAULT NULL,
  `simid` varchar(30) DEFAULT NULL,
  `sim` varchar(30) DEFAULT NULL,
  `imsi` varchar(30) DEFAULT NULL,
  `packagename` varchar(20) DEFAULT NULL,
  `simstate` varchar(10) DEFAULT NULL,
  `expiretime` varchar(30) DEFAULT NULL,
  `oddtime` varchar(30) DEFAULT NULL,
  `dayusagedata` double(15,5) DEFAULT NULL COMMENT '日用量',
  `monthUsageData` double(15,5) DEFAULT NULL COMMENT '月用量',
  `flowLeftValue` double(15,5) DEFAULT NULL COMMENT '月剩余',
  `amountusagedata` double(15,5) DEFAULT NULL COMMENT '月总量',
  `totalmonthusageflow` double(15,5) DEFAULT NULL,
  `holdName` varchar(30) DEFAULT NULL,
  `lastActiveTime` varchar(30) DEFAULT NULL,
  `flowLeftTime` varchar(30) DEFAULT NULL,
  `outWarehouseDate` varchar(30) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid` (`guid`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79051 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mlb_unicom_card_copy
-- ----------------------------
DROP TABLE IF EXISTS `mlb_unicom_card_copy`;
CREATE TABLE `mlb_unicom_card_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(30) DEFAULT NULL,
  `simid` int(11) DEFAULT NULL,
  `sim` varchar(30) DEFAULT NULL,
  `imsi` varchar(30) DEFAULT NULL,
  `packagename` varchar(20) DEFAULT NULL,
  `simstate` varchar(10) DEFAULT NULL,
  `expiretime` varchar(30) DEFAULT NULL,
  `oddtime` varchar(30) DEFAULT NULL,
  `dayusagedata` double(15,5) DEFAULT NULL COMMENT '日用量',
  `monthUsageData` double(15,5) DEFAULT NULL COMMENT '月用量',
  `flowLeftValue` double(15,5) DEFAULT NULL COMMENT '月剩余',
  `amountusagedata` double(15,5) DEFAULT NULL COMMENT '月总量',
  `totalmonthusageflow` double(15,5) DEFAULT NULL,
  `holdName` varchar(30) DEFAULT NULL,
  `lastActiveTime` varchar(30) DEFAULT NULL,
  `flowLeftTime` varchar(30) DEFAULT NULL,
  `outWarehouseDate` varchar(30) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid` (`guid`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141289 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mm_prod_order
-- ----------------------------
DROP TABLE IF EXISTS `mm_prod_order`;
CREATE TABLE `mm_prod_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderCode` varchar(50) DEFAULT NULL,
  `CustomID` int(11) DEFAULT NULL,
  `ProductID` int(11) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `DeliveryDate` date DEFAULT NULL,
  `PlanStartDate` date DEFAULT NULL,
  `PlanFinishDate` date DEFAULT NULL,
  `RFIDCode` varchar(50) DEFAULT NULL,
  `CreateUserID` int(11) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INDEX_WORKSOP_CODE` (`OrderCode`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mm_work_order
-- ----------------------------
DROP TABLE IF EXISTS `mm_work_order`;
CREATE TABLE `mm_work_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderID` int(11) DEFAULT NULL,
  `Quantity` varchar(255) DEFAULT NULL,
  `ProcessId` int(11) DEFAULT NULL,
  `WorkshopID` int(11) DEFAULT NULL,
  `FinishNum` int(11) DEFAULT '0',
  `QuantityNum` int(11) unsigned DEFAULT '0',
  `Status` varchar(50) DEFAULT '0',
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for package
-- ----------------------------
DROP TABLE IF EXISTS `package`;
CREATE TABLE `package` (
  `id` int(10) NOT NULL,
  `iccid` varchar(50) DEFAULT NULL,
  `imsi` varchar(50) DEFAULT NULL,
  `package_name` varchar(20) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mlb_record
-- ----------------------------
DROP TABLE IF EXISTS `t_mlb_record`;
CREATE TABLE `t_mlb_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wxOrderId` varchar(20) DEFAULT NULL,
  `OrderSign` varchar(50) DEFAULT NULL COMMENT '订单号',
  `wxOrderNo` varchar(50) DEFAULT NULL COMMENT '微信交易单号',
  `Amount` double(10,3) DEFAULT NULL COMMENT '金额',
  `ICCID` varchar(50) DEFAULT NULL COMMENT 'ICCID',
  `SIM` varchar(50) DEFAULT NULL COMMENT 'SIM',
  `IMSI` varchar(50) DEFAULT NULL COMMENT 'ISMI',
  `IMEI` varchar(50) DEFAULT NULL,
  `PackageName` varchar(50) DEFAULT NULL COMMENT '续费套餐',
  `PayState` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `PayTime` varchar(50) DEFAULT NULL COMMENT '支付时间',
  `CreateTime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `PayMenter` varchar(50) DEFAULT NULL COMMENT '支付账户',
  `isToActiveOrder` varchar(50) DEFAULT NULL COMMENT '续费类型',
  `isPush` varchar(10) DEFAULT NULL COMMENT '是否推送',
  `holdName` varchar(10) DEFAULT NULL COMMENT '所属用户',
  `oldPackageName` varchar(20) DEFAULT NULL COMMENT '基础套餐',
  `isFirst` varchar(5) DEFAULT NULL COMMENT '首次支付',
  `simState` varchar(10) DEFAULT NULL COMMENT '卡状态',
  `activeTimespan` varchar(10) DEFAULT NULL COMMENT '距首次激活',
  `payEEName` varchar(20) DEFAULT NULL COMMENT '商户',
  `accessEEName` varchar(20) DEFAULT NULL COMMENT '接入方',
  `platformType` varchar(20) DEFAULT NULL,
  `RenewalsStatus` varchar(20) DEFAULT NULL COMMENT '续费状态',
  `RenewalsStatusMsg` varchar(60) DEFAULT NULL COMMENT '失败原因',
  `SimFromType` varchar(10) DEFAULT NULL,
  `Tag` varchar(10) DEFAULT NULL COMMENT '标签',
  `TradeType` varchar(10) DEFAULT NULL COMMENT '支付方式',
  `SourceType` varchar(10) DEFAULT NULL COMMENT '卡源',
  `Receivables` varchar(10) DEFAULT NULL,
  `isExpireRenewals` varchar(10) DEFAULT NULL COMMENT '到期续费',
  `MonthUsageData` varchar(10) DEFAULT NULL COMMENT '当月用量',
  `recordTime` varchar(255) DEFAULT NULL COMMENT '系统执行时间',
  PRIMARY KEY (`id`),
  KEY `iccid` (`ICCID`)
) ENGINE=InnoDB AUTO_INCREMENT=3346 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mlb_temp
-- ----------------------------
DROP TABLE IF EXISTS `t_mlb_temp`;
CREATE TABLE `t_mlb_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wxOrderId` varchar(20) DEFAULT NULL,
  `OrderSign` varchar(50) DEFAULT NULL COMMENT '订单号',
  `wxOrderNo` varchar(50) DEFAULT NULL COMMENT '微信交易单号',
  `Amount` double(10,3) DEFAULT NULL COMMENT '金额',
  `ICCID` varchar(50) DEFAULT NULL COMMENT 'ICCID',
  `SIM` varchar(50) DEFAULT NULL COMMENT 'SIM',
  `IMSI` varchar(50) DEFAULT NULL COMMENT 'ISMI',
  `IMEI` varchar(50) DEFAULT NULL,
  `PackageName` varchar(50) DEFAULT NULL COMMENT '续费套餐',
  `PayState` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `PayTime` varchar(50) DEFAULT NULL COMMENT '支付时间',
  `CreateTime` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `PayMenter` varchar(50) DEFAULT NULL COMMENT '支付账户',
  `isToActiveOrder` varchar(50) DEFAULT NULL COMMENT '续费类型',
  `isPush` varchar(10) DEFAULT NULL COMMENT '是否推送',
  `holdName` varchar(10) DEFAULT NULL COMMENT '所属用户',
  `oldPackageName` varchar(20) DEFAULT NULL COMMENT '基础套餐',
  `isFirst` varchar(5) DEFAULT NULL COMMENT '首次支付',
  `simState` varchar(10) DEFAULT NULL COMMENT '卡状态',
  `activeTimespan` varchar(10) DEFAULT NULL COMMENT '距首次激活',
  `payEEName` varchar(20) DEFAULT NULL COMMENT '商户',
  `accessEEName` varchar(20) DEFAULT NULL COMMENT '接入方',
  `platformType` varchar(20) DEFAULT NULL,
  `RenewalsStatus` varchar(20) DEFAULT NULL COMMENT '续费状态',
  `RenewalsStatusMsg` varchar(60) DEFAULT NULL COMMENT '失败原因',
  `SimFromType` varchar(10) DEFAULT NULL,
  `Tag` varchar(10) DEFAULT NULL COMMENT '标签',
  `TradeType` varchar(10) DEFAULT NULL COMMENT '支付方式',
  `SourceType` varchar(10) DEFAULT NULL COMMENT '卡源',
  `Receivables` varchar(10) DEFAULT NULL,
  `isExpireRenewals` varchar(10) DEFAULT NULL COMMENT '到期续费',
  `MonthUsageData` varchar(10) DEFAULT NULL COMMENT '当月用量',
  `recordTime` varchar(255) DEFAULT NULL COMMENT '系统执行时间',
  PRIMARY KEY (`id`),
  KEY `iccid` (`ICCID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_package
-- ----------------------------
DROP TABLE IF EXISTS `t_package`;
CREATE TABLE `t_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(20) DEFAULT NULL,
  `discrip` varchar(100) DEFAULT NULL,
  `cost` decimal(10,0) DEFAULT NULL,
  `renew` decimal(10,0) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_task_point
-- ----------------------------
DROP TABLE IF EXISTS `t_task_point`;
CREATE TABLE `t_task_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pointTime` varchar(30) DEFAULT NULL,
  `endTime` varchar(30) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `error` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=567 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(10) NOT NULL,
  `roleid` varchar(10) DEFAULT NULL,
  `userName` varchar(30) DEFAULT NULL,
  `loginname` varchar(30) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  `cost` decimal(10,5) DEFAULT NULL,
  `renew` decimal(10,5) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL COMMENT '用户表',
  PRIMARY KEY (`id`),
  KEY `USER_INDEX` (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for unicom_card_agent
-- ----------------------------
DROP TABLE IF EXISTS `unicom_card_agent`;
CREATE TABLE `unicom_card_agent` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `iccid` varchar(100) DEFAULT NULL,
  `agentid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `iccid_index` (`iccid`)
) ENGINE=MyISAM AUTO_INCREMENT=82595 DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM AUTO_INCREMENT=32092 DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM AUTO_INCREMENT=32121 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
) ENGINE=MyISAM AUTO_INCREMENT=72493 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
  `orderNo` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `orderNo` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
