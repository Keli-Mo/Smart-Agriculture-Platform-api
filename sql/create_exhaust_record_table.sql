-- 排风记录表 acw_exhaust_record
CREATE TABLE IF NOT EXISTS `acw_exhaust_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `greenhouse_id` bigint DEFAULT NULL COMMENT '大棚ID',
  `schedule_id` bigint DEFAULT NULL COMMENT '计划ID',
  `action` varchar(100) DEFAULT NULL COMMENT '执行动作',
  `duration` varchar(50) DEFAULT NULL COMMENT '执行时长',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `idx_greenhouse_id` (`greenhouse_id`),
  KEY `idx_schedule_id` (`schedule_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排风记录表';

-- 添加字段注释
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `greenhouse_id` bigint DEFAULT NULL COMMENT '大棚ID';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `schedule_id` bigint DEFAULT NULL COMMENT '计划ID';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `action` varchar(100) DEFAULT NULL COMMENT '执行动作';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `duration` varchar(50) DEFAULT NULL COMMENT '执行时长';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `create_time` datetime DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `create_by` varchar(64) DEFAULT NULL COMMENT '创建者';

-- 插入测试数据
INSERT INTO `acw_exhaust_record` (`greenhouse_id`, `schedule_id`, `action`, `duration`, `create_time`, `create_by`) VALUES
(1, 1, '启动排风系统', '30分钟', NOW(), 'admin'),
(1, 1, '关闭排风系统', '30分钟', DATE_ADD(NOW(), INTERVAL 30 MINUTE), 'admin'),
(2, 2, '启动排风系统', '45分钟', NOW(), 'admin'),
(2, 2, '关闭排风系统', '45分钟', DATE_ADD(NOW(), INTERVAL 45 MINUTE), 'admin'),
(3, 3, '排风计划暂停', '0分钟', NOW(), 'system');