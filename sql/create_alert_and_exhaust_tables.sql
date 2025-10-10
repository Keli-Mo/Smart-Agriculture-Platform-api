-- 预警记录表 acw_alert_log
CREATE TABLE IF NOT EXISTS `acw_alert_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gh_id` bigint DEFAULT NULL COMMENT '大棚id',
  `device_no` varchar(50) DEFAULT NULL COMMENT '设备编码',
  `temperature` varchar(10) DEFAULT NULL COMMENT '温度',
  `humility` varchar(10) DEFAULT NULL COMMENT '湿度',
  `smoke` varchar(10) DEFAULT NULL COMMENT '烟雾',
  `pm25` varchar(10) DEFAULT NULL COMMENT 'PM2.5',
  `alert` varchar(500) DEFAULT NULL COMMENT '预警信息',
  `type` varchar(50) DEFAULT NULL COMMENT '预警类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `idx_gh_id` (`gh_id`),
  KEY `idx_device_no` (`device_no`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警记录表';

-- 排风计划表 acw_exhaust_schedule
CREATE TABLE IF NOT EXISTS `acw_exhaust_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `greenhouse_id` bigint DEFAULT NULL COMMENT '大棚ID',
  `schedule` text COMMENT '计划详情',
  `reason` varchar(500) DEFAULT NULL COMMENT '分析',
  `weather` varchar(200) DEFAULT NULL COMMENT '天气',
  `status` varchar(10) DEFAULT NULL COMMENT '是否生效（0=生效，1=失效）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `idx_greenhouse_id` (`greenhouse_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排风计划表';

-- 添加表注释
ALTER TABLE `acw_alert_log` COMMENT = '预警记录表';
ALTER TABLE `acw_exhaust_schedule` COMMENT = '排风计划表';

-- 添加字段注释
ALTER TABLE `acw_alert_log` MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `gh_id` bigint DEFAULT NULL COMMENT '大棚id';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `device_no` varchar(50) DEFAULT NULL COMMENT '设备编码';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `temperature` varchar(10) DEFAULT NULL COMMENT '温度';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `humility` varchar(10) DEFAULT NULL COMMENT '湿度';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `smoke` varchar(10) DEFAULT NULL COMMENT '烟雾';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `pm25` varchar(10) DEFAULT NULL COMMENT 'PM2.5';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `alert` varchar(500) DEFAULT NULL COMMENT '预警信息';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `type` varchar(50) DEFAULT NULL COMMENT '预警类型';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `create_time` datetime DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `acw_alert_log` MODIFY COLUMN `create_by` varchar(64) DEFAULT NULL COMMENT '创建者';

ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `greenhouse_id` bigint DEFAULT NULL COMMENT '大棚ID';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `schedule` text COMMENT '计划详情';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `reason` varchar(500) DEFAULT NULL COMMENT '分析';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `weather` varchar(200) DEFAULT NULL COMMENT '天气';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `status` varchar(10) DEFAULT NULL COMMENT '是否生效（0=生效，1=失效）';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `create_time` datetime DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `acw_exhaust_schedule` MODIFY COLUMN `create_by` varchar(64) DEFAULT NULL COMMENT '创建者';

-- 插入测试数据
INSERT INTO `acw_alert_log` (`gh_id`, `device_no`, `temperature`, `humility`, `smoke`, `pm25`, `alert`, `type`, `create_time`, `create_by`) VALUES
(1, 'DEV001', '35', '80', '0', '75', '温度过高，湿度超标', '温度预警', NOW(), 'admin'),
(1, 'DEV002', '15', '45', '1', '150', '检测到烟雾，PM2.5超标', '空气质量预警', NOW(), 'admin'),
(2, 'DEV003', '28', '65', '0', '45', '环境参数正常', '正常', NOW(), 'admin');

INSERT INTO `acw_exhaust_schedule` (`greenhouse_id`, `schedule`, `reason`, `weather`, `status`, `create_time`, `create_by`) VALUES
(1, '上午9:00-10:00开启排风，下午15:00-16:00开启排风', '根据温度和湿度分析，需要进行通风调节', '晴天，温度28°C，湿度65%', '0', NOW(), 'admin'),
(2, '上午8:30-9:30开启排风，下午14:30-15:30开启排风', '大棚内湿度偏高，需要增加通风时间', '多云，温度25°C，湿度70%', '0', NOW(), 'admin'),
(3, '暂停排风计划', '雨天湿度较高，暂停排风以避免过度干燥', '小雨，温度20°C，湿度85%', '1', NOW(), 'admin');