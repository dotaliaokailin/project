CREATE DATABASE db01;
USE db01;
-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `deptno` bigint(20) NOT NULL AUTO_INCREMENT,
  `dname` varchar(60) DEFAULT NULL,
  `db_source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` (dname,db_source) VALUES ('开发部', DATABASE());
INSERT INTO `dept` (dname,db_source) VALUES ('人事部', DATABASE());
INSERT INTO `dept` (dname,db_source) VALUES ('财务部', DATABASE());
INSERT INTO `dept` (dname,db_source) VALUES ('市场部', DATABASE());
INSERT INTO `dept` (dname,db_source) VALUES ('运维部', DATABASE());
INSERT INTO `dept` (dname,db_source) VALUES ('测试部', DATABASE());
