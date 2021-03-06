/*
 Navicat Premium Data Transfer

 Source Server         :  本地
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : pre

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

*/

CREATE DATABASE IF NOT EXISTS test_db_char
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for social_UserConnection
-- ----------------------------
DROP TABLE IF EXISTS `social_UserConnection`;
CREATE TABLE `social_UserConnection` (
                                         `userId` varchar(255) NOT NULL,
                                         `providerId` varchar(255) NOT NULL,
                                         `providerUserId` varchar(255) NOT NULL,
                                         `rank` int(11) NOT NULL,
                                         `displayName` varchar(255) DEFAULT NULL,
                                         `profileUrl` varchar(512) DEFAULT NULL,
                                         `imageUrl` varchar(512) DEFAULT NULL,
                                         `accessToken` varchar(512) NOT NULL,
                                         `secret` varchar(512) DEFAULT NULL,
                                         `refreshToken` varchar(512) DEFAULT NULL,
                                         `expireTime` bigint(20) DEFAULT NULL,
                                         `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                                         PRIMARY KEY (`userId`,`providerId`,`providerUserId`) USING BTREE,
                                         UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='社交登录表';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
                            `dept_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
                            `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '部门名称',
                            `sort` int(11) DEFAULT NULL COMMENT '排序',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
                            `parent_id` int(11) DEFAULT NULL COMMENT '上级部门',
                            `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                            PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, '信息服务中心', 0, '2019-04-21 22:53:33', '2019-10-08 10:57:59', '0', 0, 1);
INSERT INTO `sys_dept` VALUES (2, '事业四部', 0, '2019-04-21 22:53:57', '2019-12-14 15:26:45', '0', 1, 1);
INSERT INTO `sys_dept` VALUES (3, '人事部', 0, '2019-04-21 22:54:10', '2019-12-10 16:48:03', '0', 1, 1);
INSERT INTO `sys_dept` VALUES (4, '数据部', 0, '2019-04-21 22:54:46', '2019-10-08 10:58:01', '0', 1, 1);
INSERT INTO `sys_dept` VALUES (5, '基础研究部', 4, '2019-04-30 14:31:46', '2019-12-14 15:15:42', '0', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
                            `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
                            `dict_name` varchar(100) NOT NULL COMMENT '名称',
                            `dict_code` varchar(50) DEFAULT NULL COMMENT '字典编码',
                            `description` varchar(100) DEFAULT NULL COMMENT '描述',
                            `sort` int(4) DEFAULT NULL COMMENT '排序（升序）',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `remark` varchar(50) DEFAULT NULL COMMENT '备注信息',
                            `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
                            `tenant_id` int(11) DEFAULT NULL COMMENT '租户Id',
                            PRIMARY KEY (`id`),
                            KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, '用户状态', 'lock_flag', '用户是否正常还是锁定', NULL, '2019-12-16 13:35:43', '2019-12-17 21:24:29', NULL, '0', 1);
INSERT INTO `sys_dict` VALUES (2, '菜单类型', 'menu_type', '菜单类型 （类型   0：目录   1：菜单   2：按钮）', NULL, '2019-12-16 13:42:46', '2019-12-17 21:24:29', NULL, '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
                                 `id` varchar(50) CHARACTER SET utf8 NOT NULL,
                                 `dict_id` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典id',
                                 `item_text` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典项文本',
                                 `item_value` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典项值',
                                 `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
                                 `status` int(11) DEFAULT NULL COMMENT '状态（1启用 0不启用）',
                                 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `index_table_dict_id` (`dict_id`) USING BTREE,
                                 KEY `index_table_dict_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典详情表';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_item` VALUES ('0c8f86876bfc7c59a5236010fdcaa07e', '2', '目录', '1', '', NULL, '2019-12-16 13:57:39', '2019-12-16 13:57:39');
INSERT INTO `sys_dict_item` VALUES ('3fe7ad23294384de45197f3379b8d658', '1', '锁定', '1', '0-正常，1-锁定', NULL, '2019-12-16 13:39:56', '2019-12-16 13:39:56');
INSERT INTO `sys_dict_item` VALUES ('5ace75b3caf31b86efa50430954d624f', '2', '按钮', '3', '', NULL, '2019-12-16 13:57:55', '2019-12-16 13:57:55');
INSERT INTO `sys_dict_item` VALUES ('6ea98d652a06220c99b9468ead68e6f9', '2', '菜单', '1', '', NULL, '2019-12-16 13:57:48', '2019-12-16 13:57:48');
INSERT INTO `sys_dict_item` VALUES ('f27a639dee243eef860f453c2ab8547e', '1', '正常', '0', '0-正常，1-锁定', NULL, '2019-12-16 13:39:45', '2019-12-16 13:39:45');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
                           `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `ip` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作IP',
                           `type` int(3) DEFAULT NULL COMMENT '操作类型 1 操作记录2异常记录',
                           `user_name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人',
                           `description` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作描述',
                           `action_method` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
                           `action_url` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                           `params` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求参数',
                           `browser` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '浏览器',
                           `class_path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类路径',
                           `request_method` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
                           `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
                           `finish_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
                           `consuming_time` bigint(11) DEFAULT NULL COMMENT '消耗时间',
                           `ex_desc` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '异常详情信息',
                           `ex_detail` text COLLATE utf8mb4_bin COMMENT '异常描述',
                           `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                           `location` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作地点',
                           `os` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作系统',
                           PRIMARY KEY (`id`),
                           KEY `index_type` (`type`) USING BTREE COMMENT '日志类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统日志';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
                            `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                            `name` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
                            `perms` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单权限标识',
                            `path` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '前端跳转URL',
                            `component` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单组件',
                            `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
                            `icon` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
                            `sort` int(11) DEFAULT '1' COMMENT '排序',
                            `type` char(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单类型 （类型   0：目录   1：菜单   2：按钮）',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
                            `is_frame` tinyint(1) DEFAULT NULL COMMENT '是否为外链',
                            PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '权限管理', '', 'admin', '', 0, 'authority', 0, '0', '2019-04-21 22:45:08', '2019-05-05 20:20:31', '0', 1);
INSERT INTO `sys_menu` VALUES (2, '用户管理', '', 'user', 'admin/user', 1, 'user', 1, '1', '2019-04-21 22:49:22', '2019-05-12 19:02:34', '0', 1);
INSERT INTO `sys_menu` VALUES (3, '部门管理', '', 'dept', 'admin/dept', 1, 'dept', 2, '1', '2019-04-21 22:52:11', '2019-05-12 21:25:14', '0', 1);
INSERT INTO `sys_menu` VALUES (5, '用户新增', 'sys:user:add', '', NULL, 2, '', 0, '2', '2019-04-22 13:09:11', '2019-06-08 11:21:07', '0', 1);
INSERT INTO `sys_menu` VALUES (6, '系统管理', '', 'sys', '', 0, 'sys', 1, '0', '2019-04-22 23:48:02', '2019-05-06 22:44:51', '0', 1);
INSERT INTO `sys_menu` VALUES (7, '操作日志', '', 'log', 'sys/log', 6, 'log', 1, '1', '2019-04-22 23:59:40', '2019-09-23 15:58:22', '0', 1);
INSERT INTO `sys_menu` VALUES (11, '部门新增', 'sys:dept:add', '', NULL, 3, '', 0, '2', '2019-04-25 11:09:50', '2019-06-08 13:13:45', '0', 1);
INSERT INTO `sys_menu` VALUES (13, '角色管理', '', 'role', 'admin/role', 1, 'peoples', 1, '1', '2019-04-29 21:08:28', '2019-05-05 20:20:53', '0', 1);
INSERT INTO `sys_menu` VALUES (14, '用户修改', 'sys:user:update', '', NULL, 2, '', 0, '2', '2019-04-30 23:43:31', '2019-06-08 11:22:23', '0', 1);
INSERT INTO `sys_menu` VALUES (15, '角色新增', 'sys:role:add', '', NULL, 13, '', 0, '2', '2019-05-01 08:49:21', '2019-06-09 16:39:48', '0', 1);
INSERT INTO `sys_menu` VALUES (16, '菜单管理', '', 'menu', 'admin/menu', 1, 'menu', 3, '1', '2019-05-03 15:26:58', '2019-05-05 20:20:56', '0', 1);
INSERT INTO `sys_menu` VALUES (27, '日志删除', 'sys:log:delete', '', '', 7, '', 0, '2', '2019-05-06 22:47:47', '2019-06-08 13:15:05', '0', 1);
INSERT INTO `sys_menu` VALUES (28, '菜单增加', 'sys:menu:add', '', '', 16, '', 0, '2', '2019-05-08 16:09:43', '2019-06-08 13:14:02', '0', 1);
INSERT INTO `sys_menu` VALUES (29, '菜单修改', 'sys:menu:update', '', '', 16, '', 0, '2', '2019-05-08 16:10:06', '2019-06-08 13:14:05', '0', 1);
INSERT INTO `sys_menu` VALUES (30, '部门修改', 'sys:dept:update', '', '', 3, '', 0, '2', '2019-05-08 23:49:54', '2019-06-08 13:13:49', '0', 1);
INSERT INTO `sys_menu` VALUES (31, '部门删除', 'sys:dept:delete', '', '', 3, '', 0, '2', '2019-05-08 23:53:41', '2019-06-08 13:13:52', '0', 1);
INSERT INTO `sys_menu` VALUES (33, '用户查看', 'sys:user:view', '', '', 2, '', 0, '2', '2019-05-12 18:59:46', '2019-06-08 11:23:01', '0', 1);
INSERT INTO `sys_menu` VALUES (34, '角色修改', 'sys:role:update', '', '', 13, '', 0, '2', '2019-05-12 19:05:03', '2019-06-08 13:13:29', '0', 1);
INSERT INTO `sys_menu` VALUES (35, '用户删除', 'sys:user:delete', '', '', 2, '', 0, '2', '2019-05-12 19:08:13', '2019-06-08 11:23:07', '0', 1);
INSERT INTO `sys_menu` VALUES (36, '菜单删除', 'sys:menu:delete', '', '', 16, '', 0, '2', '2019-05-12 19:10:02', '2019-06-08 13:14:09', '0', 1);
INSERT INTO `sys_menu` VALUES (37, '角色删除', 'sys:role:delete', '', '', 13, '', 0, '2', '2019-05-12 19:11:14', '2019-06-08 13:13:34', '0', 1);
INSERT INTO `sys_menu` VALUES (38, '角色查看', 'sys:role:view', '', '', 13, '', 0, '2', '2019-05-12 19:11:37', '2019-06-08 13:13:37', '0', 1);
INSERT INTO `sys_menu` VALUES (43, '数据字典', '', 'dict', 'sys/dict', 6, 'tag', 0, '1', '2019-05-16 18:17:32', '2019-12-16 15:30:37', '0', 1);
INSERT INTO `sys_menu` VALUES (44, '部门查看', 'sys:dept:view', '', '', 3, '', 0, '2', '2019-06-07 20:50:31', '2019-06-08 13:13:55', '0', 1);
INSERT INTO `sys_menu` VALUES (45, '字典查看', 'sys:dipt:view', '', '', 43, '', 0, '2', '2019-06-07 20:55:42', '2019-06-08 13:14:56', '0', 1);
INSERT INTO `sys_menu` VALUES (46, '菜单查看', 'sys:menu:view', '', '', 16, '', 0, '2', '2019-06-08 13:14:32', NULL, '0', 1);
INSERT INTO `sys_menu` VALUES (47, '修改密码', 'sys:user:updatePass', '', '', 2, '', 0, '2', '2019-06-15 09:43:20', '2019-06-15 09:43:20', '0', 1);
INSERT INTO `sys_menu` VALUES (48, '修改邮箱', 'sys:user:updateEmail', '', '', 2, '', 0, '2', '2019-06-15 09:43:58', '2019-06-15 09:43:58', '0', 1);
INSERT INTO `sys_menu` VALUES (51, '社交账号管理', '', 'social', 'admin/social', 1, 'peoples', 6, '1', '2019-07-19 13:22:44', '2019-07-19 13:24:45', '0', 1);
INSERT INTO `sys_menu` VALUES (52, '解绑账号', 'sys:social:untied', '', '', 51, '', 0, '2', '2019-07-22 13:06:53', '2019-07-22 13:06:53', '0', 1);
INSERT INTO `sys_menu` VALUES (53, '代码生成', '', '/codegen', 'sys/codegen', 6, 'clipboard', 0, '1', '2019-07-25 12:55:37', '2019-08-02 14:52:04', '0', 1);
INSERT INTO `sys_menu` VALUES (54, '社交查看', 'sys:social:view', '', '', 51, '', 0, '2', '2019-08-03 16:16:46', '2019-08-03 16:16:46', '0', 1);
INSERT INTO `sys_menu` VALUES (55, '代码生成', 'sys:codegen:codegen', '', '', 53, '', 0, '2', '2019-08-10 00:08:20', '2019-08-10 00:08:20', '0', 1);
INSERT INTO `sys_menu` VALUES (56, '租户管理', '', 'tenant', 'admin/tenant', 1, 'guide', 5, '1', '2019-08-10 10:52:26', '2019-08-10 10:54:11', '0', 1);
INSERT INTO `sys_menu` VALUES (57, '流程管理', '', 'activiti', '', 0, 'documentation', 2, '0', '2019-10-08 11:03:22', '2019-10-08 11:03:22', '0', 1);
INSERT INTO `sys_menu` VALUES (58, '模型管理', '', 'model', 'activiti/model', 57, 'chart', 0, '1', '2019-10-08 11:06:51', '2019-10-08 11:06:51', '0', 1);
INSERT INTO `sys_menu` VALUES (59, '流程部署', '', 'processDeployment', 'activiti/processDeployment', 57, 'blog', 0, '1', '2019-10-08 13:48:20', '2019-10-08 13:49:02', '0', 1);
INSERT INTO `sys_menu` VALUES (61, '我的流程', '', 'process', '', 0, 'excel', 0, '0', '2019-10-11 10:39:00', '2019-10-11 10:39:00', '0', 1);
INSERT INTO `sys_menu` VALUES (62, '发起流程', '', 'processList', 'process/processList', 61, 'documentation', 0, '1', '2019-10-11 10:49:11', '2019-10-11 10:49:11', '0', 1);
INSERT INTO `sys_menu` VALUES (63, '待签任务', '', 'pendingTask', 'process/pendingTask', 61, 'excel', 0, '1', '2019-10-11 10:54:12', '2019-10-11 10:54:12', '0', 1);
INSERT INTO `sys_menu` VALUES (64, '待办任务', '', 'upcomingTask', 'process/upcomingTask', 61, 'eye-open', 0, '1', '2019-10-11 10:55:46', '2019-10-11 10:55:46', '0', 1);
INSERT INTO `sys_menu` VALUES (65, '已发任务', '', 'deliveredTask', 'process/deliveredTask', 61, 'drag', 0, '1', '2019-10-11 10:57:15', '2019-10-11 10:57:15', '0', 1);
INSERT INTO `sys_menu` VALUES (66, '已完任务', '', 'completedTask', 'process/completedTask', 61, 'clipboard', 0, '1', '2019-10-11 10:58:38', '2019-10-11 10:59:19', '0', 1);
INSERT INTO `sys_menu` VALUES (67, '查看日志', 'sys:log:view', '', '', 7, '', 0, '2', '2019-12-10 16:49:05', '2019-12-10 16:49:05', '0', 1);
INSERT INTO `sys_menu` VALUES (68, '添加字典', 'sys:dict:add', '', '', 43, '', 0, '2', '2019-12-15 21:16:09', '2019-12-15 21:16:09', '0', 1);
INSERT INTO `sys_menu` VALUES (69, '添加字典详情', 'sys:dictItem:add', '', '', 43, '', 0, '2', '2019-12-15 22:08:01', '2019-12-15 22:08:01', '0', 1);
INSERT INTO `sys_menu` VALUES (70, '更新字典详情', 'sys:dictItem:edit', '', '', 43, '', 0, '2', '2019-12-16 12:19:53', '2019-12-16 12:19:53', '0', 1);
INSERT INTO `sys_menu` VALUES (71, '更新字典', 'sys:dict:edit', '', '', 43, '', 0, '2', '2019-12-16 13:44:01', '2019-12-16 13:44:01', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色主键',
                            `role_name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
                            `role_code` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色标识',
                            `role_desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色描述',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
                            `ds_type` int(1) DEFAULT NULL COMMENT '数据权限类型',
                            `ds_scope` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据权限范围 1 全部 2 本级 3 本级以及子级 4 自定义',
                            `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                            PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ADMIN_ROLE', '超级管理员', '2019-04-22 21:53:38', '2019-12-15 21:17:07', '0', 1, '4,5,6,7,12,14,16', 1);
INSERT INTO `sys_role` VALUES (2, '部门管理员', 'DEPART_ROLE', '部门管理员', '2019-04-24 21:11:28', '2019-10-08 11:04:52', '0', 1, '2', 1);
INSERT INTO `sys_role` VALUES (3, '一般用户', 'GERERAL_ROLE', '一般用户', '2019-08-03 15:52:36', '2019-12-14 17:57:38', '0', 4, '16,17,4,5,6,7,12,14', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
                                 `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
                                 `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
                                 `dept_id` int(20) DEFAULT NULL COMMENT '部门ID',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=306 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept` VALUES (1, 1, 1);
INSERT INTO `sys_role_dept` VALUES (2, 2, 2);
INSERT INTO `sys_role_dept` VALUES (3, 3, 3);
INSERT INTO `sys_role_dept` VALUES (4, 2, 4);
INSERT INTO `sys_role_dept` VALUES (5, 3, 5);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `role_id` int(11) NOT NULL COMMENT '角色ID',
                                 `menu_id` int(11) NOT NULL COMMENT '菜单ID',
                                 PRIMARY KEY (`id`),
                                 KEY `index_role_id` (`role_id`) USING BTREE COMMENT '角色Id',
                                 KEY `index_menu_id` (`menu_id`) USING BTREE COMMENT '菜单Id'
) ENGINE=InnoDB AUTO_INCREMENT=3232 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 3, 1);
INSERT INTO `sys_role_menu` VALUES (2, 3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 3, 33);
INSERT INTO `sys_role_menu` VALUES (4, 3, 13);
INSERT INTO `sys_role_menu` VALUES (5, 3, 38);
INSERT INTO `sys_role_menu` VALUES (6, 3, 3);
INSERT INTO `sys_role_menu` VALUES (7, 3, 44);
INSERT INTO `sys_role_menu` VALUES (8, 3, 16);
INSERT INTO `sys_role_menu` VALUES (9, 3, 17);
INSERT INTO `sys_role_menu` VALUES (10, 3, 41);
INSERT INTO `sys_role_menu` VALUES (11, 3, 6);
INSERT INTO `sys_role_menu` VALUES (12, 3, 7);
INSERT INTO `sys_role_menu` VALUES (13, 3, 18);
INSERT INTO `sys_role_menu` VALUES (14, 2, 1);
INSERT INTO `sys_role_menu` VALUES (15, 2, 2);
INSERT INTO `sys_role_menu` VALUES (16, 2, 5);
INSERT INTO `sys_role_menu` VALUES (17, 2, 14);
INSERT INTO `sys_role_menu` VALUES (18, 2, 33);
INSERT INTO `sys_role_menu` VALUES (19, 2, 35);
INSERT INTO `sys_role_menu` VALUES (20, 2, 47);
INSERT INTO `sys_role_menu` VALUES (21, 2, 48);
INSERT INTO `sys_role_menu` VALUES (22, 2, 13);
INSERT INTO `sys_role_menu` VALUES (23, 2, 15);
INSERT INTO `sys_role_menu` VALUES (24, 2, 34);
INSERT INTO `sys_role_menu` VALUES (25, 2, 37);
INSERT INTO `sys_role_menu` VALUES (26, 2, 38);
INSERT INTO `sys_role_menu` VALUES (27, 2, 3);
INSERT INTO `sys_role_menu` VALUES (28, 2, 11);
INSERT INTO `sys_role_menu` VALUES (29, 2, 30);
INSERT INTO `sys_role_menu` VALUES (30, 2, 31);
INSERT INTO `sys_role_menu` VALUES (31, 2, 44);
INSERT INTO `sys_role_menu` VALUES (32, 2, 16);
INSERT INTO `sys_role_menu` VALUES (33, 2, 28);
INSERT INTO `sys_role_menu` VALUES (34, 2, 29);
INSERT INTO `sys_role_menu` VALUES (35, 2, 36);
INSERT INTO `sys_role_menu` VALUES (36, 2, 46);
INSERT INTO `sys_role_menu` VALUES (37, 2, 56);
INSERT INTO `sys_role_menu` VALUES (38, 2, 72);
INSERT INTO `sys_role_menu` VALUES (39, 2, 52);
INSERT INTO `sys_role_menu` VALUES (40, 2, 54);
INSERT INTO `sys_role_menu` VALUES (41, 2, 62);
INSERT INTO `sys_role_menu` VALUES (42, 2, 63);
INSERT INTO `sys_role_menu` VALUES (43, 2, 64);
INSERT INTO `sys_role_menu` VALUES (44, 2, 65);
INSERT INTO `sys_role_menu` VALUES (45, 2, 66);
INSERT INTO `sys_role_menu` VALUES (46, 2, 6);
INSERT INTO `sys_role_menu` VALUES (47, 2, 43);
INSERT INTO `sys_role_menu` VALUES (48, 2, 45);
INSERT INTO `sys_role_menu` VALUES (49, 2, 68);
INSERT INTO `sys_role_menu` VALUES (50, 2, 69);
INSERT INTO `sys_role_menu` VALUES (51, 2, 70);
INSERT INTO `sys_role_menu` VALUES (52, 2, 71);
INSERT INTO `sys_role_menu` VALUES (53, 2, 53);
INSERT INTO `sys_role_menu` VALUES (54, 2, 55);
INSERT INTO `sys_role_menu` VALUES (55, 2, 7);
INSERT INTO `sys_role_menu` VALUES (56, 2, 27);
INSERT INTO `sys_role_menu` VALUES (57, 2, 67);
INSERT INTO `sys_role_menu` VALUES (58, 2, 58);
INSERT INTO `sys_role_menu` VALUES (59, 2, 59);
INSERT INTO `sys_role_menu` VALUES (60, 1, 1);
INSERT INTO `sys_role_menu` VALUES (61, 1, 2);
INSERT INTO `sys_role_menu` VALUES (62, 1, 3);
INSERT INTO `sys_role_menu` VALUES (63, 1, 5);
INSERT INTO `sys_role_menu` VALUES (64, 1, 6);
INSERT INTO `sys_role_menu` VALUES (65, 1, 7);
INSERT INTO `sys_role_menu` VALUES (66, 1, 11);
INSERT INTO `sys_role_menu` VALUES (67, 1, 13);
INSERT INTO `sys_role_menu` VALUES (68, 1, 14);
INSERT INTO `sys_role_menu` VALUES (69, 1, 15);
INSERT INTO `sys_role_menu` VALUES (70, 1, 16);
INSERT INTO `sys_role_menu` VALUES (71, 1, 27);
INSERT INTO `sys_role_menu` VALUES (72, 1, 28);
INSERT INTO `sys_role_menu` VALUES (73, 1, 29);
INSERT INTO `sys_role_menu` VALUES (74, 1, 30);
INSERT INTO `sys_role_menu` VALUES (75, 1, 31);
INSERT INTO `sys_role_menu` VALUES (76, 1, 33);
INSERT INTO `sys_role_menu` VALUES (77, 1, 34);
INSERT INTO `sys_role_menu` VALUES (78, 1, 35);
INSERT INTO `sys_role_menu` VALUES (79, 1, 36);
INSERT INTO `sys_role_menu` VALUES (80, 1, 37);
INSERT INTO `sys_role_menu` VALUES (81, 1, 38);
INSERT INTO `sys_role_menu` VALUES (82, 1, 43);
INSERT INTO `sys_role_menu` VALUES (83, 1, 44);
INSERT INTO `sys_role_menu` VALUES (84, 1, 45);
INSERT INTO `sys_role_menu` VALUES (85, 1, 46);
INSERT INTO `sys_role_menu` VALUES (86, 1, 47);
INSERT INTO `sys_role_menu` VALUES (87, 1, 48);
INSERT INTO `sys_role_menu` VALUES (88, 1, 51);
INSERT INTO `sys_role_menu` VALUES (89, 1, 52);
INSERT INTO `sys_role_menu` VALUES (90, 1, 54);
INSERT INTO `sys_role_menu` VALUES (91, 1, 57);
INSERT INTO `sys_role_menu` VALUES (92, 1, 58);
INSERT INTO `sys_role_menu` VALUES (93, 1, 59);
INSERT INTO `sys_role_menu` VALUES (94, 1, 61);
INSERT INTO `sys_role_menu` VALUES (95, 1, 62);
INSERT INTO `sys_role_menu` VALUES (96, 1, 63);
INSERT INTO `sys_role_menu` VALUES (97, 1, 64);
INSERT INTO `sys_role_menu` VALUES (98, 1, 65);
INSERT INTO `sys_role_menu` VALUES (99, 1, 66);
INSERT INTO `sys_role_menu` VALUES (100, 1, 67);
INSERT INTO `sys_role_menu` VALUES (101, 1, 68);
INSERT INTO `sys_role_menu` VALUES (102, 1, 69);
INSERT INTO `sys_role_menu` VALUES (103, 1, 70);
INSERT INTO `sys_role_menu` VALUES (104, 1, 71);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `username` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
                            `password` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
                            `dept_id` int(10) DEFAULT NULL COMMENT '部门ID',
                            `job_id` int(10) DEFAULT NULL COMMENT '岗位ID',
                            `email` varchar(25) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
                            `phone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
                            `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            `lock_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-锁定',
                            `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-删除',
                            `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                            PRIMARY KEY (`user_id`),
                            KEY `user_idx_dept_id` (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (4, 'admin', 'sm4EncryptECB', 6, 3, 'lihaodongmail@163.com', '17521296869', NULL, '2019-04-23 23:29:51', '2019-12-16 17:34:37', '0', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `user_id` int(10) NOT NULL COMMENT '用户ID',
                                 `role_id` int(10) NOT NULL COMMENT '角色ID',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
