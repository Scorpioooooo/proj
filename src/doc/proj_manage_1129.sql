/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : project_manage

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2018-11-29 15:31:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `configuration`
-- ----------------------------
DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of configuration
-- ----------------------------

-- ----------------------------
-- Table structure for `coocaa_hr`
-- ----------------------------
DROP TABLE IF EXISTS `coocaa_hr`;
CREATE TABLE `coocaa_hr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(5) DEFAULT NULL COMMENT '人员id',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `group` varchar(30) DEFAULT NULL COMMENT '分组',
  `role` varchar(30) DEFAULT NULL COMMENT '角色',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `duties` varchar(10) DEFAULT NULL COMMENT '职务',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT 'email地址',
  `workplace` varchar(50) DEFAULT NULL COMMENT '工作地点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=378 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `documents`
-- ----------------------------
DROP TABLE IF EXISTS `documents`;
CREATE TABLE `documents` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `document_id` int(30) NOT NULL DEFAULT '0' COMMENT '文档id',
  `document_name` varchar(30) DEFAULT NULL COMMENT '文档名称',
  `document_folder` varchar(100) DEFAULT NULL COMMENT '文档保存路径',
  `document_size` int(10) DEFAULT NULL COMMENT '文件大小',
  `document_md5` varchar(32) DEFAULT NULL COMMENT '文档md5值',
  `belong_project_id` int(5) DEFAULT NULL COMMENT '所属项目',
  `belong_task_id` int(5) DEFAULT NULL COMMENT '归属任务ID，可为空',
  `upload_person_id` int(11) DEFAULT NULL COMMENT '上传人员',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '文档创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '文档修改时间',
  PRIMARY KEY (`id`,`document_id`),
  KEY `document_project_id_rlt` (`belong_project_id`),
  CONSTRAINT `document_project_id_rlt` FOREIGN KEY (`belong_project_id`) REFERENCES `projects` (`proj_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of documents
-- ----------------------------

-- ----------------------------
-- Table structure for `mileposts`
-- ----------------------------
DROP TABLE IF EXISTS `mileposts`;
CREATE TABLE `mileposts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `milepost_id` int(11) DEFAULT NULL COMMENT '里程碑id',
  `milepost_description` varchar(1024) DEFAULT NULL COMMENT '里程碑描述',
  `orderby` int(3) DEFAULT NULL COMMENT '排序',
  `resolved` tinyint(1) DEFAULT NULL COMMENT '是否已经达成',
  `resolve_time` timestamp NULL DEFAULT NULL COMMENT '里程碑解决时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='里程碑';

-- ----------------------------
-- Records of mileposts
-- ----------------------------

-- ----------------------------
-- Table structure for `projects`
-- ----------------------------
DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `id` int(30) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proj_id` int(20) DEFAULT NULL COMMENT '项目id',
  `proj_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `proj_initiator_id` int(20) DEFAULT NULL COMMENT '项目发起人id',
  `proj_include_department_ids` varchar(100) DEFAULT NULL COMMENT '项目参与部门id列表，以“,”分割',
  `proj_manager_id` int(20) DEFAULT NULL COMMENT '项目总负责人',
  `proj_status` enum('缓慢','延期','正常','挂起','取消','已关闭','进行中','未开始') DEFAULT '正常' COMMENT '项目状态',
  `proj_stage` enum('生命周期','发布','开发，测试，需求方评审','测试评审','开发联合调试','阶段测试','开发进行中','开发任务分解','开发','计划评审','计划方案评估','计划风险评估','计划任务分解','计划评估','立项通过','发起审核','发起评估','发起') DEFAULT '开发联合调试' COMMENT '项目阶段',
  `proj_completion_degree` int(3) DEFAULT NULL COMMENT '项目完成度',
  `proj_grade` enum('简单','简单紧急','一般','一般紧急','重要','重要紧急') DEFAULT NULL COMMENT '项目重要等级',
  `proj_risk` int(10) DEFAULT NULL COMMENT '项目风险等级',
  `risk_ids` varchar(150) DEFAULT NULL COMMENT '风险id',
  `milepost_ids` varchar(150) DEFAULT NULL,
  `proj_begin_time` timestamp NULL DEFAULT NULL COMMENT '项目起始时间',
  `proj_end_time` timestamp NULL DEFAULT NULL COMMENT '项目预计结束时间',
  `proj_real_begin_time` timestamp NULL DEFAULT NULL COMMENT '项目实际起始时间',
  `proj_real_end_time` timestamp NULL DEFAULT NULL COMMENT '项目实际结束时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '本条信息创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '本条信息更新时间',
  PRIMARY KEY (`id`),
  KEY `proj_id` (`proj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of projects
-- ----------------------------
INSERT INTO `projects` VALUES ('1', null, null, null, null, null, '正常', '开发联合调试', null, '简单', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `projects_baseinfo`
-- ----------------------------
DROP TABLE IF EXISTS `projects_baseinfo`;
CREATE TABLE `projects_baseinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL COMMENT '项目ID',
  `market` text COMMENT '市场分析',
  `technology` text COMMENT '技术可行性分析',
  `competitor` text COMMENT '竞品分析',
  `policy` text COMMENT '政策风险分析',
  `enclosure_document_ids` text COMMENT '附件文件id，以“,”切割',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of projects_baseinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `projects_evaluation`
-- ----------------------------
DROP TABLE IF EXISTS `projects_evaluation`;
CREATE TABLE `projects_evaluation` (
  `id` int(30) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proj_id` int(20) DEFAULT NULL COMMENT '项目id',
  `level` int(3) DEFAULT NULL COMMENT '项目评级',
  `score_final` int(3) DEFAULT NULL COMMENT '项目打分',
  `score_plan` int(3) DEFAULT NULL COMMENT '计划，策划评分',
  `score_test` int(3) DEFAULT NULL COMMENT '测试评分',
  `score_develope` int(3) DEFAULT NULL COMMENT '开发评分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of projects_evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for `risks`
-- ----------------------------
DROP TABLE IF EXISTS `risks`;
CREATE TABLE `risks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `risk_id` int(11) DEFAULT NULL,
  `orderby` int(3) DEFAULT NULL COMMENT '排序',
  `risk_discription` varchar(1024) DEFAULT NULL COMMENT '风险描述',
  `risk_solution` varchar(1024) DEFAULT NULL COMMENT '风险解决方案',
  `risk_isresolved` tinyint(1) DEFAULT NULL COMMENT '是否已经解决',
  `resolve_time` timestamp NULL DEFAULT NULL COMMENT '风险解决时间',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of risks
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` varchar(30) DEFAULT NULL COMMENT '部门名称',
  `dept_tel` varchar(22) DEFAULT NULL COMMENT '部门电话',
  `dept_fax` varchar(22) DEFAULT NULL COMMENT '部门传真',
  `dept_charge_person_id` int(5) DEFAULT NULL COMMENT '部门负责人',
  `dept_degree` int(3) DEFAULT NULL COMMENT '部门等级',
  `disabled` int(11) DEFAULT '0' COMMENT '0可用1禁用2删除',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `seq` int(11) DEFAULT '0' COMMENT '排序',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '部门父编号',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='部门信息表';

-- ----------------------------
-- Table structure for `sys_log_login`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` varchar(45) DEFAULT NULL COMMENT '用户名',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(20) DEFAULT NULL COMMENT '用户登录ip',
  `user_agent` varchar(300) DEFAULT NULL COMMENT '用户浏览器信息',
  `login_type` int(11) DEFAULT '0' COMMENT '登录类型',
  `remark` varchar(45) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户登录日志';


-- ----------------------------
-- Table structure for `sys_log_op`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_op`;
CREATE TABLE `sys_log_op` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `elapsed_time` int(11) DEFAULT NULL COMMENT '耗用时间',
  `exec_sql` text COMMENT '执行的语句',
  `user_id` int(11) DEFAULT NULL COMMENT '执行人id',
  `user_name` varchar(45) DEFAULT NULL COMMENT '执行人名称',
  `exec_type` varchar(45) DEFAULT NULL COMMENT '动作,delete update insert',
  `exec_table` varchar(45) DEFAULT NULL COMMENT '操作表',
  `op_ip` varchar(32) DEFAULT NULL COMMENT '操作人IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

-- ----------------------------
-- Records of sys_log_op
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `elid` varchar(50) DEFAULT NULL COMMENT '元素ID',
  `icon_class` varchar(30) DEFAULT NULL COMMENT '节点图标样式',
  `tab_id` varchar(20) DEFAULT NULL COMMENT 'TAB页ID',
  `tab_title` varchar(20) DEFAULT NULL COMMENT 'TAB页标题',
  `tab_icon` varchar(30) DEFAULT NULL COMMENT 'TAB页图标样式',
  `iframe_url` varchar(200) DEFAULT NULL COMMENT '框架中打开URL',
  `seq` int(11) DEFAULT '0' COMMENT '排序',
  `modle` int(1) DEFAULT '0' COMMENT '打开模型0展开1TAB中打开2执行',
  `disabled` int(1) DEFAULT '0' COMMENT '0正常1禁用',
  `pmid` int(11) DEFAULT '0' COMMENT '菜单父编号',
  `version` varchar(5) NOT NULL COMMENT '菜单使适用版本（1：应用圈1.0，3：应用圈3.0，n应用圈n）',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='系统导航菜单表';


-- ----------------------------
-- Table structure for `sys_menu_run`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_run`;
CREATE TABLE `sys_menu_run` (
  `menu_id` int(11) NOT NULL COMMENT '菜单编号',
  `run_id` int(11) NOT NULL COMMENT '操作',
  KEY `FK_sys_menu_run` (`menu_id`),
  KEY `FK_sys_menu_run_2` (`run_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单-功能，关联表';

-- ----------------------------
-- Records of sys_menu_run
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `seq` int(11) DEFAULT '0' COMMENT '排序号',
  `role_desc` varchar(30) DEFAULT NULL COMMENT '说明',
  `version` varchar(5) NOT NULL DEFAULT '0' COMMENT '角色适用版本（1：应用圈1.0，3：应用圈3.0，n应用圈n）',
  `create_uid` int(11) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_uid` int(11) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='角色表';


-- ----------------------------
-- Table structure for `sys_role_menu_run`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_run`;
CREATE TABLE `sys_role_menu_run` (
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `menu_id` int(11) NOT NULL COMMENT '菜单编号',
  `run_id` int(11) NOT NULL COMMENT '运行权限编号',
  PRIMARY KEY (`role_id`,`menu_id`,`run_id`),
  KEY `FK_sys_role_menu_run_2` (`menu_id`),
  KEY `FK_sys_role_menu_run_r` (`run_id`),
  CONSTRAINT `sys_role_menu_run_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`),
  CONSTRAINT `sys_role_menu_run_ibfk_2` FOREIGN KEY (`run_id`) REFERENCES `sys_run` (`run_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单-功能，关联表';


-- ----------------------------
-- Table structure for `sys_run`
-- ----------------------------
DROP TABLE IF EXISTS `sys_run`;
CREATE TABLE `sys_run` (
  `run_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `run_comm` varchar(20) DEFAULT NULL COMMENT '命令',
  `run_name` varchar(20) DEFAULT NULL COMMENT '说明',
  `run_btn` bit(1) DEFAULT NULL COMMENT '是否按钮',
  `run_btn_icon` varchar(20) DEFAULT NULL COMMENT '按钮样式',
  `seq` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`run_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统功能表';

-- ----------------------------
-- Records of sys_run
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_run_copy`
-- ----------------------------
DROP TABLE IF EXISTS `sys_run_copy`;
CREATE TABLE `sys_run_copy` (
  `run_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `run_comm` varchar(20) DEFAULT NULL COMMENT '命令',
  `run_name` varchar(20) DEFAULT NULL COMMENT '说明',
  `run_btn` bit(1) DEFAULT NULL COMMENT '是否按钮',
  `run_btn_icon` varchar(20) DEFAULT NULL COMMENT '按钮样式',
  `seq` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`run_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='系统功能表';

-- ----------------------------
-- Records of sys_run_copy
-- ----------------------------
INSERT INTO `sys_run_copy` VALUES ('1', 'browser', '浏览', '', null, '0');
INSERT INTO `sys_run_copy` VALUES ('2', 'add', '新增', '', 'icon-tools-add', '1');
INSERT INTO `sys_run_copy` VALUES ('3', 'edit', '编辑', '', 'icon-tools-edit', '2');
INSERT INTO `sys_run_copy` VALUES ('4', 'delete', '删除', '', 'icon-tools-del', '3');
INSERT INTO `sys_run_copy` VALUES ('5', 'search', '高级查询', '', 'icon-search', '4');
INSERT INTO `sys_run_copy` VALUES ('6', 'refresh', '刷新', '', null, '5');
INSERT INTO `sys_run_copy` VALUES ('7', 'print', '打印', '', null, '6');
INSERT INTO `sys_run_copy` VALUES ('8', 'inport', '导入', '', null, '7');
INSERT INTO `sys_run_copy` VALUES ('9', 'export', '导出', '', null, '8');
INSERT INTO `sys_run_copy` VALUES ('10', 'audit', '审核', '', 'icon-auth', '9');
INSERT INTO `sys_run_copy` VALUES ('11', 'shelves', '上架', '', 'icon-cart-put', '10');
INSERT INTO `sys_run_copy` VALUES ('12', 'soldOut', '下架', '', 'icon-cart-remove', '11');
INSERT INTO `sys_run_copy` VALUES ('13', 'syn', '同步', '', 'icon-cart-syn', '12');
INSERT INTO `sys_run_copy` VALUES ('14', 'addblack', '加入黑名单', '', 'icon-tools-add', '13');
INSERT INTO `sys_run_copy` VALUES ('15', 'save', '保存', '', null, '0');
INSERT INTO `sys_run_copy` VALUES ('16', 'import', '从基础数据导入', '', 'icon-tools-add', '14');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `nick_name` varchar(45) NOT NULL DEFAULT '' COMMENT '昵称',
  `dept_id` int(11) DEFAULT NULL COMMENT '所属部门编号',
  `login_name` varchar(30) NOT NULL COMMENT '账号',
  `login_pwd` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(6) DEFAULT NULL COMMENT '盐值',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件地址',
  `workplace` varchar(45) NOT NULL DEFAULT '' COMMENT '固定电话',
  `mobile` varchar(45) NOT NULL DEFAULT '' COMMENT '移动电话',
  `user_sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别:1男士,2女士',
  `login_ip` varchar(30) DEFAULT NULL COMMENT '最后登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  `super_admin` int(11) DEFAULT '0' COMMENT '超级管理员0否1是',
  `disabled` int(1) DEFAULT '0' COMMENT '0可用1禁用2删除',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `date_create` datetime DEFAULT NULL COMMENT '创建时间',
  `date_modify` datetime DEFAULT NULL COMMENT '最后修改时间',
  `area_tag_id` int(11) DEFAULT '0' COMMENT '地区ID',
  `country_tag_id` int(11) DEFAULT '0' COMMENT '国家ID',
  `language_tag_id` int(11) DEFAULT '0' COMMENT '语言ID',
  `company_id` int(11) DEFAULT '0' COMMENT '公司ID',
  `creater` int(11) DEFAULT '0' COMMENT '创建人',
  `authorized` int(11) DEFAULT '0' COMMENT '授权人',
  PRIMARY KEY (`id`),
  KEY `FK_sys_user` (`dept_id`),
  KEY `i_tel` (`workplace`),
  KEY `i_company` (`company_id`),
  KEY `i_area` (`area_tag_id`),
  KEY `i_country` (`country_tag_id`),
  KEY `i_language` (`language_tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=439 DEFAULT CHARSET=utf8 COMMENT='操作员表';


-- ----------------------------
-- Table structure for `sys_user_menu_run`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu_run`;
CREATE TABLE `sys_user_menu_run` (
  `umr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user_menu_run自增ID',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单编号',
  `run_id` int(11) NOT NULL DEFAULT '0' COMMENT '运行权限编号',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态:1为有效，0为失效',
  `creater` int(11) DEFAULT '0' COMMENT '创建人',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `modifier` int(11) DEFAULT '0' COMMENT '修改人',
  `last_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`umr_id`),
  KEY `i_userid` (`user_id`),
  KEY `i_menuid` (`menu_id`),
  KEY `i_runid` (`run_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与后台管理页面权限关系表';

-- ----------------------------
-- Records of sys_user_menu_run
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_sys_user_role_r` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员角色关联表';



-- ----------------------------
-- Table structure for `task_baseinfo`
-- ----------------------------
DROP TABLE IF EXISTS `task_baseinfo`;
CREATE TABLE `task_baseinfo` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `task_id` int(20) DEFAULT NULL COMMENT '任务id',
  `task_name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_baseinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `tasks`
-- ----------------------------
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `task_id` int(20) DEFAULT NULL COMMENT '任务id',
  `belong_project_id` int(10) DEFAULT NULL COMMENT '归属项目id 1:1',
  `task_asign_person_id` int(10) DEFAULT NULL COMMENT '任务负责人',
  `task_asign_person_history_ids` varchar(1024) DEFAULT NULL COMMENT '指派任务历史负责人',
  `task_level` int(2) DEFAULT NULL COMMENT '任务级别，任务分两级。',
  `task_parent_id` int(20) DEFAULT NULL COMMENT '上级任务id',
  `task_name` varchar(30) DEFAULT NULL COMMENT '任务名称',
  `task_discription` varchar(1024) DEFAULT NULL COMMENT '任务描述',
  `task_grade` int(3) DEFAULT '3' COMMENT '任务重要等级',
  `task_begin_time` timestamp NULL DEFAULT NULL COMMENT '任务计划起始时间',
  `task_end_time` timestamp NULL DEFAULT NULL COMMENT '任务预计结束时间',
  `task_real_begin_time` timestamp NULL DEFAULT NULL COMMENT '任务实际开始时间',
  `task_real_end_time` timestamp NULL DEFAULT NULL COMMENT '任务实际结束时间',
  `createtime` timestamp NULL DEFAULT NULL COMMENT '本信息创建时间',
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '本条信息更新时间',
  `task_isclosed` int(11) DEFAULT '0' COMMENT '任务是否结束 1: 结束 0：未结束',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tasks
-- ----------------------------
