/*
Navicat MySQL Data Transfer

Source Server         : that_day
Source Server Version : 50632
Source Host           : 192.168.220.130:3306
Source Database       : that_day

Target Server Type    : MYSQL
Target Server Version : 50632
File Encoding         : 65001

Date: 2020-08-31 15:08:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for code_column_config
-- ----------------------------
DROP TABLE IF EXISTS `code_column_config`;
CREATE TABLE `code_column_config` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) DEFAULT NULL,
  `column_name` varchar(255) DEFAULT NULL,
  `column_type` varchar(255) DEFAULT NULL,
  `dict_name` varchar(255) DEFAULT NULL,
  `extra` varchar(255) DEFAULT NULL,
  `form_show` bit(1) DEFAULT NULL,
  `form_type` varchar(255) DEFAULT NULL,
  `key_type` varchar(255) DEFAULT NULL,
  `list_show` bit(1) DEFAULT NULL,
  `not_null` bit(1) DEFAULT NULL,
  `query_type` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `date_annotation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`column_id`) USING BTREE,
  KEY `idx_table_name` (`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成字段信息存储';

-- ----------------------------
-- Records of code_column_config
-- ----------------------------
INSERT INTO `code_column_config` VALUES ('187', 'sys_job', 'job_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('188', 'sys_job', 'name', 'varchar', null, '', '', null, 'UNI', '', '', null, '岗位名称', null);
INSERT INTO `code_column_config` VALUES ('189', 'sys_job', 'enabled', 'bit', null, '', '', null, 'MUL', '', '', null, '岗位状态', null);
INSERT INTO `code_column_config` VALUES ('190', 'sys_job', 'job_sort', 'int', null, '', '', null, '', '', '\0', null, '排序', null);
INSERT INTO `code_column_config` VALUES ('191', 'sys_job', 'create_by', 'varchar', null, '', '', null, '', '', '\0', null, '创建者', null);
INSERT INTO `code_column_config` VALUES ('192', 'sys_job', 'update_by', 'varchar', null, '', '', null, '', '', '\0', null, '更新者', null);
INSERT INTO `code_column_config` VALUES ('193', 'sys_job', 'create_time', 'datetime', null, '', '', null, '', '', '\0', null, '创建日期', null);
INSERT INTO `code_column_config` VALUES ('194', 'sys_job', 'update_time', 'datetime', null, '', '', null, '', '', '\0', null, '更新时间', null);
INSERT INTO `code_column_config` VALUES ('195', 'sys_dict_detail', 'detail_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('196', 'sys_dict_detail', 'dict_id', 'bigint', null, '', '', null, 'MUL', '', '\0', null, '字典id', null);
INSERT INTO `code_column_config` VALUES ('197', 'sys_dict_detail', 'label', 'varchar', null, '', '', null, '', '', '', null, '字典标签', null);
INSERT INTO `code_column_config` VALUES ('198', 'sys_dict_detail', 'value', 'varchar', null, '', '', null, '', '', '', null, '字典值', null);
INSERT INTO `code_column_config` VALUES ('199', 'sys_dict_detail', 'dict_sort', 'int', null, '', '', null, '', '', '\0', null, '排序', null);
INSERT INTO `code_column_config` VALUES ('200', 'sys_dict_detail', 'create_by', 'varchar', null, '', '', null, '', '', '\0', null, '创建者', null);
INSERT INTO `code_column_config` VALUES ('201', 'sys_dict_detail', 'update_by', 'varchar', null, '', '', null, '', '', '\0', null, '更新者', null);
INSERT INTO `code_column_config` VALUES ('202', 'sys_dict_detail', 'create_time', 'datetime', null, '', '', null, '', '', '\0', null, '创建日期', null);
INSERT INTO `code_column_config` VALUES ('203', 'sys_dict_detail', 'update_time', 'datetime', null, '', '', null, '', '', '\0', null, '更新时间', null);
INSERT INTO `code_column_config` VALUES ('210', 'sys_dict', 'dict_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('211', 'sys_dict', 'name', 'varchar', null, '', '', null, '', '', '', null, '字典名称', null);
INSERT INTO `code_column_config` VALUES ('212', 'sys_dict', 'description', 'varchar', null, '', '', null, '', '', '\0', null, '描述', null);
INSERT INTO `code_column_config` VALUES ('213', 'sys_dict', 'create_by', 'varchar', null, '', '', null, '', '', '\0', null, '创建者', null);
INSERT INTO `code_column_config` VALUES ('214', 'sys_dict', 'update_by', 'varchar', null, '', '', null, '', '', '\0', null, '更新者', null);
INSERT INTO `code_column_config` VALUES ('215', 'sys_dict', 'create_time', 'datetime', null, '', '', null, '', '', '\0', null, '创建日期', null);
INSERT INTO `code_column_config` VALUES ('216', 'sys_dict', 'update_time', 'datetime', null, '', '', null, '', '', '\0', null, '更新时间', null);
INSERT INTO `code_column_config` VALUES ('217', 'sys_users_roles', 'user_id', 'bigint', null, '', '', null, 'PRI', '', '', null, '用户ID', null);
INSERT INTO `code_column_config` VALUES ('218', 'sys_users_roles', 'role_id', 'bigint', null, '', '', null, 'PRI', '', '', null, '角色ID', null);
INSERT INTO `code_column_config` VALUES ('219', 'sys_menu', 'menu_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('220', 'sys_menu', 'pid', 'bigint', null, '', '', null, 'MUL', '', '\0', null, '上级菜单ID', null);
INSERT INTO `code_column_config` VALUES ('221', 'sys_menu', 'sub_count', 'int', null, '', '', null, '', '', '\0', null, '子菜单数目', null);
INSERT INTO `code_column_config` VALUES ('222', 'sys_menu', 'type', 'int', null, '', '', null, '', '', '\0', null, '菜单类型', null);
INSERT INTO `code_column_config` VALUES ('223', 'sys_menu', 'title', 'varchar', null, '', '', null, 'UNI', '', '\0', null, '菜单标题', null);
INSERT INTO `code_column_config` VALUES ('224', 'sys_menu', 'name', 'varchar', null, '', '', null, 'UNI', '', '\0', null, '组件名称', null);
INSERT INTO `code_column_config` VALUES ('225', 'sys_menu', 'component', 'varchar', null, '', '', null, '', '', '\0', null, '组件', null);
INSERT INTO `code_column_config` VALUES ('226', 'sys_menu', 'menu_sort', 'int', null, '', '', null, '', '', '\0', null, '排序', null);
INSERT INTO `code_column_config` VALUES ('227', 'sys_menu', 'icon', 'varchar', null, '', '', null, '', '', '\0', null, '图标', null);
INSERT INTO `code_column_config` VALUES ('228', 'sys_menu', 'path', 'varchar', null, '', '', null, '', '', '\0', null, '链接地址', null);
INSERT INTO `code_column_config` VALUES ('229', 'sys_menu', 'i_frame', 'bit', null, '', '', null, '', '', '\0', null, '是否外链', null);
INSERT INTO `code_column_config` VALUES ('230', 'sys_menu', 'cache', 'bit', null, '', '', null, '', '', '\0', null, '缓存', null);
INSERT INTO `code_column_config` VALUES ('231', 'sys_menu', 'hidden', 'bit', null, '', '', null, '', '', '\0', null, '隐藏', null);
INSERT INTO `code_column_config` VALUES ('232', 'sys_menu', 'permission', 'varchar', null, '', '', null, '', '', '\0', null, '权限', null);
INSERT INTO `code_column_config` VALUES ('233', 'sys_menu', 'create_by', 'varchar', null, '', '', null, '', '', '\0', null, '创建者', null);
INSERT INTO `code_column_config` VALUES ('234', 'sys_menu', 'update_by', 'varchar', null, '', '', null, '', '', '\0', null, '更新者', null);
INSERT INTO `code_column_config` VALUES ('235', 'sys_menu', 'create_time', 'datetime', null, '', '', null, '', '', '\0', null, '创建日期', null);
INSERT INTO `code_column_config` VALUES ('236', 'sys_menu', 'update_time', 'datetime', null, '', '', null, '', '', '\0', null, '更新时间', null);
INSERT INTO `code_column_config` VALUES ('237', 'sys_role', 'role_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('238', 'sys_role', 'name', 'varchar', null, '', '', null, 'UNI', '', '', null, '名称', null);
INSERT INTO `code_column_config` VALUES ('239', 'sys_role', 'level', 'int', null, '', '', null, '', '', '\0', null, '角色级别', null);
INSERT INTO `code_column_config` VALUES ('240', 'sys_role', 'description', 'varchar', null, '', '', null, '', '', '\0', null, '描述', null);
INSERT INTO `code_column_config` VALUES ('241', 'sys_role', 'data_scope', 'varchar', null, '', '', null, '', '', '\0', null, '数据权限', null);
INSERT INTO `code_column_config` VALUES ('242', 'sys_role', 'create_by', 'varchar', null, '', '', null, '', '', '\0', null, '创建者', null);
INSERT INTO `code_column_config` VALUES ('243', 'sys_role', 'update_by', 'varchar', null, '', '', null, '', '', '\0', null, '更新者', null);
INSERT INTO `code_column_config` VALUES ('244', 'sys_role', 'create_time', 'datetime', null, '', '', null, '', '', '\0', null, '创建日期', null);
INSERT INTO `code_column_config` VALUES ('245', 'sys_role', 'update_time', 'datetime', null, '', '', null, '', '', '\0', null, '更新时间', null);
INSERT INTO `code_column_config` VALUES ('246', 'sys_dept', 'dept_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('247', 'sys_dept', 'pid', 'bigint', null, '', '', null, 'MUL', '', '\0', null, '上级部门', null);
INSERT INTO `code_column_config` VALUES ('248', 'sys_dept', 'sub_count', 'int', null, '', '', null, '', '', '\0', null, '子部门数目', null);
INSERT INTO `code_column_config` VALUES ('249', 'sys_dept', 'name', 'varchar', null, '', '', null, '', '', '', null, '名称', null);
INSERT INTO `code_column_config` VALUES ('250', 'sys_dept', 'dept_sort', 'int', null, '', '', null, '', '', '\0', null, '排序', null);
INSERT INTO `code_column_config` VALUES ('251', 'sys_dept', 'enabled', 'bit', null, '', '', null, 'MUL', '', '', null, '状态', null);
INSERT INTO `code_column_config` VALUES ('252', 'sys_dept', 'create_by', 'varchar', null, '', '', null, '', '', '\0', null, '创建者', null);
INSERT INTO `code_column_config` VALUES ('253', 'sys_dept', 'update_by', 'varchar', null, '', '', null, '', '', '\0', null, '更新者', null);
INSERT INTO `code_column_config` VALUES ('254', 'sys_dept', 'create_time', 'datetime', null, '', '', null, '', '', '\0', null, '创建日期', null);
INSERT INTO `code_column_config` VALUES ('255', 'sys_dept', 'update_time', 'datetime', null, '', '', null, '', '', '\0', null, '更新时间', null);
INSERT INTO `code_column_config` VALUES ('256', 'code_column_config', 'column_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('257', 'code_column_config', 'table_name', 'varchar', null, '', '', null, 'MUL', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('258', 'code_column_config', 'column_name', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('259', 'code_column_config', 'column_type', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('260', 'code_column_config', 'dict_name', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('261', 'code_column_config', 'extra', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('262', 'code_column_config', 'form_show', 'bit', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('263', 'code_column_config', 'form_type', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('264', 'code_column_config', 'key_type', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('265', 'code_column_config', 'list_show', 'bit', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('266', 'code_column_config', 'not_null', 'bit', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('267', 'code_column_config', 'query_type', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('268', 'code_column_config', 'remark', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('269', 'code_column_config', 'date_annotation', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('270', 'code_gen_config', 'config_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('271', 'code_gen_config', 'table_name', 'varchar', null, '', '', null, 'MUL', '', '\0', null, '表名', null);
INSERT INTO `code_column_config` VALUES ('272', 'code_gen_config', 'author', 'varchar', null, '', '', null, '', '', '\0', null, '作者', null);
INSERT INTO `code_column_config` VALUES ('273', 'code_gen_config', 'cover', 'bit', null, '', '', null, '', '', '\0', null, '是否覆盖', null);
INSERT INTO `code_column_config` VALUES ('274', 'code_gen_config', 'module_name', 'varchar', null, '', '', null, '', '', '\0', null, '模块名称', null);
INSERT INTO `code_column_config` VALUES ('275', 'code_gen_config', 'pack', 'varchar', null, '', '', null, '', '', '\0', null, '至于哪个包下', null);
INSERT INTO `code_column_config` VALUES ('276', 'code_gen_config', 'path', 'varchar', null, '', '', null, '', '', '\0', null, '前端代码生成的路径', null);
INSERT INTO `code_column_config` VALUES ('277', 'code_gen_config', 'api_path', 'varchar', null, '', '', null, '', '', '\0', null, '前端Api文件路径', null);
INSERT INTO `code_column_config` VALUES ('278', 'code_gen_config', 'prefix', 'varchar', null, '', '', null, '', '', '\0', null, '表前缀', null);
INSERT INTO `code_column_config` VALUES ('279', 'code_gen_config', 'api_alias', 'varchar', null, '', '', null, '', '', '\0', null, '接口名称', null);
INSERT INTO `code_column_config` VALUES ('280', 'sys_log', 'log_id', 'bigint', null, 'auto_increment', '', null, 'PRI', '', '\0', null, 'ID', null);
INSERT INTO `code_column_config` VALUES ('281', 'sys_log', 'description', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('282', 'sys_log', 'log_type', 'varchar', null, '', '', null, 'MUL', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('283', 'sys_log', 'method', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('284', 'sys_log', 'params', 'text', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('285', 'sys_log', 'request_ip', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('286', 'sys_log', 'time', 'bigint', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('287', 'sys_log', 'username', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('288', 'sys_log', 'address', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('289', 'sys_log', 'browser', 'varchar', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('290', 'sys_log', 'exception_detail', 'text', null, '', '', null, '', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('291', 'sys_log', 'create_time', 'datetime', null, '', '', null, 'MUL', '', '\0', null, '', null);
INSERT INTO `code_column_config` VALUES ('292', 'sys_roles_depts', 'role_id', 'bigint', null, '', '', null, 'PRI', '', '', null, '', null);
INSERT INTO `code_column_config` VALUES ('293', 'sys_roles_depts', 'dept_id', 'bigint', null, '', '', null, 'PRI', '', '', null, '', null);
INSERT INTO `code_column_config` VALUES ('294', 'sys_roles_menus', 'menu_id', 'bigint', null, '', '', null, 'PRI', '', '', null, '菜单ID', null);
INSERT INTO `code_column_config` VALUES ('295', 'sys_roles_menus', 'role_id', 'bigint', null, '', '', null, 'PRI', '', '', null, '角色ID', null);

-- ----------------------------
-- Table structure for code_gen_config
-- ----------------------------
DROP TABLE IF EXISTS `code_gen_config`;
CREATE TABLE `code_gen_config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) DEFAULT NULL COMMENT '表名',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `cover` bit(1) DEFAULT NULL COMMENT '是否覆盖',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `pack` varchar(255) DEFAULT NULL COMMENT '至于哪个包下',
  `path` varchar(255) DEFAULT NULL COMMENT '前端代码生成的路径',
  `api_path` varchar(255) DEFAULT NULL COMMENT '前端Api文件路径',
  `prefix` varchar(255) DEFAULT NULL COMMENT '表前缀',
  `api_alias` varchar(255) DEFAULT NULL COMMENT '接口名称',
  PRIMARY KEY (`config_id`) USING BTREE,
  KEY `idx_table_name` (`table_name`(100))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成器配置';

-- ----------------------------
-- Records of code_gen_config
-- ----------------------------
INSERT INTO `code_gen_config` VALUES ('6', 'sys_dict_detail', 'King', '\0', 'zzx', 'com.mm.admin.modules', 'zzx', 'a\\', null, 'a');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级部门',
  `sub_count` int(5) DEFAULT '0' COMMENT '子部门数目',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `dept_sort` int(5) DEFAULT '999' COMMENT '排序',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE,
  KEY `inx_pid` (`pid`),
  KEY `inx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('2', '7', '0', '研发部', '3', '', null, 'admin', '2019-03-25 09:15:32', '2020-05-10 17:37:58');
INSERT INTO `sys_dept` VALUES ('7', null, '1', '华南分部', '0', '', null, 'admin', '2019-03-25 11:04:50', '2020-05-10 19:59:12');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', 'user_status', '用户状态', null, null, '2019-10-27 20:31:36', null);
INSERT INTO `sys_dict` VALUES ('4', 'dept_status', '部门状态', null, null, '2019-10-27 20:31:36', null);

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_id` bigint(11) DEFAULT NULL COMMENT '字典id',
  `label` varchar(255) NOT NULL COMMENT '字典标签',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `dict_sort` int(5) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`detail_id`) USING BTREE,
  KEY `FK5tpkputc6d9nboxojdbgnpmyb` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典详情';

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO `sys_dict_detail` VALUES ('1', '1', '激活', 'true', '1', null, null, '2019-10-27 20:31:36', null);
INSERT INTO `sys_dict_detail` VALUES ('2', '1', '禁用', 'false', '2', null, null, null, null);
INSERT INTO `sys_dict_detail` VALUES ('3', '4', '启用', 'true', '1', null, null, null, null);
INSERT INTO `sys_dict_detail` VALUES ('4', '4', '停用', 'false', '2', null, null, '2019-10-27 20:31:36', null);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `description` varchar(255) DEFAULT NULL,
  `log_type` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` text,
  `request_ip` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `exception_detail` text,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE,
  KEY `log_create_time_index` (`create_time`),
  KEY `inx_log_type` (`log_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3682 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int(5) DEFAULT '0' COMMENT '子菜单数目',
  `type` int(11) DEFAULT NULL COMMENT '菜单类型',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `menu_sort` int(5) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE KEY `uniq_title` (`title`),
  UNIQUE KEY `uniq_name` (`name`),
  KEY `inx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', null, '7', '0', '系统管理', null, null, '1', 'system', 'system', '\0', '\0', '\0', null, null, null, '2018-12-18 15:11:29', null);
INSERT INTO `sys_menu` VALUES ('2', '1', '3', '1', '用户管理', 'User', 'system/user/index', '2', 'peoples', 'user', '\0', '\0', '\0', 'user:list', null, null, '2018-12-18 15:14:44', null);
INSERT INTO `sys_menu` VALUES ('3', '1', '3', '1', '角色管理', 'Role', 'system/role/index', '3', 'role', 'role', '\0', '\0', '\0', 'roles:list', null, null, '2018-12-18 15:16:07', null);
INSERT INTO `sys_menu` VALUES ('5', '1', '3', '1', '菜单管理', 'Menu', 'system/menu/index', '5', 'menu', 'menu', '\0', '\0', '\0', 'menu:list', null, null, '2018-12-18 15:17:28', null);
INSERT INTO `sys_menu` VALUES ('7', '1', '0', '1', '操作日志', 'Log', 'monitor/log/index', '11', 'log', 'logs', '\0', '\0', '\0', null, null, 'admin', '2018-12-18 15:18:26', '2020-08-12 15:30:43');
INSERT INTO `sys_menu` VALUES ('9', '36', '0', '1', 'SQL监控', 'Sql', 'monitor/sql/index', '18', 'sqlMonitor', 'druid', '\0', '\0', '\0', null, null, 'admin', '2018-12-18 15:19:34', '2020-08-12 15:31:23');
INSERT INTO `sys_menu` VALUES ('11', '36', '0', '1', '图标库', 'Icons', 'components/icons/index', '51', 'icon', 'icon', '\0', '\0', '\0', null, null, 'admin', '2018-12-19 13:38:49', '2020-08-12 15:34:41');
INSERT INTO `sys_menu` VALUES ('30', '36', '0', '1', '代码生成', 'GeneratorIndex', 'generator/index', '12', 'dev', 'generator', '\0', '', '\0', null, null, 'admin', '2019-01-11 15:45:55', '2020-08-12 15:38:35');
INSERT INTO `sys_menu` VALUES ('32', '1', '0', '1', '异常日志', 'ErrorLog', 'monitor/log/errorLog', '12', 'error', 'errorLog', '\0', '\0', '\0', null, null, 'admin', '2019-01-13 13:49:03', '2020-08-12 15:31:03');
INSERT INTO `sys_menu` VALUES ('35', '1', '3', '1', '部门管理', 'Dept', 'system/dept/index', '6', 'dept', 'dept', '\0', '\0', '\0', 'dept:list', null, null, '2019-03-25 09:46:00', null);
INSERT INTO `sys_menu` VALUES ('36', null, '6', '0', '系统工具', null, '', '30', 'sys-tools', 'sys-tools', '\0', '\0', '\0', null, null, null, '2019-03-29 10:57:35', null);
INSERT INTO `sys_menu` VALUES ('39', '1', '3', '1', '字典管理', 'Dict', 'system/dict/index', '8', 'dictionary', 'dict', '\0', '\0', '\0', 'dict:list', null, null, '2019-04-10 11:49:04', null);
INSERT INTO `sys_menu` VALUES ('44', '2', '0', '2', '用户新增', null, '', '2', '', '', '\0', '\0', '\0', 'user:add', null, null, '2019-10-29 10:59:46', null);
INSERT INTO `sys_menu` VALUES ('45', '2', '0', '2', '用户编辑', null, '', '3', '', '', '\0', '\0', '\0', 'user:edit', null, null, '2019-10-29 11:00:08', null);
INSERT INTO `sys_menu` VALUES ('46', '2', '0', '2', '用户删除', null, '', '4', '', '', '\0', '\0', '\0', 'user:del', null, null, '2019-10-29 11:00:23', null);
INSERT INTO `sys_menu` VALUES ('48', '3', '0', '2', '角色创建', null, '', '2', '', '', '\0', '\0', '\0', 'roles:add', null, null, '2019-10-29 12:45:34', null);
INSERT INTO `sys_menu` VALUES ('49', '3', '0', '2', '角色修改', null, '', '3', '', '', '\0', '\0', '\0', 'roles:edit', null, null, '2019-10-29 12:46:16', null);
INSERT INTO `sys_menu` VALUES ('50', '3', '0', '2', '角色删除', null, '', '4', '', '', '\0', '\0', '\0', 'roles:del', null, null, '2019-10-29 12:46:51', null);
INSERT INTO `sys_menu` VALUES ('52', '5', '0', '2', '菜单新增', null, '', '2', '', '', '\0', '\0', '\0', 'menu:add', null, null, '2019-10-29 12:55:07', null);
INSERT INTO `sys_menu` VALUES ('53', '5', '0', '2', '菜单编辑', null, '', '3', '', '', '\0', '\0', '\0', 'menu:edit', null, null, '2019-10-29 12:55:40', null);
INSERT INTO `sys_menu` VALUES ('54', '5', '0', '2', '菜单删除', null, '', '4', '', '', '\0', '\0', '\0', 'menu:del', null, null, '2019-10-29 12:56:00', null);
INSERT INTO `sys_menu` VALUES ('56', '35', '0', '2', '部门新增', null, '', '2', '', '', '\0', '\0', '\0', 'dept:add', null, null, '2019-10-29 12:57:09', null);
INSERT INTO `sys_menu` VALUES ('57', '35', '0', '2', '部门编辑', null, '', '3', '', '', '\0', '\0', '\0', 'dept:edit', null, null, '2019-10-29 12:57:27', null);
INSERT INTO `sys_menu` VALUES ('58', '35', '0', '2', '部门删除', null, '', '4', '', '', '\0', '\0', '\0', 'dept:del', null, null, '2019-10-29 12:57:41', null);
INSERT INTO `sys_menu` VALUES ('64', '39', '0', '2', '字典新增', null, '', '2', '', '', '\0', '\0', '\0', 'dict:add', null, null, '2019-10-29 13:00:17', null);
INSERT INTO `sys_menu` VALUES ('65', '39', '0', '2', '字典编辑', null, '', '3', '', '', '\0', '\0', '\0', 'dict:edit', null, null, '2019-10-29 13:00:42', null);
INSERT INTO `sys_menu` VALUES ('66', '39', '0', '2', '字典删除', null, '', '4', '', '', '\0', '\0', '\0', 'dict:del', null, null, '2019-10-29 13:00:59', null);
INSERT INTO `sys_menu` VALUES ('82', '36', '0', '1', '生成配置', 'GeneratorConfig', 'generator/config', '12', 'dev', 'generator/config/:tableName', '\0', '', '', '', null, 'admin', '2019-11-17 20:08:56', '2020-08-12 15:38:48');
INSERT INTO `sys_menu` VALUES ('83', '36', '0', '1', '图表库', 'Echarts', 'components/Echarts', '50', 'chart', 'echarts', '\0', '', '\0', '', null, 'admin', '2019-11-21 09:04:32', '2020-08-12 15:34:32');
INSERT INTO `sys_menu` VALUES ('116', '36', '0', '1', '生成预览', 'Preview', 'generator/preview', '12', 'java', 'generator/preview/:tableName', '\0', '', '', null, null, 'admin', '2019-11-26 14:54:36', '2020-08-12 15:38:58');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `level` int(255) DEFAULT NULL COMMENT '角色级别',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '1', '-', '全部', null, 'admin', '2018-11-23 11:04:37', '2020-08-21 17:04:37');
INSERT INTO `sys_role` VALUES ('2', '普通用户', '2', '-', '自定义', null, 'admin', '2018-11-23 13:09:06', '2020-08-12 15:37:06');

-- ----------------------------
-- Table structure for sys_roles_depts
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE `sys_roles_depts` (
  `role_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE,
  KEY `FK7qg6itn5ajdoa9h9o78v9ksur` (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色部门关联';

-- ----------------------------
-- Records of sys_roles_depts
-- ----------------------------
INSERT INTO `sys_roles_depts` VALUES ('2', '7');

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`menu_id`,`role_id`) USING BTREE,
  KEY `FKcngg2qadojhi3a651a5adkvbq` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
INSERT INTO `sys_roles_menus` VALUES ('1', '1');
INSERT INTO `sys_roles_menus` VALUES ('2', '1');
INSERT INTO `sys_roles_menus` VALUES ('3', '1');
INSERT INTO `sys_roles_menus` VALUES ('5', '1');
INSERT INTO `sys_roles_menus` VALUES ('7', '1');
INSERT INTO `sys_roles_menus` VALUES ('9', '1');
INSERT INTO `sys_roles_menus` VALUES ('11', '1');
INSERT INTO `sys_roles_menus` VALUES ('30', '1');
INSERT INTO `sys_roles_menus` VALUES ('32', '1');
INSERT INTO `sys_roles_menus` VALUES ('35', '1');
INSERT INTO `sys_roles_menus` VALUES ('36', '1');
INSERT INTO `sys_roles_menus` VALUES ('39', '1');
INSERT INTO `sys_roles_menus` VALUES ('82', '1');
INSERT INTO `sys_roles_menus` VALUES ('83', '1');
INSERT INTO `sys_roles_menus` VALUES ('116', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门名称',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `avatar_name` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `avatar_path` varchar(255) DEFAULT NULL COMMENT '头像真实路径',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `is_admin` bit(1) DEFAULT b'0' COMMENT '是否为admin账号',
  `enabled` bigint(20) DEFAULT NULL COMMENT '状态：1启用、0禁用',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新着',
  `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `UK_kpubos9gc2cvtkb0thktkbkes` (`email`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `uniq_username` (`username`),
  UNIQUE KEY `uniq_email` (`email`),
  KEY `FK5rwmryny6jthaaxkogownknqp` (`dept_id`) USING BTREE,
  KEY `FKpq2dhypk2qgt68nauh2by22jb` (`avatar_name`) USING BTREE,
  KEY `inx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '2', 'admin', '管理员', '男', '18888888888', '201507802@qq.com', null, null, '$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa', '', '1', null, 'admin', '2020-05-03 16:38:31', '2018-08-23 09:11:56', '2020-05-05 10:12:21');
INSERT INTO `sys_user` VALUES ('2', '2', 'test', '测试', '男', '18888888888', '231@qq.com', null, null, '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', '\0', '1', 'admin', 'admin', null, '2020-05-05 11:15:49', '2020-08-21 16:49:06');

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
  KEY `FKq4eq273l04bpu4efj0jd0jb98` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES ('1', '1');
INSERT INTO `sys_users_roles` VALUES ('2', '2');
