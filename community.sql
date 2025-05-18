CREATE DATABASE community;

USE community;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `activity_information`
-- ----------------------------
DROP TABLE IF EXISTS `activity_information`;
CREATE TABLE `activity_information` (
  `activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(255) DEFAULT NULL,
  `activity_type` varchar(255) DEFAULT NULL,
  `activity_date` date DEFAULT NULL,
  `activity_location` varchar(255) DEFAULT NULL,
  `expected_activity_number` varchar(11) DEFAULT NULL,
  `activity_description` varchar(255) DEFAULT NULL,
  `assocoated_community_number` int(11) DEFAULT NULL,
  `activity_summary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `assocoated_community_number` (`assocoated_community_number`),
  CONSTRAINT `assocoated_community_number` FOREIGN KEY (`assocoated_community_number`) REFERENCES `community_information` (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity_information
-- ----------------------------
INSERT INTO `activity_information` VALUES ('1', '英语大赛', '比赛类', '2025-03-19', '讲堂', '222', '我是一个活动1', '1', '34');
INSERT INTO `activity_information` VALUES ('2', '歌手大赛', '比赛类', '2025-03-21', '操场', '23', '我是一个活动2', '2', '23');
INSERT INTO `activity_information` VALUES ('3', '野炊', '生活类', '2025-03-18', '公园', '90', '我是一个活动3', '3', 'erer');
INSERT INTO `activity_information` VALUES ('4', '阅读大赛', '比赛类', '2025-03-08', '教室', '555', '我是一个活动4', '4', null);

-- ----------------------------
-- Table structure for `community_information`
-- ----------------------------
DROP TABLE IF EXISTS `community_information`;
CREATE TABLE `community_information` (
  `community_id` int(255) NOT NULL AUTO_INCREMENT,
  `community_name` varchar(255) DEFAULT NULL,
  `community_type` varchar(255) DEFAULT NULL,
  `founder_name` varchar(255) DEFAULT NULL,
  `founder_id` varchar(10) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `community_introduction` varchar(255) DEFAULT NULL,
  `community_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of community_information
-- ----------------------------
INSERT INTO `community_information` VALUES ('1', '舞蹈', '体育类', '梨花', '1234567890', '2025-03-07', '我是一个社团1', '活跃');
INSERT INTO `community_information` VALUES ('2', '演讲', '文艺类', '五十卡', '1111111111', '2025-03-06', '我是一个社团2', '平静');
INSERT INTO `community_information` VALUES ('3', '比赛', '比赛类', '李红', '2222222222', '2025-03-05', '我是一个社团3', '活跃');
INSERT INTO `community_information` VALUES ('4', '绘画', '绘画类', '花开富贵', '3333333333', '2025-03-04', '我是一个社团4', '倒闭');

-- ----------------------------
-- Table structure for `fund_information`
-- ----------------------------
DROP TABLE IF EXISTS `fund_information`;
CREATE TABLE `fund_information` (
  `fund_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '经费id',
  `fund_amount` int(11) DEFAULT NULL,
  `fund_type` varchar(255) DEFAULT NULL,
  `fund_record_date` date DEFAULT NULL,
  `fund_description` varchar(255) DEFAULT NULL,
  `associated_activity_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`fund_id`),
  KEY `associated_activity_number` (`associated_activity_number`),
  CONSTRAINT `associated_activity_number` FOREIGN KEY (`associated_activity_number`) REFERENCES `activity_information` (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_information
-- ----------------------------
INSERT INTO `fund_information` VALUES ('1', '873', '收入', '2025-03-14', '我是经费1', '1');
INSERT INTO `fund_information` VALUES ('2', '-429', '支出', '2025-03-16', '我是经费2', '2');
INSERT INTO `fund_information` VALUES ('3', '-545', '支出', '2025-03-11', '我是经费3', '3');
INSERT INTO `fund_information` VALUES ('4', '635', '收入', '2025-03-21', '我是经费4', '4');

-- ----------------------------
-- Table structure for `user_information`
-- ----------------------------
DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `user_student_id` varchar(10) NOT NULL,
  `user_join_date` date DEFAULT NULL,
  `user_role` varchar(255) NOT NULL,
  `user_phone` varchar(11) DEFAULT NULL,
  `user_belong_community_number` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_student_id` (`user_student_id`) USING BTREE,
  KEY `user_belong_community_number` (`user_belong_community_number`),
  CONSTRAINT `user_belong_community_number` FOREIGN KEY (`user_belong_community_number`) REFERENCES `community_information` (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_information
-- ----------------------------
INSERT INTO `user_information` VALUES ('1', 'zz', '3', '2025-03-28', '1', '36719035473', '3');
INSERT INTO `user_information` VALUES ('2', 'ww', '4', '2025-03-13', '1', '67823514569', '4');
INSERT INTO `user_information` VALUES ('3', 'qq', '1', null, '2', null, '3');
INSERT INTO `user_information` VALUES ('4', 'aa', '2', null, '2', null, '4');