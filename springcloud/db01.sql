/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : db01

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2021-03-01 09:53:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `deptno` bigint(20) NOT NULL AUTO_INCREMENT,
  `dname` varchar(60) DEFAULT NULL,
  `db_source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '开发部', 'db01');
INSERT INTO `dept` VALUES ('2', '人事部', 'db01');
INSERT INTO `dept` VALUES ('3', '财务部', 'db01');
INSERT INTO `dept` VALUES ('4', '市场部', 'db01');
INSERT INTO `dept` VALUES ('5', '运维部', 'db01');
INSERT INTO `dept` VALUES ('6', '测试部', 'db01');
