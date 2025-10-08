/*
大棚基础数据和环境监测数据SQL文件
智能大棚环境监测系统
*/

-- 创建大棚信息表（如果不存在）
DROP TABLE IF EXISTS `acw_greenhouse`;
CREATE TABLE `acw_greenhouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `location` varchar(255) DEFAULT NULL COMMENT '详细位置',
  `length` varchar(50) DEFAULT NULL COMMENT '长',
  `width` varchar(50) DEFAULT NULL COMMENT '宽',
  `height` varchar(50) DEFAULT NULL COMMENT '高',
  `area` varchar(50) DEFAULT NULL COMMENT '种植面积',
  `crop` varchar(100) DEFAULT NULL COMMENT '作物',
  `lng` varchar(50) DEFAULT NULL COMMENT '经度',
  `lat` varchar(50) DEFAULT NULL COMMENT '纬度',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='大棚信息';

-- 创建大棚监测数据表（如果不存在）
DROP TABLE IF EXISTS `acw_greenhouse_monitor`;
CREATE TABLE `acw_greenhouse_monitor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gh_id` bigint(20) DEFAULT NULL COMMENT '大棚ID',
  `device_no` varchar(100) DEFAULT NULL COMMENT '设备编号',
  `temperature` varchar(20) DEFAULT NULL COMMENT '温度(°C)',
  `humility` varchar(20) DEFAULT NULL COMMENT '湿度(%)',
  `smoke` varchar(20) DEFAULT NULL COMMENT '烟雾浓度',
  `pm25` varchar(20) DEFAULT NULL COMMENT 'PM2.5',
  `data_time` datetime DEFAULT NULL COMMENT '监测时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_gh_id` (`gh_id`),
  KEY `idx_data_time` (`data_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='大棚监测数据';

-- 插入大棚基础数据
INSERT INTO `acw_greenhouse` (`id`, `name`, `location`, `length`, `width`, `height`, `area`, `crop`, `lng`, `lat`, `picture`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
(1, '一号智能温室', '农业科技园区A区-1号地块', '50', '20', '4.5', '1000', '番茄', '116.3974', '39.9093', '/profile/upload/greenhouse/gh1.jpg', '现代化智能温室，配备自动温控系统', 'admin', '2025-01-15 09:00:00', 'admin', '2025-01-15 09:00:00'),
(2, '二号生态大棚', '农业科技园区B区-2号地块', '80', '25', '5.0', '2000', '黄瓜', '116.3980', '39.9100', '/profile/upload/greenhouse/gh2.jpg', '生态种植大棚，采用有机种植模式', 'admin', '2025-01-20 10:30:00', 'admin', '2025-01-20 10:30:00'),
(3, '三号育苗温室', '农业科技园区C区-3号地块', '30', '15', '3.8', '450', '育苗区', '116.3985', '39.9105', '/profile/upload/greenhouse/gh3.jpg', '专业育苗温室，配备补光系统', 'admin', '2025-02-01 14:20:00', 'admin', '2025-02-01 14:20:00'),
(4, '四号实验大棚', '农业科技园区D区-4号地块', '40', '20', '4.2', '800', '试验作物', '116.3990', '39.9110', '/profile/upload/greenhouse/gh4.jpg', '科研实验大棚，用于新品种试验', 'admin', '2025-02-10 16:45:00', 'admin', '2025-02-10 16:45:00');

-- 插入大棚监测数据 - 模拟一周的环境数据
-- 一号大棚数据（番茄）
INSERT INTO `acw_greenhouse_monitor` (`gh_id`, `device_no`, `temperature`, `humility`, `smoke`, `pm25`, `data_time`, `remark`, `create_by`, `create_time`) VALUES
(1, 'TEMP_SENSOR_001', '22.5', '65.2', '0.01', '15.3', '2025-10-08 08:00:00', '早晨温度较低，湿度适中', 'system', '2025-10-08 08:00:00'),
(1, 'TEMP_SENSOR_001', '28.3', '58.7', '0.02', '18.1', '2025-10-08 14:00:00', '午后温度升高，湿度下降', 'system', '2025-10-08 14:00:00'),
(1, 'TEMP_SENSOR_001', '24.8', '62.1', '0.01', '16.5', '2025-10-08 20:00:00', '傍晚温度回落', 'system', '2025-10-08 20:00:00'),

(1, 'TEMP_SENSOR_001', '21.8', '68.3', '0.01', '14.7', '2025-03-16 08:00:00', '阴天，湿度稍高', 'system', '2025-03-16 08:00:00'),
(1, 'TEMP_SENSOR_001', '26.7', '61.2', '0.02', '17.3', '2025-03-16 14:00:00', '云层变薄，温度上升', 'system', '2025-03-16 14:00:00'),
(1, 'TEMP_SENSOR_001', '23.5', '64.8', '0.01', '15.9', '2025-03-16 20:00:00', '夜晚温度稳定', 'system', '2025-03-16 20:00:00'),

(1, 'TEMP_SENSOR_001', '23.2', '63.1', '0.01', '15.2', '2025-03-17 08:00:00', '晴天，条件良好', 'system', '2025-03-17 08:00:00'),
(1, 'TEMP_SENSOR_001', '29.1', '55.4', '0.02', '19.2', '2025-03-17 14:00:00', '阳光充足，温度较高', 'system', '2025-03-17 14:00:00'),
(1, 'TEMP_SENSOR_001', '25.3', '59.8', '0.01', '17.4', '2025-03-17 20:00:00', '通风系统运行良好', 'system', '2025-03-17 20:00:00');

-- 二号大棚数据（黄瓜）
INSERT INTO `acw_greenhouse_monitor` (`gh_id`, `device_no`, `temperature`, `humility`, `smoke`, `pm25`, `data_time`, `remark`, `create_by`, `create_time`) VALUES
(2, 'TEMP_SENSOR_002', '24.1', '72.3', '0.01', '12.8', '2025-10-08 08:00:00', '黄瓜需要较高湿度', 'system', '2025-10-08 08:00:00'),
(2, 'TEMP_SENSOR_002', '27.8', '68.5', '0.02', '16.2', '2025-10-08 14:00:00', '湿度保持在适宜范围', 'system', '2025-10-08 14:00:00'),
(2, 'TEMP_SENSOR_002', '25.2', '70.1', '0.01', '14.3', '2025-10-08 20:00:00', '夜间湿度自然上升', 'system', '2025-10-08 20:00:00'),

(2, 'TEMP_SENSOR_002', '23.5', '74.2', '0.01', '11.9', '2025-03-16 08:00:00', '晨露形成，湿度较高', 'system', '2025-03-16 08:00:00'),
(2, 'TEMP_SENSOR_002', '28.3', '69.7', '0.02', '15.8', '2025-03-16 14:00:00', '开启通风调节湿度', 'system', '2025-03-16 14:00:00'),
(2, 'TEMP_SENSOR_002', '24.8', '71.6', '0.01', '13.5', '2025-03-16 20:00:00', '湿度控制良好', 'system', '2025-03-16 20:00:00'),

(2, 'TEMP_SENSOR_002', '24.7', '71.8', '0.01', '13.2', '2025-03-17 08:00:00', '黄瓜生长环境稳定', 'system', '2025-03-17 08:00:00'),
(2, 'TEMP_SENSOR_002', '29.2', '66.4', '0.02', '17.1', '2025-03-17 14:00:00', '温度升高，湿度下降', 'system', '2025-03-17 14:00:00'),
(2, 'TEMP_SENSOR_002', '26.1', '69.3', '0.01', '15.7', '2025-03-17 20:00:00', '开启雾化增加湿度', 'system', '2025-03-17 20:00:00');

-- 三号大棚数据（育苗区）
INSERT INTO `acw_greenhouse_monitor` (`gh_id`, `device_no`, `temperature`, `humility`, `smoke`, `pm25`, `data_time`, `remark`, `create_by`, `create_time`) VALUES
(3, 'TEMP_SENSOR_003', '20.8', '78.2', '0.00', '8.5', '2025-10-08 08:00:00', '育苗需要高湿环境', 'system', '2025-10-08 08:00:00'),
(3, 'TEMP_SENSOR_003', '25.3', '74.6', '0.01', '11.2', '2025-10-08 14:00:00', '温度控制在适宜范围', 'system', '2025-10-08 14:00:00'),
(3, 'TEMP_SENSOR_003', '22.1', '76.8', '0.00', '9.7', '2025-10-08 20:00:00', '育苗环境稳定', 'system', '2025-10-08 20:00:00'),

(3, 'TEMP_SENSOR_003', '21.2', '79.1', '0.00', '8.1', '2025-03-16 08:00:00', '高湿环境有利于种子萌发', 'system', '2025-03-16 08:00:00'),
(3, 'TEMP_SENSOR_003', '24.8', '75.3', '0.01', '10.8', '2025-03-16 14:00:00', '避免温度过高', 'system', '2025-03-16 14:00:00'),
(3, 'TEMP_SENSOR_003', '21.7', '77.5', '0.00', '9.3', '2025-03-16 20:00:00', '保持稳定的育苗环境', 'system', '2025-03-16 20:00:00'),

(3, 'TEMP_SENSOR_003', '21.5', '77.8', '0.00', '9.0', '2025-03-17 08:00:00', '育苗条件良好', 'system', '2025-03-17 08:00:00'),
(3, 'TEMP_SENSOR_003', '26.1', '73.2', '0.01', '12.4', '2025-03-17 14:00:00', '适当通风防止病害', 'system', '2025-03-17 14:00:00'),
(3, 'TEMP_SENSOR_003', '22.8', '75.9', '0.00', '10.1', '2025-03-17 20:00:00', '育苗环境控制良好', 'system', '2025-03-17 20:00:00');

-- 四号大棚数据（实验作物）
INSERT INTO `acw_greenhouse_monitor` (`gh_id`, `device_no`, `temperature`, `humility`, `smoke`, `pm25`, `data_time`, `remark`, `create_by`, `create_time`) VALUES
(4, 'TEMP_SENSOR_004', '23.7', '67.4', '0.01', '13.6', '2025-10-08 08:00:00', '实验作物对照组', 'system', '2025-10-08 08:00:00'),
(4, 'TEMP_SENSOR_004', '28.9', '62.1', '0.02', '18.7', '2025-10-08 14:00:00', '标准实验条件', 'system', '2025-10-08 14:00:00'),
(4, 'TEMP_SENSOR_004', '25.4', '64.8', '0.01', '16.2', '2025-10-08 20:00:00', '实验数据记录', 'system', '2025-10-08 20:00:00'),

(4, 'TEMP_SENSOR_004', '24.2', '68.1', '0.01', '14.3', '2025-03-16 08:00:00', '对照实验进行中', 'system', '2025-03-16 08:00:00'),
(4, 'TEMP_SENSOR_004', '29.5', '61.7', '0.02', '19.4', '2025-03-16 14:00:00', '收集实验数据', 'system', '2025-03-16 14:00:00'),
(4, 'TEMP_SENSOR_004', '26.1', '63.9', '0.01', '17.1', '2025-03-16 20:00:00', '实验条件稳定', 'system', '2025-03-16 20:00:00'),

(4, 'TEMP_SENSOR_004', '25.8', '64.2', '0.01', '16.8', '2025-03-17 08:00:00', '实验数据对比', 'system', '2025-03-17 08:00:00'),
(4, 'TEMP_SENSOR_004', '30.2', '59.3', '0.02', '20.1', '2025-03-17 14:00:00', '高温对照组', 'system', '2025-03-17 14:00:00'),
(4, 'TEMP_SENSOR_004', '27.3', '61.5', '0.01', '18.4', '2025-03-17 20:00:00', '实验结束数据', 'system', '2025-03-17 20:00:00');

-- 添加更多环境数据（光照、土壤等扩展字段）
-- 如果需要，可以添加以下扩展表格

/*
-- 光照监测数据表（扩展）
DROP TABLE IF EXISTS `acw_light_monitor`;
CREATE TABLE `acw_light_monitor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gh_id` bigint(20) DEFAULT NULL COMMENT '大棚ID',
  `device_no` varchar(100) DEFAULT NULL COMMENT '设备编号',
  `light_intensity` varchar(20) DEFAULT NULL COMMENT '光照强度(lux)',
  `light_duration` varchar(20) DEFAULT NULL COMMENT '光照时长(小时)',
  `data_time` datetime DEFAULT NULL COMMENT '监测时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_gh_id` (`gh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='光照监测数据';

-- 土壤监测数据表（扩展）
DROP TABLE IF EXISTS `acw_soil_monitor`;
CREATE TABLE `acw_soil_monitor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gh_id` bigint(20) DEFAULT NULL COMMENT '大棚ID',
  `device_no` varchar(100) DEFAULT NULL COMMENT '设备编号',
  `soil_temperature` varchar(20) DEFAULT NULL COMMENT '土壤温度(°C)',
  `soil_moisture` varchar(20) DEFAULT NULL COMMENT '土壤湿度(%)',
  `soil_ph` varchar(20) DEFAULT NULL COMMENT '土壤pH值',
  `soil_ec` varchar(20) DEFAULT NULL COMMENT '土壤电导率(ms/cm)',
  `soil_nitrogen` varchar(20) DEFAULT NULL COMMENT '土壤氮含量(mg/kg)',
  `soil_phosphorus` varchar(20) DEFAULT NULL COMMENT '土壤磷含量(mg/kg)',
  `soil_potassium` varchar(20) DEFAULT NULL COMMENT '土壤钾含量(mg/kg)',
  `data_time` datetime DEFAULT NULL COMMENT '监测时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_gh_id` (`gh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='土壤监测数据';
*/

-- 查询验证数据
-- SELECT * FROM acw_greenhouse;
-- SELECT * FROM acw_greenhouse_monitor ORDER BY data_time DESC;
-- SELECT gh.name, COUNT(m.id) as monitor_count, AVG(m.temperature) as avg_temp, AVG(m.humility) as avg_humidity
-- FROM acw_greenhouse gh
-- LEFT JOIN acw_greenhouse_monitor m ON gh.id = m.gh_id
-- GROUP BY gh.id, gh.name;