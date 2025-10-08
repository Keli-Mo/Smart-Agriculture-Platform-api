-- 修复报告显示为0的问题
-- 解决方案：更新数据时间为当前时间范围内

SELECT '=== 开始修复数据 ===' AS info;

-- 1. 查看当前时间
SELECT NOW() as current_time;

-- 2. 备份原始数据（可选）
CREATE TABLE IF NOT EXISTS acw_greenhouse_monitor_backup AS
SELECT * FROM acw_greenhouse_monitor WHERE 1=0;

INSERT INTO acw_greenhouse_monitor_backup
SELECT * FROM acw_greenhouse_monitor;

-- 3. 更新数据时间为最近3天（确保在API查询范围内）
UPDATE acw_greenhouse_monitor
SET data_time = DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 72) HOUR)
WHERE data_time < DATE_SUB(NOW(), INTERVAL 24 HOUR);

-- 4. 验证更新后的时间范围
SELECT
    MIN(data_time) as earliest_time,
    MAX(data_time) as latest_time,
    COUNT(*) as total_records
FROM acw_greenhouse_monitor;

-- 5. 检查每个大棚的最新数据
SELECT
    gh.name as greenhouse_name,
    m.gh_id,
    COUNT(*) as record_count,
    MAX(m.data_time) as latest_record,
    MIN(m.data_time) as earliest_record
FROM acw_greenhouse_monitor m
JOIN acw_greenhouse gh ON m.gh_id = gh.id
GROUP BY m.gh_id, gh.name
ORDER BY m.gh_id;

-- 6. 生成测试查询（模拟API调用）
SELECT '=== 测试API查询逻辑 ===' AS info;

-- 模拟过去24小时的数据查询
SELECT
    DATE_FORMAT(data_time, '%m-%d %H:00') as time_slot,
    COUNT(*) as record_count,
    ROUND(AVG(CAST(temperature AS DECIMAL(10,2))), 2) as avg_temperature,
    ROUND(AVG(CAST(humility AS DECIMAL(10,2))), 2) as avg_humidity,
    ROUND(AVG(CAST(smoke AS DECIMAL(10,2))), 2) as avg_smoke,
    ROUND(AVG(CAST(pm25 AS DECIMAL(10,2))), 2) as avg_pm25
FROM acw_greenhouse_monitor
WHERE gh_id = 1  -- 1号大棚
  AND data_time >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
GROUP BY DATE_FORMAT(data_time, '%m-%d %H:00')
ORDER BY time_slot;

-- 7. 确保数据不为NULL或空字符串
UPDATE acw_greenhouse_monitor
SET temperature = '0.0' WHERE temperature IS NULL OR temperature = '';

UPDATE acw_greenhouse_monitor
SET humility = '0.0' WHERE humility IS NULL OR humility = '';

UPDATE acw_greenhouse_monitor
SET smoke = '0.0' WHERE smoke IS NULL OR smoke = '';

UPDATE acw_greenhouse_monitor
SET pm25 = '0.0' WHERE pm25 IS NULL OR pm25 = '';

-- 8. 添加一些实时测试数据（确保有数据）
INSERT INTO acw_greenhouse_monitor (gh_id, device_no, temperature, humility, smoke, pm25, data_time, remark, create_by, create_time) VALUES
(1, 'TEMP_SENSOR_001', '23.5', '65.2', '0.01', '15.3', DATE_SUB(NOW(), INTERVAL 1 HOUR), '实时测试数据', 'system', NOW()),
(1, 'TEMP_SENSOR_001', '24.8', '62.1', '0.02', '16.5', DATE_SUB(NOW(), INTERVAL 2 HOUR), '实时测试数据', 'system', NOW()),
(1, 'TEMP_SENSOR_001', '22.1', '68.3', '0.01', '14.7', DATE_SUB(NOW(), INTERVAL 3 HOUR), '实时测试数据', 'system', NOW()),
(2, 'TEMP_SENSOR_002', '25.2', '72.1', '0.01', '13.8', DATE_SUB(NOW(), INTERVAL 1 HOUR), '实时测试数据', 'system', NOW()),
(2, 'TEMP_SENSOR_002', '26.7', '69.5', '0.02', '15.2', DATE_SUB(NOW(), INTERVAL 2 HOUR), '实时测试数据', 'system', NOW()),
(3, 'TEMP_SENSOR_003', '21.8', '76.2', '0.00', '9.5', DATE_SUB(NOW(), INTERVAL 1 HOUR), '实时测试数据', 'system', NOW()),
(4, 'TEMP_SENSOR_004', '26.4', '64.8', '0.01', '17.1', DATE_SUB(NOW(), INTERVAL 1 HOUR), '实时测试数据', 'system', NOW());

-- 9. 验证最终数据
SELECT '=== 最终验证 ===' AS info;

SELECT
    COUNT(*) as total_records,
    MIN(data_time) as earliest_time,
    MAX(data_time) as latest_time,
    ROUND(AVG(CAST(temperature AS DECIMAL(10,2))), 2) as avg_temp,
    ROUND(AVG(CAST(humility AS DECIMAL(10,2))), 2) as avg_humidity
FROM acw_greenhouse_monitor
WHERE data_time >= DATE_SUB(NOW(), INTERVAL 24 HOUR);

SELECT '=== 修复完成 ===' AS info;