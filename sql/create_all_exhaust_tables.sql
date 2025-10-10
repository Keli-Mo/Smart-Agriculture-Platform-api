-- 完整的排风系统数据库表结构
-- 包含：排风计划表、排风记录表

-- 1. 排风计划表 acw_exhaust_schedule
CREATE TABLE IF NOT EXISTS `acw_exhaust_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `greenhouse_id` bigint DEFAULT NULL COMMENT '大棚ID',
  `schedule` text COMMENT '计划详情',
  `reason` varchar(500) DEFAULT NULL COMMENT '分析',
  `weather` varchar(200) DEFAULT NULL COMMENT '天气',
  `status` varchar(10) DEFAULT NULL COMMENT '是否生效（0=生效，1=失效）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_greenhouse_id` (`greenhouse_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排风计划表';

-- 2. 排风记录表 acw_exhaust_record
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

-- 添加表注释
ALTER TABLE `acw_exhaust_schedule` COMMENT = '排风计划表';
ALTER TABLE `acw_exhaust_record` COMMENT = '排风记录表';

-- 添加排风计划表字段注释
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `greenhouse_id` bigint DEFAULT NULL COMMENT '大棚ID';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `schedule` text COMMENT '计划详情';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `reason` varchar(500) DEFAULT NULL COMMENT '分析';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `weather` varchar(200) DEFAULT NULL COMMENT '天气';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `status` varchar(10) DEFAULT NULL COMMENT '是否生效（0=生效，1=失效）';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `create_time` datetime DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `create_by` varchar(64) DEFAULT NULL COMMENT '创建者';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `update_time` datetime DEFAULT NULL COMMENT '更新时间';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `update_by` varchar(64) DEFAULT NULL COMMENT '更新者';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `remark` varchar(500) DEFAULT NULL COMMENT '备注';

-- 添加排风记录表字段注释
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `greenhouse_id` bigint DEFAULT NULL COMMENT '大棚ID';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `schedule_id` bigint DEFAULT NULL COMMENT '计划ID';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `action` varchar(100) DEFAULT NULL COMMENT '执行动作';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `duration` varchar(50) DEFAULT NULL COMMENT '执行时长';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `create_time` datetime DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `acw_exhaust_record` MODIFY COLUMN `create_by` varchar(64) DEFAULT NULL COMMENT '创建者';

-- 插入排风计划测试数据
INSERT INTO `acw_exhaust_schedule` (`greenhouse_id`, `schedule`, `reason`, `weather`, `status`, `create_time`, `create_by`) VALUES
(1, '上午9:00-10:00开启排风，下午15:00-16:00开启排风', '根据温度和湿度分析，需要进行通风调节', '晴天，温度28°C，湿度65%', '0', NOW(), 'admin'),
(2, '上午8:30-9:30开启排风，下午14:30-15:30开启排风', '大棚内湿度偏高，需要增加通风时间', '多云，温度25°C，湿度70%', '0', NOW(), 'admin'),
(3, '暂停排风计划', '雨天湿度较高，暂停排风以避免过度干燥', '小雨，温度20°C，湿度85%', '1', NOW(), 'admin');

-- 插入排风记录测试数据
INSERT INTO `acw_exhaust_record` (`greenhouse_id`, `schedule_id`, `action`, `duration`, `create_time`, `create_by`) VALUES
(1, 1, '启动排风系统', '30分钟', NOW(), 'admin'),
(1, 1, '关闭排风系统', '30分钟', DATE_ADD(NOW(), INTERVAL 30 MINUTE), 'admin'),
(2, 2, '启动排风系统', '45分钟', NOW(), 'admin'),
(2, 2, '关闭排风系统', '45分钟', DATE_ADD(NOW(), INTERVAL 45 MINUTE), 'admin'),
(3, 3, '排风计划暂停', '0分钟', NOW(), 'system');

-- 创建外键约束（可选，根据实际需求添加）
-- ALTER TABLE `acw_exhaust_record` ADD CONSTRAINT `fk_exhaust_record_schedule`
-- FOREIGN KEY (`schedule_id`) REFERENCES `acw_exhaust_schedule` (`id`) ON DELETE CASCADE;

-- ALTER TABLE `acw_exhaust_record` ADD CONSTRAINT `fk_exhaust_record_greenhouse`
-- FOREIGN KEY (`greenhouse_id`) REFERENCES `acw_greenhouse` (`id`) ON DELETE CASCADE;

-- ALTER TABLE `acw_exhaust_schedule` ADD CONSTRAINT `fk_exhaust_schedule_greenhouse`
-- FOREIGN KEY (`greenhouse_id`) REFERENCES `acw_greenhouse` (`id`) ON DELETE CASCADE;