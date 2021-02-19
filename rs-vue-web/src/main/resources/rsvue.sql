/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.10.100
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.10.100:3306
 Source Schema         : springboot2.3.2

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 19/02/2021 15:31:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CRON_EXPRESSION` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('Task', 'cronTrigger', 'DEFAULT', '* * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
INSERT INTO `qrtz_fired_triggers` VALUES ('Task', 'NON_CLUSTERED1611810845630', 'cronTrigger', 'DEFAULT', 'NON_CLUSTERED', 1611810859066, 1611810860000, 0, 'ACQUIRED', NULL, NULL, '0', '0');

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('Task', 'jobDetail2', 'DEFAULT', NULL, 'org.rs.core.config.ScheduleConfiguration$MySecondJob', '1', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400046E616D6574000473616E677800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('Task', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('Task', 'cronTrigger', 'DEFAULT', 'jobDetail2', 'DEFAULT', NULL, 1611810860000, 1611810859000, 0, 'ACQUIRED', 'CRON', 1611810844000, 0, NULL, 0, '');

-- ----------------------------
-- Table structure for rs_access_log
-- ----------------------------
DROP TABLE IF EXISTS `rs_access_log`;
CREATE TABLE `rs_access_log`  (
  `LOGID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'logid',
  `OPERATION` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '方法',
  `METHODNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '方法',
  `PARAMETERS` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '参数',
  `MILLISECONDS` int(11) NOT NULL COMMENT '消耗时间',
  `IP` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '消耗时间',
  `ACCESSDATE` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '访问时间',
  `USERID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '访问人编号',
  `USERNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '访问人名称',
  `DEPTID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门ID',
  `DEPTNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门名称',
  `DEPTPATH` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门路径',
  PRIMARY KEY (`LOGID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_access_log
-- ----------------------------
INSERT INTO `rs_access_log` VALUES ('A000000001', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 30, '127.0.0.1', '2021-02-16 19:04:55', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A000000002', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000001\",\"pageSize\":20,\"page\":0}]', 20, '127.0.0.1', '2021-02-16 19:04:56', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A000000003', '查询菜单列表', 'org.rs.core.web.MenuCtrler.query()', '[{}]', 22, '127.0.0.1', '2021-02-16 19:04:58', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000B', '查询用户信息', 'org.rs.core.web.UserCtrler.query()', '[{\"page\":0,\"pageSize\":20,\"bmbh\":\"\",\"yhmc\":\"\"}]', 58, '127.0.0.1', '2021-02-16 21:11:41', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000C', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 53, '127.0.0.1', '2021-02-16 21:11:41', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000D', '查询机构树信息', 'org.rs.core.web.UserCtrler.queryDeptTree()', '[{}]', 75, '127.0.0.1', '2021-02-16 21:11:41', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000E', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 12, '127.0.0.1', '2021-02-16 21:12:31', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000F', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000001\",\"pageSize\":20,\"page\":0}]', 11, '127.0.0.1', '2021-02-16 21:12:31', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000G', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000002\",\"pageSize\":20,\"page\":0}]', 15, '127.0.0.1', '2021-02-16 21:12:33', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000H', '查询用户信息', 'org.rs.core.web.UserCtrler.query()', '[{\"page\":0,\"pageSize\":20,\"bmbh\":\"\",\"yhmc\":\"\"}]', 10, '127.0.0.1', '2021-02-16 21:12:37', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000I', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 20, '127.0.0.1', '2021-02-16 21:12:38', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000J', '查询机构树信息', 'org.rs.core.web.UserCtrler.queryDeptTree()', '[{}]', 27, '127.0.0.1', '2021-02-16 21:12:38', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000K', '查询菜单列表', 'org.rs.core.web.MenuCtrler.query()', '[{}]', 26, '127.0.0.1', '2021-02-16 21:12:45', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000L', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 21, '127.0.0.1', '2021-02-16 21:13:00', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000M', '查询角色', 'org.rs.core.web.RoleCtrler.query()', '[{\"page\":0,\"pageSize\":20}]', 41, '127.0.0.1', '2021-02-16 21:13:00', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000N', '查询机构树信息', 'org.rs.core.web.RoleCtrler.queryDeptTree()', '[{}]', 45, '127.0.0.1', '2021-02-16 21:13:00', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000O', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000003\",\"pageSize\":20,\"page\":0}]', 12, '127.0.0.1', '2021-02-16 21:13:30', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000P', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000002\",\"pageSize\":20,\"page\":0}]', 6, '127.0.0.1', '2021-02-16 21:13:31', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000Q', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000001\",\"pageSize\":20,\"page\":0}]', 8, '127.0.0.1', '2021-02-16 21:13:32', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000R', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000002\",\"pageSize\":20,\"page\":0}]', 47, '127.0.0.1', '2021-02-16 21:13:34', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000S', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000001\",\"pageSize\":20,\"page\":0}]', 11, '127.0.0.1', '2021-02-16 21:13:35', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000T', '查询用户信息', 'org.rs.core.web.UserCtrler.query()', '[{\"bmbh\":\"D00000001\",\"yhmc\":\"\",\"page\":0,\"pageSize\":20}]', 12, '127.0.0.1', '2021-02-16 21:13:37', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000U', '查询用户信息', 'org.rs.core.web.DeptCtrler.queryUser()', '[{\"bmbh\":\"D00000001\",\"yhmc\":\"\",\"page\":0,\"pageSize\":20}]', 19, '127.0.0.1', '2021-02-16 21:13:37', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000V', '查询用户信息', 'org.rs.core.web.UserCtrler.query()', '[{\"bmbh\":\"D00000001\",\"yhmc\":\"p\",\"page\":0,\"pageSize\":20}]', 5569, '127.0.0.1', '2021-02-16 21:13:51', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000000W', '查询用户信息', 'org.rs.core.web.DeptCtrler.queryUser()', '[{\"bmbh\":\"D00000001\",\"yhmc\":\"p\",\"page\":0,\"pageSize\":20}]', 5585, '127.0.0.1', '2021-02-16 21:13:51', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A000000015', '查询用户信息', 'org.rs.core.web.UserCtrler.query()', '[{\"page\":0,\"pageSize\":20,\"bmbh\":\"\",\"yhmc\":\"\"}]', 36, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:04', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A000000016', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 41, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:04', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A000000017', '查询机构树信息', 'org.rs.core.web.UserCtrler.queryDeptTree()', '[{}]', 69, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:04', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A000000018', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 7, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:07', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A000000019', '查询机构信息', 'org.rs.core.web.DeptCtrler.query()', '[{\"bmpbh\":\"D00000001\",\"pageSize\":20,\"page\":0}]', 14, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:07', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001A', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 8, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:09', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001B', '查询角色', 'org.rs.core.web.RoleCtrler.query()', '[{\"page\":0,\"pageSize\":20}]', 18, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:09', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001C', '查询机构树信息', 'org.rs.core.web.RoleCtrler.queryDeptTree()', '[{}]', 36, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:09', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001F', '查询用户信息', 'org.rs.core.web.UserCtrler.query()', '[{\"page\":0,\"pageSize\":20,\"bmbh\":\"\",\"yhmc\":\"\"}]', 48, '0:0:0:0:0:0:0:1', '2021-02-18 08:50:16', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001G', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 46, '0:0:0:0:0:0:0:1', '2021-02-18 08:50:16', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001H', '查询机构树信息', 'org.rs.core.web.UserCtrler.queryDeptTree()', '[{}]', 86, '0:0:0:0:0:0:0:1', '2021-02-18 08:50:16', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001P', '查询机构树信息', 'org.rs.core.web.DeptCtrler.queryDeptTree()', '[{}]', 177, '0:0:0:0:0:0:0:1', '2021-02-18 08:53:58', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001Q', '查询用户信息', 'org.rs.core.web.UserCtrler.query()', '[{\"page\":0,\"pageSize\":20,\"bmbh\":\"\",\"yhmc\":\"\"}]', 277, '0:0:0:0:0:0:0:1', '2021-02-18 08:53:58', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001R', '查询机构树信息', 'org.rs.core.web.UserCtrler.queryDeptTree()', '[{}]', 380, '0:0:0:0:0:0:0:1', '2021-02-18 08:53:58', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');
INSERT INTO `rs_access_log` VALUES ('A00000001S', '查询角色列表', 'org.rs.core.web.AuthCtrler.queryMenuTree()', '[{}]', 56, '0:0:0:0:0:0:0:1', '2021-02-18 08:54:00', 'U000000000', '系统管理员', 'D00000001', '系统根机构', 'D00000001');

-- ----------------------------
-- Table structure for rs_dept
-- ----------------------------
DROP TABLE IF EXISTS `rs_dept`;
CREATE TABLE `rs_dept`  (
  `BMBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门编号',
  `BMDM` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门组织代码',
  `BMMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门名称',
  `BMPBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '上级部门编号，root为根部门编号',
  `BMZT` int(11) NOT NULL COMMENT '部门状态，0-正常，1-锁定，2-注销',
  `BMLJ` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门路径，逗号分割的部门编号',
  `BMCJ` int(11) NOT NULL COMMENT '部门层级，从零开始，逐渐递加',
  `BMXH` int(11) NOT NULL COMMENT '部门排序号',
  `BMFH` int(11) NOT NULL COMMENT '部门是否复核，0-正常，1-待复核',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`BMBH`) USING BTREE,
  UNIQUE INDEX `RS_DEPT_UI_BMDM`(`BMDM`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_dept
-- ----------------------------
INSERT INTO `rs_dept` VALUES ('D00000001', 'D00000001', '系统根机构', 'D00000000', 0, 'D00000001', 0, 0, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_dept` VALUES ('D00000002', 'D00000002', '石家庄公司', 'D00000001', 0, 'D00000001,D00000002', 1, 0, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_dept` VALUES ('D00000003', 'D00000003', '唐山公司', 'D00000001', 0, 'D00000001,D00000003', 1, 1, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_dept` VALUES ('D00000004', 'D00000004', '保定公司', 'D00000001', 0, 'D00000001,D00000004', 1, 2, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_dept` VALUES ('D00000005', 'D00000005', '邯郸公司', 'D00000001', 0, 'D00000001,D00000005', 1, 3, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_dept_user
-- ----------------------------
DROP TABLE IF EXISTS `rs_dept_user`;
CREATE TABLE `rs_dept_user`  (
  `BMBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门编号',
  `YHBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户编号',
  `CJSJ` datetime(0) NOT NULL COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  PRIMARY KEY (`BMBH`, `YHBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rs_dict
-- ----------------------------
DROP TABLE IF EXISTS `rs_dict`;
CREATE TABLE `rs_dict`  (
  `ZDZJ` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典主键',
  `ZDBH` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典编号',
  `ZDMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典描述',
  `ZDLX` int(11) NOT NULL COMMENT '数据字典类型，0-系统字典，1-用户自定义字典',
  `ZDJG` int(11) NOT NULL COMMENT '数据字典结构，0-不分层，1-分层树形结构',
  `ZDXH` int(11) NOT NULL COMMENT '数据字典排序号',
  `ZDZT` tinyint(4) NOT NULL COMMENT '字典状态：0-正常，1-锁定，2-删除',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`ZDZJ`) USING BTREE,
  UNIQUE INDEX `RS_DICT_UI_ZDBH`(`ZDBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_dict
-- ----------------------------
INSERT INTO `rs_dict` VALUES ('D00000001', 'menuType', '菜单类型', 0, 0, 0, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict` VALUES ('D00000004', 'roleType', '角色类型', 0, 0, 1, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict` VALUES ('D00000007', 'roleLevel', '角色级别', 0, 0, 2, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_dict_info
-- ----------------------------
DROP TABLE IF EXISTS `rs_dict_info`;
CREATE TABLE `rs_dict_info`  (
  `TMZJ` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典条目主键',
  `ZDBH` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典编号',
  `TMPBH` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典条目父编号',
  `TMBH` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典条目编号',
  `TMMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '数据字典条目名称',
  `TMXH` int(11) NOT NULL COMMENT '数据字典排序号',
  `TMZT` tinyint(4) NOT NULL COMMENT '条目状态：0-正常，1-锁定，2-删除',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`TMZJ`) USING BTREE,
  UNIQUE INDEX `RS_DICT_INFO_UI_ZDBH_TMBH`(`ZDBH`, `TMBH`) USING BTREE,
  INDEX `RS_DICT_INFO_I_ZDBH`(`ZDBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_dict_info
-- ----------------------------
INSERT INTO `rs_dict_info` VALUES ('I00000002', 'menuType', '', '0', '子菜单', 0, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I00000003', 'menuType', '', '1', '路径', 1, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I00000005', 'roleType', '', '0', '管理角色', 0, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I00000006', 'roleType', '', '1', '普通角色', 1, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I00000008', 'roleLevel', '', '0', '级别0', 0, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I00000009', 'roleLevel', '', '1', '级别1', 1, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I0000000A', 'roleLevel', '', '2', '级别2', 2, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I0000000B', 'roleLevel', '', '3', '级别3', 3, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I0000000C', 'roleLevel', '', '4', '级别4', 4, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_dict_info` VALUES ('I0000000D', 'roleLevel', '', '5', '级别5', 5, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_key_table
-- ----------------------------
DROP TABLE IF EXISTS `rs_key_table`;
CREATE TABLE `rs_key_table`  (
  `KEYNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '序列号ID',
  `KEYMAXSEQ` bigint(20) NOT NULL COMMENT '序列号当前值',
  `KEYSTEP` int(11) NOT NULL COMMENT '序列号增长值',
  `KEYDATE` datetime(0) NOT NULL COMMENT '序列号更新时间',
  PRIMARY KEY (`KEYNAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_key_table
-- ----------------------------
INSERT INTO `rs_key_table` VALUES ('_RS_ACCESS_LOG_KEY_', 71, 10, '2021-02-17 09:04:38');
INSERT INTO `rs_key_table` VALUES ('_RS_DEPT_KEY_', 11, 10, '2021-02-17 08:23:00');
INSERT INTO `rs_key_table` VALUES ('_RS_DICT_KEY_', 21, 10, '2021-02-17 08:23:01');
INSERT INTO `rs_key_table` VALUES ('_RS_LOGIN_LOG_KEY_', 61, 10, '2021-02-17 09:04:36');
INSERT INTO `rs_key_table` VALUES ('_RS_MENU_KEY_', 21, 10, '2021-02-17 08:23:01');
INSERT INTO `rs_key_table` VALUES ('_RS_ROLE_KEY_', 11, 10, '2021-02-17 08:23:01');
INSERT INTO `rs_key_table` VALUES ('_RS_USER_AUTH_KEY_', 11, 10, '2021-02-17 08:23:00');
INSERT INTO `rs_key_table` VALUES ('_RS_USER_KEY_', 11, 10, '2021-02-17 08:23:00');

-- ----------------------------
-- Table structure for rs_login_log
-- ----------------------------
DROP TABLE IF EXISTS `rs_login_log`;
CREATE TABLE `rs_login_log`  (
  `LOGID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'logid',
  `USERID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `LOGINNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录账号',
  `USERNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名称',
  `DEPTID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门ID',
  `DEPTNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门名称',
  `DEPTPATH` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门路径',
  `SQLY` tinyint(4) NOT NULL COMMENT '授权类型，0-登录名，1-手机号，2-邮箱，其它值待实现',
  `IP` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '消耗时间',
  `LOGINDATE` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '登录时间',
  PRIMARY KEY (`LOGID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_login_log
-- ----------------------------
INSERT INTO `rs_login_log` VALUES ('L000000001', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '127.0.0.1', '2021-02-16 19:04:53');
INSERT INTO `rs_login_log` VALUES ('L00000000B', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '127.0.0.1', '2021-02-16 21:11:38');
INSERT INTO `rs_login_log` VALUES ('L00000000L', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '0:0:0:0:0:0:0:1', '2021-02-17 20:18:02');
INSERT INTO `rs_login_log` VALUES ('L00000000M', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '127.0.0.1', '2021-02-18 05:56:02');
INSERT INTO `rs_login_log` VALUES ('L00000000N', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '127.0.0.1', '2021-02-18 06:40:26');
INSERT INTO `rs_login_log` VALUES ('L00000000V', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '0:0:0:0:0:0:0:1', '2021-02-18 08:45:51');
INSERT INTO `rs_login_log` VALUES ('L00000000W', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '0:0:0:0:0:0:0:1', '2021-02-18 08:50:04');
INSERT INTO `rs_login_log` VALUES ('L00000000X', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '0:0:0:0:0:0:0:1', '2021-02-18 08:50:13');
INSERT INTO `rs_login_log` VALUES ('L00000000Y', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '0:0:0:0:0:0:0:1', '2021-02-18 08:50:41');
INSERT INTO `rs_login_log` VALUES ('L000000015', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '0:0:0:0:0:0:0:1', '2021-02-18 08:55:43');
INSERT INTO `rs_login_log` VALUES ('L00000001F', 'U000000000', 'sysadmin', '系统管理员', 'D00000001', '系统根机构', 'D00000001', 0, '127.0.0.1', '2021-02-19 01:28:44');

-- ----------------------------
-- Table structure for rs_menu
-- ----------------------------
DROP TABLE IF EXISTS `rs_menu`;
CREATE TABLE `rs_menu`  (
  `CDBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单编号',
  `PCDBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '上级菜单编号',
  `CDMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `ICON` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单对应的图标名称',
  `CDLJ` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单路径',
  `CDLX` tinyint(4) NOT NULL COMMENT '菜单类型，0-菜单，1-路径',
  `SFHC` tinyint(4) NOT NULL COMMENT '是否缓存：0-缓存，1-不缓存',
  `CDZT` tinyint(4) NOT NULL COMMENT '菜单状态：0-正常，1-锁定，2-删除',
  `CDXH` int(11) NOT NULL COMMENT '菜单排序号',
  `CJSJ` datetime(0) NOT NULL COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  PRIMARY KEY (`CDBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_menu
-- ----------------------------
INSERT INTO `rs_menu` VALUES ('M000000001', 'M000000000', '系统管理', 'rs-icon-mgmt', '/', 0, 1, 0, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000002', 'M000000001', '用户管理', 'rs-icon-user', '/sys/user', 1, 1, 0, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000003', 'M000000001', '机构管理', 'rs-icon-unit', '/sys/unit', 1, 1, 0, 1, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000004', 'M000000001', '角色管理', 'rs-icon-role', '/sys/role', 1, 1, 0, 2, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000005', 'M000000001', '菜单管理', 'el-icon-menu', '/sys/menu', 1, 1, 0, 3, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000006', 'M000000001', '权限管理', 'rs-icon-auth', '/sys/auth', 1, 1, 0, 4, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000007', 'M000000001', '系统配置', 'rs-icon-setting', '/', 0, 1, 0, 5, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000008', 'M000000007', '字典配置', 'rs-icon-dict', '/sys/dict', 1, 1, 0, 6, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M000000009', 'M000000007', '策略配置', 'rs-icon-anquan', '/sys/strategy', 1, 1, 0, 7, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M00000000A', 'M000000000', '管理信息', 'rs-icon-theme', '/', 0, 1, 0, 1, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M00000000B', 'M00000000A', '系统日志', 'rs-icon-auth', '/sys/log', 1, 1, 0, 0, '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_menu` VALUES ('M00000000C', 'M00000000A', '系统信息', 'rs-icon-auth', '/sys/info', 1, 1, 0, 1, '2021-02-16 18:23:18', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_menu_url
-- ----------------------------
DROP TABLE IF EXISTS `rs_menu_url`;
CREATE TABLE `rs_menu_url`  (
  `CDBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单编号',
  `URL` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'url',
  PRIMARY KEY (`CDBH`, `URL`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_menu_url
-- ----------------------------
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/dept/user/add');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/dept/user/delete');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user/*.*');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user/add');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user/delete');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user/password');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user/role/add');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user/role/delete');
INSERT INTO `rs_menu_url` VALUES ('M000000002', '/api/user/save');

-- ----------------------------
-- Table structure for rs_refresh_milliseconds
-- ----------------------------
DROP TABLE IF EXISTS `rs_refresh_milliseconds`;
CREATE TABLE `rs_refresh_milliseconds`  (
  `REFRESH_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '刷新项编号',
  `MILLISECONDS` bigint(20) NOT NULL COMMENT '最后刷新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_refresh_milliseconds
-- ----------------------------
INSERT INTO `rs_refresh_milliseconds` VALUES ('SPRING_SECURITY_CONTEXT', 1612017951694);

-- ----------------------------
-- Table structure for rs_role
-- ----------------------------
DROP TABLE IF EXISTS `rs_role`;
CREATE TABLE `rs_role`  (
  `JSBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编号',
  `JSNM` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名字',
  `JSMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `JSLX` int(11) NOT NULL COMMENT '角色类型，0-管理角色，其他-自定义角色',
  `JSJB` int(11) NOT NULL COMMENT '角色级别',
  `JSXH` int(11) NOT NULL COMMENT '角色序号，用来排序',
  `JSZT` tinyint(4) NOT NULL COMMENT '角色状态：0-正常，1-锁定，2-删除',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`JSBH`) USING BTREE,
  UNIQUE INDEX `RS_ROLE_UI_JSNM`(`JSNM`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_role
-- ----------------------------
INSERT INTO `rs_role` VALUES ('R00000001', 'ROLE_SYSADMIN', '系统管理员', 0, 0, 0, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_role` VALUES ('R00000002', 'ROLE_USER', '普通用户', 1, 1, 1, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `rs_role_menu`;
CREATE TABLE `rs_role_menu`  (
  `JSBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编号',
  `CDBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单编号',
  `CJSJ` datetime(0) NOT NULL COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  PRIMARY KEY (`JSBH`, `CDBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_role_menu
-- ----------------------------
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000001', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000002', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000003', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000004', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000005', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000006', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000008', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M000000009', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M00000000A', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M00000000B', '2021-02-16 18:23:18', 'U000000000', '系统管理员');
INSERT INTO `rs_role_menu` VALUES ('R00000001', 'M00000000C', '2021-02-16 18:23:18', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_role_url
-- ----------------------------
DROP TABLE IF EXISTS `rs_role_url`;
CREATE TABLE `rs_role_url`  (
  `JSBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编号',
  `URL` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'url',
  `CJSJ` datetime(0) NOT NULL COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  PRIMARY KEY (`JSBH`, `URL`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_role_url
-- ----------------------------
INSERT INTO `rs_role_url` VALUES ('R0000000B', '/auth/**', '2021-01-01 22:29:00', 'U000000000', '系统管理员');
INSERT INTO `rs_role_url` VALUES ('R0000000B', '/dept/**', '2021-01-01 22:35:27', 'U000000000', '系统管理员');
INSERT INTO `rs_role_url` VALUES ('R0000000B', '/log/**', '2021-01-30 08:45:52', 'U000000000', '系统管理员');
INSERT INTO `rs_role_url` VALUES ('R0000000B', '/user/**', '2021-01-01 22:40:42', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_role_user
-- ----------------------------
DROP TABLE IF EXISTS `rs_role_user`;
CREATE TABLE `rs_role_user`  (
  `JSBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编号',
  `YHBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户编号',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`JSBH`, `YHBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_role_user
-- ----------------------------
INSERT INTO `rs_role_user` VALUES ('R00000001', 'U000000000', '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_role_user` VALUES ('R00000002', 'U000000001', '2021-02-16 18:23:18', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_strategy
-- ----------------------------
DROP TABLE IF EXISTS `rs_strategy`;
CREATE TABLE `rs_strategy`  (
  `CLBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '策略编号',
  `CLMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '策略名称',
  `CLZT` int(11) NOT NULL COMMENT '策略状态，0-启用，1-关闭',
  `CLXH` int(11) NOT NULL COMMENT '策略排序号',
  PRIMARY KEY (`CLBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_strategy
-- ----------------------------
INSERT INTO `rs_strategy` VALUES ('level', '自定义', 1, 3);
INSERT INTO `rs_strategy` VALUES ('level1', '一级策略', 0, 0);
INSERT INTO `rs_strategy` VALUES ('level2', '二级策略', 1, 1);
INSERT INTO `rs_strategy` VALUES ('level3', '三级策略', 1, 2);

-- ----------------------------
-- Table structure for rs_strategy_desc
-- ----------------------------
DROP TABLE IF EXISTS `rs_strategy_desc`;
CREATE TABLE `rs_strategy_desc`  (
  `MSBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '策略描述编号',
  `MSMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '策略描述名称',
  `MSXH` int(11) NOT NULL COMMENT '策略描述排序号',
  PRIMARY KEY (`MSBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_strategy_desc
-- ----------------------------
INSERT INTO `rs_strategy_desc` VALUES ('ALLOW_LOGIN_FAIL_TIMES', '登录出错门限(0:不检测)', 12);
INSERT INTO `rs_strategy_desc` VALUES ('AUTO_ENABLE_TIME', '自动解锁用户时间（小时）', 6);
INSERT INTO `rs_strategy_desc` VALUES ('DEFAULT_PASSWORD', '系统默认密码', 7);
INSERT INTO `rs_strategy_desc` VALUES ('FIRST_MODIFY_PASSWORD', '首次登录修改密码(是:yes,不是:no)', 14);
INSERT INTO `rs_strategy_desc` VALUES ('IS_AUTO_ENABLE', '是否自动解锁用户(是:yes,不是:no)', 5);
INSERT INTO `rs_strategy_desc` VALUES ('IS_KICK_SESSION', '一个用户达到最大会话数后是否允许登录', 1);
INSERT INTO `rs_strategy_desc` VALUES ('LOGIN_CHECK', '登录是否需要检验码(是:yes,不是:no)', 4);
INSERT INTO `rs_strategy_desc` VALUES ('MAX_FAILED_LOGIN_COUNT', '密码允许的最多错误次数(0:不检测)', 9);
INSERT INTO `rs_strategy_desc` VALUES ('MAX_SESSION_COUNT', '系统允许最大会话总数(0:不检测)', 2);
INSERT INTO `rs_strategy_desc` VALUES ('PASSWORD_EXPIRE_DAYS', '密码有效期（天）', 8);
INSERT INTO `rs_strategy_desc` VALUES ('PASSWORD_LEVEL', '密码强度等级', 13);
INSERT INTO `rs_strategy_desc` VALUES ('PASSWORD_MIN_LENGTH', '密码最短的长度限制(0:不检测)', 11);
INSERT INTO `rs_strategy_desc` VALUES ('PASSWORD_MODIFIED_TIMES', '密码不可与前几次历史密码重复(0:不检测)', 10);
INSERT INTO `rs_strategy_desc` VALUES ('SESSION_CONTROL', '会话控制(有:yes,没有:no)', 3);
INSERT INTO `rs_strategy_desc` VALUES ('USER_SESSION', '一个用户允许的会话数(0:不检测)', 0);

-- ----------------------------
-- Table structure for rs_strategy_info
-- ----------------------------
DROP TABLE IF EXISTS `rs_strategy_info`;
CREATE TABLE `rs_strategy_info`  (
  `CLBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '策略编号',
  `MSBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '策略描述编号',
  `TMZ` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '策略条目值',
  `TMZT` int(11) NOT NULL COMMENT '策略状态，0-启用，1-锁定，2-删除',
  `TMXH` int(11) NOT NULL COMMENT '策略排序号',
  PRIMARY KEY (`CLBH`, `MSBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_strategy_info
-- ----------------------------
INSERT INTO `rs_strategy_info` VALUES ('level', 'ALLOW_LOGIN_FAIL_TIMES', '0', 0, 14);
INSERT INTO `rs_strategy_info` VALUES ('level', 'AUTO_ENABLE_TIME', '0', 0, 10);
INSERT INTO `rs_strategy_info` VALUES ('level', 'DEFAULT_PASSWORD', '123456', 0, 11);
INSERT INTO `rs_strategy_info` VALUES ('level', 'FIRST_MODIFY_PASSWORD', '0', 0, 8);
INSERT INTO `rs_strategy_info` VALUES ('level', 'IS_AUTO_ENABLE', '0', 0, 9);
INSERT INTO `rs_strategy_info` VALUES ('level', 'IS_KICK_SESSION', '1', 0, 1);
INSERT INTO `rs_strategy_info` VALUES ('level', 'LOGIN_CHECK', '0', 0, 4);
INSERT INTO `rs_strategy_info` VALUES ('level', 'MAX_FAILED_LOGIN_COUNT', '0', 0, 12);
INSERT INTO `rs_strategy_info` VALUES ('level', 'MAX_SESSION_COUNT', '0', 0, 2);
INSERT INTO `rs_strategy_info` VALUES ('level', 'PASSWORD_EXPIRE_DAYS', '0', 0, 6);
INSERT INTO `rs_strategy_info` VALUES ('level', 'PASSWORD_LEVEL', '0', 0, 5);
INSERT INTO `rs_strategy_info` VALUES ('level', 'PASSWORD_MIN_LENGTH', '6', 0, 13);
INSERT INTO `rs_strategy_info` VALUES ('level', 'PASSWORD_MODIFIED_TIMES', '0', 0, 7);
INSERT INTO `rs_strategy_info` VALUES ('level', 'SESSION_CONTROL', '0', 0, 3);
INSERT INTO `rs_strategy_info` VALUES ('level', 'USER_SESSION', '0', 0, 0);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'ALLOW_LOGIN_FAIL_TIMES', '0', 0, 14);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'AUTO_ENABLE_TIME', '0', 0, 10);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'DEFAULT_PASSWORD', '123456', 0, 11);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'FIRST_MODIFY_PASSWORD', '0', 0, 8);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'IS_AUTO_ENABLE', '0', 0, 9);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'IS_KICK_SESSION', '1', 0, 1);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'LOGIN_CHECK', '0', 0, 4);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'MAX_FAILED_LOGIN_COUNT', '0', 0, 12);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'MAX_SESSION_COUNT', '0', 0, 2);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'PASSWORD_EXPIRE_DAYS', '0', 0, 6);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'PASSWORD_LEVEL', '0', 0, 5);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'PASSWORD_MIN_LENGTH', '6', 0, 13);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'PASSWORD_MODIFIED_TIMES', '0', 0, 7);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'SESSION_CONTROL', '0', 0, 3);
INSERT INTO `rs_strategy_info` VALUES ('level1', 'USER_SESSION', '0', 0, 0);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'ALLOW_LOGIN_FAIL_TIMES', '0', 0, 14);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'AUTO_ENABLE_TIME', '0', 0, 10);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'DEFAULT_PASSWORD', '123456', 0, 11);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'FIRST_MODIFY_PASSWORD', '1', 0, 8);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'IS_AUTO_ENABLE', '0', 0, 9);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'IS_KICK_SESSION', '0', 0, 1);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'LOGIN_CHECK', '0', 0, 4);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'MAX_FAILED_LOGIN_COUNT', '0', 0, 12);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'MAX_SESSION_COUNT', '0', 0, 2);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'PASSWORD_EXPIRE_DAYS', '30', 0, 6);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'PASSWORD_LEVEL', '1', 0, 5);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'PASSWORD_MIN_LENGTH', '6', 0, 13);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'PASSWORD_MODIFIED_TIMES', '1', 0, 7);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'SESSION_CONTROL', '0', 0, 3);
INSERT INTO `rs_strategy_info` VALUES ('level2', 'USER_SESSION', '2', 0, 0);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'ALLOW_LOGIN_FAIL_TIMES', '0', 0, 14);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'AUTO_ENABLE_TIME', '0', 0, 10);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'DEFAULT_PASSWORD', '123456', 0, 11);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'FIRST_MODIFY_PASSWORD', '1', 0, 8);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'IS_AUTO_ENABLE', '0', 0, 9);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'IS_KICK_SESSION', '0', 0, 1);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'LOGIN_CHECK', '1', 0, 4);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'MAX_FAILED_LOGIN_COUNT', '0', 0, 12);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'MAX_SESSION_COUNT', '100', 0, 2);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'PASSWORD_EXPIRE_DAYS', '7', 0, 6);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'PASSWORD_LEVEL', '2', 0, 5);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'PASSWORD_MIN_LENGTH', '6', 0, 13);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'PASSWORD_MODIFIED_TIMES', '3', 0, 7);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'SESSION_CONTROL', '1', 0, 3);
INSERT INTO `rs_strategy_info` VALUES ('level3', 'USER_SESSION', '1', 0, 0);

-- ----------------------------
-- Table structure for rs_url
-- ----------------------------
DROP TABLE IF EXISTS `rs_url`;
CREATE TABLE `rs_url`  (
  `URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'url地址',
  `URLMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'url名称',
  `URLLX` tinyint(4) NOT NULL COMMENT 'url类型，0-类上的url,1-方法上的url',
  `URLST` tinyint(4) NOT NULL COMMENT 'url状态，0-正常,1-删除',
  PRIMARY KEY (`URL`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_url
-- ----------------------------
INSERT INTO `rs_url` VALUES ('/auth/**', '授权配置API', 0, 0);
INSERT INTO `rs_url` VALUES ('/auth/menu', '根据角色编号查询菜单列表', 1, 0);
INSERT INTO `rs_url` VALUES ('/auth/menu/save', '添加菜单信息到角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/auth/menu/tree', '查询角色列表', 1, 0);
INSERT INTO `rs_url` VALUES ('/auth/url', '查询URL信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/auth/url/add', '添加URL到角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/auth/url/delete', '从角色删除URL', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept', '查询机构信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/**', '机构信息API', 0, 0);
INSERT INTO `rs_url` VALUES ('/dept/add', '添加机构', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/delete', '删除机构', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/save', '修改机构', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/tree', '查询机构树信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/user', '查询用户信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/user/add', '给机构添加关联用户', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/user/delete', '从机构中删除关联用户', 1, 0);
INSERT INTO `rs_url` VALUES ('/dept/user/edit', '修改用户所属的机构', 1, 0);
INSERT INTO `rs_url` VALUES ('/log/**', 'LOG信息API', 0, 0);
INSERT INTO `rs_url` VALUES ('/log/access', '查询AccessLOG信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/log/info', '查询SysLog信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/log/login', '查询LoginLOG信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/menu', '查询菜单列表', 1, 0);
INSERT INTO `rs_url` VALUES ('/menu/**', '菜单操作API', 0, 0);
INSERT INTO `rs_url` VALUES ('/menu/add', '添加菜单', 1, 0);
INSERT INTO `rs_url` VALUES ('/menu/delete', '删除菜单', 1, 0);
INSERT INTO `rs_url` VALUES ('/menu/save', '修改菜单', 1, 0);
INSERT INTO `rs_url` VALUES ('/menu/{id}', '根据菜单编号查询菜单', 1, 0);
INSERT INTO `rs_url` VALUES ('/role', '查询角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/role/**', '角色操作API', 0, 0);
INSERT INTO `rs_url` VALUES ('/role/add', '添加角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/role/delete', '删除角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/role/dept/tree', '查询机构树信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/role/save', '更新角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/role/user', '查询用户信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/strategy', '查询安全策略列表', 1, 0);
INSERT INTO `rs_url` VALUES ('/strategy/**', '策略操作API', 0, 0);
INSERT INTO `rs_url` VALUES ('/strategy/save', '保存安全策略信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/strategy/zt/save', '修改安全策略状态', 1, 0);
INSERT INTO `rs_url` VALUES ('/url', '查询URL信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/user', '查询用户信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/**', '用户信息API', 0, 0);
INSERT INTO `rs_url` VALUES ('/user/add', '添加用户', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/delete', '删除用户', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/dept/tree', '查询机构树信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/password', '修改用户密码', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/role', '查询机构树信息', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/role/add', '给用户添加角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/role/delete', '删除用户角色', 1, 0);
INSERT INTO `rs_url` VALUES ('/user/save', '更新用户', 1, 0);

-- ----------------------------
-- Table structure for rs_user
-- ----------------------------
DROP TABLE IF EXISTS `rs_user`;
CREATE TABLE `rs_user`  (
  `YHBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户编号',
  `DLMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录名称',
  `YHMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名称',
  `BMBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门编号',
  `PHONE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号码',
  `EMAIL` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮件地址',
  `ADDRESS` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户地址',
  `SFFH` tinyint(4) NOT NULL COMMENT '用户是否复核，0-正常，1-待复核',
  `YHLX` tinyint(4) NOT NULL COMMENT '用户类型，0-默认用户，1-普通用户，其它值为自定义用户类型',
  `YHZT` tinyint(4) NOT NULL COMMENT '用户状态，0-正常，1-锁定，2-删除',
  `DLCS` tinyint(4) NOT NULL COMMENT '记录登录失败的次数，登录成功后清零',
  `CQJC` tinyint(4) NOT NULL COMMENT '密码超期检测,0-不检测，1-检测',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`YHBH`) USING BTREE,
  UNIQUE INDEX `RS_USER_UI_DLMC`(`DLMC`) USING BTREE,
  INDEX `RS_USER_I_BMBH`(`BMBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_user
-- ----------------------------
INSERT INTO `rs_user` VALUES ('U000000000', 'sysadmin', '系统管理员', 'D00000001', NULL, NULL, NULL, 0, 0, 0, 0, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_user` VALUES ('U000000001', 'sysuser', '普通用户', 'D00000001', NULL, NULL, NULL, 0, 1, 0, 0, 0, '2021-02-16 18:23:17', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `rs_user_auth`;
CREATE TABLE `rs_user_auth`  (
  `SQBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '授权编号',
  `SQLY` tinyint(4) NOT NULL COMMENT '授权类型，0-登录名，1-手机号，2-邮箱，其它值待实现',
  `DLMC` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录名称或者第三方登录的账号',
  `YHBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '授权对应的用户编号',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`SQBH`) USING BTREE,
  UNIQUE INDEX `RS_USER_AUTH_UI_SQLY_DLMC`(`SQLY`, `DLMC`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_user_auth
-- ----------------------------
INSERT INTO `rs_user_auth` VALUES ('A000000001', 0, 'sysadmin', 'U000000000', '2021-02-16 18:23:17', 'U000000000', '系统管理员');
INSERT INTO `rs_user_auth` VALUES ('A000000002', 0, 'sysuser', 'U000000001', '2021-02-16 18:23:17', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for rs_user_password
-- ----------------------------
DROP TABLE IF EXISTS `rs_user_password`;
CREATE TABLE `rs_user_password`  (
  `YHBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户编号',
  `DLMM` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录密码',
  `CJSJ` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `CJRBH` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人编号',
  `CJRMC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人名称',
  PRIMARY KEY (`YHBH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rs_user_password
-- ----------------------------
INSERT INTO `rs_user_password` VALUES ('U000000000', '$2a$10$Jea6TQPQ6xQtLg3.6hGKf.X3o4HmqKNBj8200JYRFgN1gPaSeOOdK', '2020-12-27 05:28:47', 'U000000000', '系统管理员');
INSERT INTO `rs_user_password` VALUES ('U000000001', '$2a$10$0NeTnvduHboW14SwWUXzSeY/QoIRpWA585ECfyqRiTOS7cfVnexUu', '2020-12-27 05:28:47', 'U000000000', '系统管理员');
INSERT INTO `rs_user_password` VALUES ('U00000000B', '$2a$10$RVRwv3ckrGJ1EfFYuDau8.AwTKVXgc4AVbNmiCA1ow836DBeB7OIy', '2020-12-30 18:53:59', 'U000000000', '系统管理员');
INSERT INTO `rs_user_password` VALUES ('U00000000D', '$2a$10$1L2YB0IeLVttzym9o92yEe.epmEs.lvHnbMZHBWr04Zog55wEddsi', '2020-12-29 19:41:31', 'U000000000', '系统管理员');
INSERT INTO `rs_user_password` VALUES ('U00000000L', '$2a$10$WimMcQLo46p05jpOiBT6juquGlvMJehsmbkgMttPHF0c8YxUOR3bS', '2020-12-30 08:15:27', 'U000000000', '系统管理员');

-- ----------------------------
-- Table structure for sys_session
-- ----------------------------
DROP TABLE IF EXISTS `sys_session`;
CREATE TABLE `sys_session`  (
  `PRIMARY_ID` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '会话主键',
  `SESSION_ID` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '会话ID',
  `CREATION_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `LAST_ACCESS_TIME` bigint(20) NOT NULL COMMENT '最后访问时间',
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL COMMENT '最大不活动间隔',
  `EXPIRY_TIME` bigint(20) NOT NULL COMMENT '超期时间',
  `IP_ADDR` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户登录的IP',
  `PRINCIPAL_NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录的用户ID',
  PRIMARY KEY (`PRIMARY_ID`) USING BTREE,
  UNIQUE INDEX `SYS_SESSION_UI_SESSION_ID`(`SESSION_ID`) USING BTREE,
  INDEX `SYS_SESSION_I_EXPIRY_TIME`(`EXPIRY_TIME`) USING BTREE,
  INDEX `SYS_SESSION_I_PRINCIPAL_NAME`(`PRINCIPAL_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_session
-- ----------------------------
INSERT INTO `sys_session` VALUES ('6b524fbe68364b5aa6f4e1bbb0f14e55', '127307409c22429ea168c650d8bede54', 1613660143001, 1613660143002, 1800, 1613661943002, '0:0:0:0:0:0:0:1', 'U000000000');
INSERT INTO `sys_session` VALUES ('8ac6b69450c845cd904315289776fb98', '8f430c05484b42a3b1591c48c9bc80b3', 1612017588826, 1612018019749, 1800, 1612019819749, '127.0.0.1', 'U000000000');
INSERT INTO `sys_session` VALUES ('aea03c68109d4a699d337b47283c2b69', 'bafde7b4c77744818dc4aaceefe5a65e', 1612011015913, 1612011015917, 1800, 1612012815917, '127.0.0.1', '');
INSERT INTO `sys_session` VALUES ('bba69cbfdd874fe49761ebdc91341f40', '179728eefdf4423c8f9a8ef113681595', 1613719724646, 1613719724647, 1800, 1613721524647, '127.0.0.1', 'U000000000');
INSERT INTO `sys_session` VALUES ('bd9104dd75d24c838f5ee2b07bed2d7d', 'ae7f3e2bde2b4b78a624966e06e1adf8', 1612017597265, 1612017987064, 1800, 1612019787064, '127.0.0.1', 'U00000000B');
INSERT INTO `sys_session` VALUES ('fac235c3dd9841b79edccd95839a3ac3', '9238c23653e74d3f83062efdfb4745c2', 1613659840601, 1613660040354, 1800, 1613661840354, '0:0:0:0:0:0:0:1', 'U000000000');
INSERT INTO `sys_session` VALUES ('fe1d68a69d2643618d28b0992a75c868', '80853cbe3fb44750b57282f87758c014', 1613659550955, 1613660070795, 1800, 1613661870795, '0:0:0:0:0:0:0:1', 'U000000000');

-- ----------------------------
-- Table structure for sys_session_attributes
-- ----------------------------
DROP TABLE IF EXISTS `sys_session_attributes`;
CREATE TABLE `sys_session_attributes`  (
  `SESSION_PRIMARY_ID` char(34) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '会话主键',
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '属性名',
  `ATTRIBUTE_BYTES` blob NOT NULL COMMENT '属性内容',
  INDEX `SYS_SESSION_ATTRIBUTES_I_SESSION_PRIMARY_ID`(`SESSION_PRIMARY_ID`) USING BTREE,
  CONSTRAINT `SYS_SESSION_ATTRIBUTES_FK_SESSION_PRIMARY_ID` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `sys_session` (`PRIMARY_ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_session_attributes
-- ----------------------------
INSERT INTO `sys_session_attributes` VALUES ('aea03c68109d4a699d337b47283c2b69', 'shiroSavedRequest', 0xACED0005737200266F72672E6170616368652E736869726F2E7765622E7574696C2E536176656452657175657374AFCE3CAD7982CABA0200034C00066D6574686F647400124C6A6176612F6C616E672F537472696E673B4C000B7175657279537472696E6771007E00014C000A7265717565737455524971007E00017870740004504F5354707400102F72737675652F617574682F696E666F);
INSERT INTO `sys_session_attributes` VALUES ('aea03c68109d4a699d337b47283c2b69', 'org.apache.shiro.web.session.HttpServletSession.HOST_SESSION_KEY', 0xACED00057400093132372E302E302E31);
INSERT INTO `sys_session_attributes` VALUES ('8ac6b69450c845cd904315289776fb98', 'SPRING_SECURITY_CONTEXT', 0xACED00057372003D6F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E636F6E746578742E5365637572697479436F6E74657874496D706C00000000000002120200014C000E61757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B7870737200396F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E527341757468656E7469636174696F6E546F6B656E00000000000002120200007872004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000002120200024C000B63726564656E7469616C737400124C6A6176612F6C616E672F4F626A6563743B4C00097072696E636970616C71007E0005787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C7371007E0005787001737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00077870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000017704000000017372002E6F72672E72732E636F72652E73656375726974792E6163636573732E52734772616E746564417574686F7269747900000000000002120200064C00046A7362687400124C6A6176612F6C616E672F537472696E673B4C00046A736A627400134C6A6176612F6C616E672F496E74656765723B4C00046A736C7871007E00114C00046A736D6371007E00104C00046A736E6D71007E00104C00046A73786871007E00117870740009523030303030303031737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000071007E001674000FE7B3BBE7BB9FE7AEA1E79086E5919874000D524F4C455F53595341444D494E71007E00167871007E000E737200486F72672E737072696E676672616D65776F726B2E73656375726974792E7765622E61757468656E7469636174696F6E2E57656241757468656E7469636174696F6E44657461696C7300000000000002120200024C000D72656D6F74654164647265737371007E00104C000973657373696F6E496471007E001078707400093132372E302E302E3170707372002F6F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E55736572000000000000021202000C49000473716C784C000B617574686F72697469657374000F4C6A6176612F7574696C2F5365743B4C0004626D626871007E00104C0004626D636A71007E00104C0004626D6C6A71007E00104C0004626D6D6371007E00104C0005646570747371007E000A4C0004646C6D6371007E00104C0004646C6D6D71007E00104C0008757365726E616D6571007E00104C00047968626871007E00104C000479686D6371007E0010787000000000737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E000B737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200436F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E5573657224417574686F72697479436F6D70617261746F720000000000000212020000787077040000000171007E0012787400094430303030303030317400013074000944303030303030303174000FE7B3BBE7BB9FE6A0B9E69CBAE69E847371007E000D000000007704000000007874000873797361646D696E7074000873797361646D696E74000A5530303030303030303074000FE7B3BBE7BB9FE7AEA1E79086E59198);
INSERT INTO `sys_session_attributes` VALUES ('bd9104dd75d24c838f5ee2b07bed2d7d', 'SPRING_SECURITY_CONTEXT', 0xACED00057372003D6F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E636F6E746578742E5365637572697479436F6E74657874496D706C00000000000002120200014C000E61757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B7870737200396F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E527341757468656E7469636174696F6E546F6B656E00000000000002120200007872004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000002120200024C000B63726564656E7469616C737400124C6A6176612F6C616E672F4F626A6563743B4C00097072696E636970616C71007E0005787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C7371007E0005787001737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00077870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000017704000000017372002E6F72672E72732E636F72652E73656375726974792E6163636573732E52734772616E746564417574686F7269747900000000000002120200064C00046A7362687400124C6A6176612F6C616E672F537472696E673B4C00046A736A627400134C6A6176612F6C616E672F496E74656765723B4C00046A736C7871007E00114C00046A736D6371007E00104C00046A736E6D71007E00104C00046A73786871007E00117870740009523030303030303042737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000071007E0016740009E7AEA1E79086E5919874000A524F4C455F41444D494E7371007E0014000000017871007E000E737200486F72672E737072696E676672616D65776F726B2E73656375726974792E7765622E61757468656E7469636174696F6E2E57656241757468656E7469636174696F6E44657461696C7300000000000002120200024C000D72656D6F74654164647265737371007E00104C000973657373696F6E496471007E001078707400093132372E302E302E3170707372002F6F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E55736572000000000000021202000C49000473716C784C000B617574686F72697469657374000F4C6A6176612F7574696C2F5365743B4C0004626D626871007E00104C0004626D636A71007E00104C0004626D6C6A71007E00104C0004626D6D6371007E00104C0005646570747371007E000A4C0004646C6D6371007E00104C0004646C6D6D71007E00104C0008757365726E616D6571007E00104C00047968626871007E00104C000479686D6371007E0010787000000000737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E000B737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200436F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E5573657224417574686F72697479436F6D70617261746F720000000000000212020000787077040000000171007E001278740009443030303030303032740001317400134430303030303030312C44303030303030303274000FE79FB3E5AEB6E5BA84E585ACE58FB87371007E000D0000000077040000000078740008736A7A61646D696E70740008736A7A61646D696E74000A55303030303030303042740012E79FB3E5AEB6E5BA84E7AEA1E79086E59198);
INSERT INTO `sys_session_attributes` VALUES ('fe1d68a69d2643618d28b0992a75c868', 'SPRING_SECURITY_CONTEXT', 0xACED00057372003D6F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E636F6E746578742E5365637572697479436F6E74657874496D706C00000000000002120200014C000E61757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B7870737200396F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E527341757468656E7469636174696F6E546F6B656E00000000000002120200007872004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000002120200024C000B63726564656E7469616C737400124C6A6176612F6C616E672F4F626A6563743B4C00097072696E636970616C71007E0005787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C7371007E0005787001737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00077870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000017704000000017372002E6F72672E72732E636F72652E73656375726974792E6163636573732E52734772616E746564417574686F7269747900000000000002120200064C00046A7362687400124C6A6176612F6C616E672F537472696E673B4C00046A736A627400134C6A6176612F6C616E672F496E74656765723B4C00046A736C7871007E00114C00046A736D6371007E00104C00046A736E6D71007E00104C00046A73786871007E00117870740009523030303030303031737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000071007E001674000FE7B3BBE7BB9FE7AEA1E79086E5919874000D524F4C455F53595341444D494E71007E00167871007E000E737200486F72672E737072696E676672616D65776F726B2E73656375726974792E7765622E61757468656E7469636174696F6E2E57656241757468656E7469636174696F6E44657461696C7300000000000002120200024C000D72656D6F74654164647265737371007E00104C000973657373696F6E496471007E0010787074000F303A303A303A303A303A303A303A317400203061323338646538646462363432333638323562623739333833616462313731707372002F6F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E55736572000000000000021202000C49000473716C784C000B617574686F72697469657374000F4C6A6176612F7574696C2F5365743B4C0004626D626871007E00104C0004626D636A71007E00104C0004626D6C6A71007E00104C0004626D6D6371007E00104C0005646570747371007E000A4C0004646C6D6371007E00104C0004646C6D6D71007E00104C0008757365726E616D6571007E00104C00047968626871007E00104C000479686D6371007E0010787000000000737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E000B737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200436F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E5573657224417574686F72697479436F6D70617261746F720000000000000212020000787077040000000171007E0012787400094430303030303030317400013074000944303030303030303174000FE7B3BBE7BB9FE6A0B9E69CBAE69E847371007E000D000000007704000000007874000873797361646D696E7074000873797361646D696E74000A5530303030303030303074000FE7B3BBE7BB9FE7AEA1E79086E59198);
INSERT INTO `sys_session_attributes` VALUES ('fac235c3dd9841b79edccd95839a3ac3', 'SPRING_SECURITY_CONTEXT', 0xACED00057372003D6F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E636F6E746578742E5365637572697479436F6E74657874496D706C00000000000002120200014C000E61757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B7870737200396F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E527341757468656E7469636174696F6E546F6B656E00000000000002120200007872004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000002120200024C000B63726564656E7469616C737400124C6A6176612F6C616E672F4F626A6563743B4C00097072696E636970616C71007E0005787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C7371007E0005787001737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00077870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000017704000000017372002E6F72672E72732E636F72652E73656375726974792E6163636573732E52734772616E746564417574686F7269747900000000000002120200064C00046A7362687400124C6A6176612F6C616E672F537472696E673B4C00046A736A627400134C6A6176612F6C616E672F496E74656765723B4C00046A736C7871007E00114C00046A736D6371007E00104C00046A736E6D71007E00104C00046A73786871007E00117870740009523030303030303031737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000071007E001674000FE7B3BBE7BB9FE7AEA1E79086E5919874000D524F4C455F53595341444D494E71007E00167871007E000E737200486F72672E737072696E676672616D65776F726B2E73656375726974792E7765622E61757468656E7469636174696F6E2E57656241757468656E7469636174696F6E44657461696C7300000000000002120200024C000D72656D6F74654164647265737371007E00104C000973657373696F6E496471007E0010787074000F303A303A303A303A303A303A303A3170707372002F6F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E55736572000000000000021202000C49000473716C784C000B617574686F72697469657374000F4C6A6176612F7574696C2F5365743B4C0004626D626871007E00104C0004626D636A71007E00104C0004626D6C6A71007E00104C0004626D6D6371007E00104C0005646570747371007E000A4C0004646C6D6371007E00104C0004646C6D6D71007E00104C0008757365726E616D6571007E00104C00047968626871007E00104C000479686D6371007E0010787000000000737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E000B737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200436F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E5573657224417574686F72697479436F6D70617261746F720000000000000212020000787077040000000171007E0012787400094430303030303030317400013074000944303030303030303174000FE7B3BBE7BB9FE6A0B9E69CBAE69E847371007E000D000000007704000000007874000873797361646D696E7074000873797361646D696E74000A5530303030303030303074000FE7B3BBE7BB9FE7AEA1E79086E59198);
INSERT INTO `sys_session_attributes` VALUES ('6b524fbe68364b5aa6f4e1bbb0f14e55', 'SPRING_SECURITY_CONTEXT', 0xACED00057372003D6F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E636F6E746578742E5365637572697479436F6E74657874496D706C00000000000002120200014C000E61757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B7870737200396F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E527341757468656E7469636174696F6E546F6B656E00000000000002120200007872004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000002120200024C000B63726564656E7469616C737400124C6A6176612F6C616E672F4F626A6563743B4C00097072696E636970616C71007E0005787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C7371007E0005787001737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00077870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000017704000000017372002E6F72672E72732E636F72652E73656375726974792E6163636573732E52734772616E746564417574686F7269747900000000000002120200064C00046A7362687400124C6A6176612F6C616E672F537472696E673B4C00046A736A627400134C6A6176612F6C616E672F496E74656765723B4C00046A736C7871007E00114C00046A736D6371007E00104C00046A736E6D71007E00104C00046A73786871007E00117870740009523030303030303031737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000071007E001674000FE7B3BBE7BB9FE7AEA1E79086E5919874000D524F4C455F53595341444D494E71007E00167871007E000E737200486F72672E737072696E676672616D65776F726B2E73656375726974792E7765622E61757468656E7469636174696F6E2E57656241757468656E7469636174696F6E44657461696C7300000000000002120200024C000D72656D6F74654164647265737371007E00104C000973657373696F6E496471007E0010787074000F303A303A303A303A303A303A303A3170707372002F6F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E55736572000000000000021202000C49000473716C784C000B617574686F72697469657374000F4C6A6176612F7574696C2F5365743B4C0004626D626871007E00104C0004626D636A71007E00104C0004626D6C6A71007E00104C0004626D6D6371007E00104C0005646570747371007E000A4C0004646C6D6371007E00104C0004646C6D6D71007E00104C0008757365726E616D6571007E00104C00047968626871007E00104C000479686D6371007E0010787000000000737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E000B737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200436F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E5573657224417574686F72697479436F6D70617261746F720000000000000212020000787077040000000171007E0012787400094430303030303030317400013074000944303030303030303174000FE7B3BBE7BB9FE6A0B9E69CBAE69E847371007E000D000000007704000000007874000873797361646D696E7074000873797361646D696E74000A5530303030303030303074000FE7B3BBE7BB9FE7AEA1E79086E59198);
INSERT INTO `sys_session_attributes` VALUES ('bba69cbfdd874fe49761ebdc91341f40', 'SPRING_SECURITY_CONTEXT', 0xACED00057372003D6F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E636F6E746578742E5365637572697479436F6E74657874496D706C00000000000002120200014C000E61757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B7870737200396F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E527341757468656E7469636174696F6E546F6B656E00000000000002120200007872004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000002120200024C000B63726564656E7469616C737400124C6A6176612F6C616E672F4F626A6563743B4C00097072696E636970616C71007E0005787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C7371007E0005787001737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00077870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A657870000000017704000000017372002E6F72672E72732E636F72652E73656375726974792E6163636573732E52734772616E746564417574686F7269747900000000000002120200064C00046A7362687400124C6A6176612F6C616E672F537472696E673B4C00046A736A627400134C6A6176612F6C616E672F496E74656765723B4C00046A736C7871007E00114C00046A736D6371007E00104C00046A736E6D71007E00104C00046A73786871007E00117870740009523030303030303031737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000071007E001674000FE7B3BBE7BB9FE7AEA1E79086E5919874000D524F4C455F53595341444D494E71007E00167871007E000E737200486F72672E737072696E676672616D65776F726B2E73656375726974792E7765622E61757468656E7469636174696F6E2E57656241757468656E7469636174696F6E44657461696C7300000000000002120200024C000D72656D6F74654164647265737371007E00104C000973657373696F6E496471007E001078707400093132372E302E302E3170707372002F6F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E55736572000000000000021202000C49000473716C784C000B617574686F72697469657374000F4C6A6176612F7574696C2F5365743B4C0004626D626871007E00104C0004626D636A71007E00104C0004626D6C6A71007E00104C0004626D6D6371007E00104C0005646570747371007E000A4C0004646C6D6371007E00104C0004646C6D6D71007E00104C0008757365726E616D6571007E00104C00047968626871007E00104C000479686D6371007E0010787000000000737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E000B737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200436F72672E72732E636F72652E73656375726974792E61757468656E7469636174696F6E2E52734C6F67696E5573657224417574686F72697479436F6D70617261746F720000000000000212020000787077040000000171007E0012787400094430303030303030317400013074000944303030303030303174000FE7B3BBE7BB9FE6A0B9E69CBAE69E847371007E000D000000007704000000007874000873797361646D696E7074000873797361646D696E74000A5530303030303030303074000FE7B3BBE7BB9FE7AEA1E79086E59198);

SET FOREIGN_KEY_CHECKS = 1;
