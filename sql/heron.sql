/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100411
 Source Host           : localhost:3306
 Source Schema         : heron

 Target Server Type    : MySQL
 Target Server Version : 100411
 File Encoding         : 65001

 Date: 27/08/2020 01:23:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) UNSIGNED NOT NULL COMMENT '父菜单Id',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单对应的地址',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '1:表示删除；0:表示未删除',
  `icon` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int(100) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型；M:表示菜单；B:表示按钮',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '系统管理', '#', '', '2020-08-16 17:38:10', NULL, 0, 'fa-address-book-o', 1, 'M');
INSERT INTO `menu` VALUES (2, 1, '菜单管理', '/menu/list', 'sys:menu:*', '2020-08-16 17:44:51', NULL, 0, NULL, 1, 'M');
INSERT INTO `menu` VALUES (3, 2, '添加菜单', NULL, 'sys:menu:add', '2020-08-16 17:45:39', NULL, 0, NULL, 1, 'B');
INSERT INTO `menu` VALUES (4, 2, '删除菜单', NULL, 'sys:menu:del', '2020-08-16 17:47:01', NULL, 0, NULL, 1, 'B');
INSERT INTO `menu` VALUES (5, 2, '修改菜单', NULL, 'sys:menu:update', '2020-08-16 17:47:01', NULL, 0, NULL, 1, 'B');
INSERT INTO `menu` VALUES (6, 2, '查看菜单', NULL, 'sys:menu:list', '2020-08-16 17:47:01', NULL, 0, NULL, 1, 'B');
INSERT INTO `menu` VALUES (7, 1, '用户管理', '#', '', '2020-08-16 19:35:41', NULL, 0, '', 2, 'M');
INSERT INTO `menu` VALUES (8, 1, '日志管理', '#', '', '2020-08-16 19:35:41', NULL, 0, '', 3, 'M');
INSERT INTO `menu` VALUES (9, 1, '性能管理', '#', '', '2020-08-16 19:35:41', NULL, 0, '', 3, 'M');
INSERT INTO `menu` VALUES (10, 1, '交互管理', '#', '', '2020-08-16 19:35:41', NULL, 0, '', 4, 'M');
INSERT INTO `menu` VALUES (11, 1, '11', '11', '11', '2020-08-16 20:53:17', NULL, 0, '11', 11, 'M');
INSERT INTO `menu` VALUES (12, 1, '111', '11', '11', '2020-08-16 20:53:46', NULL, 0, '11', 11, 'M');
INSERT INTO `menu` VALUES (13, 1, '111', '11', '11', '2020-08-16 20:54:10', NULL, 0, '11', 11, 'M');
INSERT INTO `menu` VALUES (14, 0, 'test', '#', '', '2020-08-26 00:43:01', NULL, 0, NULL, 2, 'M');

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) UNSIGNED NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `is_delete` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '1:表示删除；0:表示未删除',
  `enable` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否启用；1：表示冻结；0：表示启用',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `e_mail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '123', '5e5d459c273c51a1e3c50bf8f438e58f6783be6a', '7dd90bfd-236b-1289-962f-82a37b297ada', 0, 0, NULL, '2020-08-16 14:48:45', NULL, '1234@qq.com');
INSERT INTO `user_info` VALUES (2, '456', '11911ec05cbf20af4329349fe4430e14cfe00fa7', '1c676d35-6f32-cd39-2cc1-c9cf8af2af48', 0, 0, NULL, '2020-08-16 14:56:49', NULL, '1234@qq.com');
INSERT INTO `user_info` VALUES (3, '567', '06990ebb503bf3ae046df4fe61c1cc9c8cca42ea', 'ea6c086e-cdc9-e957-fc02-c59a808b8e57', 0, 0, NULL, '2020-08-16 15:24:40', NULL, '1234@qq.com');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
