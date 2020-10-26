/*
Navicat MySQL Data Transfer

Source Server         : 鉴权-神目
Source Server Version : 50646
Source Host           : 121.36.57.125:3356
Source Database       : uid_gen

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2020-06-05 09:50:14
*/
set sql_mode=(select replace(@@sql_mode,'NO_ZERO_IN_DATE,NO_ZERO_DATE',''));
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for WORKER_NODE
-- ----------------------------
DROP TABLE IF EXISTS `WORKER_NODE`;
CREATE TABLE `WORKER_NODE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自动递增编号',
  `HOST_NAME` varchar(64) NOT NULL COMMENT '主机名',
  `PORT` varchar(64) NOT NULL COMMENT '端口',
  `TYPE` int(11) NOT NULL COMMENT '节点类型：ACTUAL或CONTAINER',
  `LAUNCH_DATE` date NOT NULL COMMENT '发射日期',
  `MODIFIED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COMMENT='UID生成器的DB WorkerID分配器';
SET FOREIGN_KEY_CHECKS=1;
