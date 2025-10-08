-- 调试报告显示为0的问题
-- 问题分析脚本

SELECT '=== 问题诊断开始 ===' AS info;

-- 1. 检查数据库表是否存在
SELECT '1. 检查表结构' AS step;
SHOW TABLES LIKE 'acw_greenhouse%';

-- 2. 检查表结构详情
SELECT '2. 表结构详情' AS step;
DESCRIBE acw_greenhouse_monitor;

-- 3. 检查数据总量
SELECT '3. 数据总量检查' AS step;
SELECT
    'acw_greenhouse' as table_name,
    COUNT(*) as record_count
FROM acw_greenhouse
UNION ALL
SELECT
    'acw_greenhouse_monitor' as table_name,
    COUNT(*) as record_count
FROM acw_greenhouse_monitor;

-- 4. 检查具体数据内容
SELECT '4. 监测数据样本' AS step;
SELECT
    id,
    gh_id,
    device_no,
    temperature,
    humility,
    smoke,
    pm25,
    data_time
FROM acw_greenhouse_monitor
ORDER BY data_time DESC
LIMIT 5;

-- 5. 检查数据类型和范围
SELECT '5. 数据统计分析' AS step;
SELECT
    'temperature' as metric,
    MIN(CAST(temperature AS DECIMAL(10,2))) as min_val,
    MAX(CAST(temperature AS DECIMAL(10,2))) as max_val,
    AVG(CAST(temperature AS DECIMAL(10,2))) as avg_val,
    COUNT(*) as count
FROM acw_greenhouse_monitor
WHERE temperature IS NOT NULL AND temperature != ''
UNION ALL
SELECT
    'humility' as metric,
    MIN(CAST(humility AS DECIMAL(10,2))) as min_val,
    MAX(CAST(humility AS DECIMAL(10,2))) as max_val,
    AVG(CAST(humility AS DECIMAL(10,2))) as avg_val,
    COUNT(*) as count
FROM acw_greenhouse_monitor
WHERE humility IS NOT NULL AND humility != ''
UNION ALL
SELECT
    'smoke' as metric,
    MIN(CAST(smoke AS DECIMAL(10,2))) as min_val,
    MAX(CAST(smoke AS DECIMAL(10,2))) as max_val,
    AVG(CAST(smoke AS DECIMAL(10,2))) as avg_val,
    COUNT(*) as count
FROM acw_greenhouse_monitor
WHERE smoke IS NOT NULL AND smoke != ''
UNION ALL
SELECT
    'pm25' as metric,
    MIN(CAST(pm25 AS DECIMAL(10,2))) as min_val,
    MAX(CAST(pm25 AS DECIMAL(10,2))) as max_val,
    AVG(CAST(pm25 AS DECIMAL(10,2))) as avg_val,
    COUNT(*) as count
FROM acw_greenhouse_monitor
WHERE pm25 IS NOT NULL AND pm25 != '';

-- 6. 检查时间范围数据（用于API查询）
SELECT '6. 时间范围数据检查' AS step;
SELECT
    MIN(data_time) as earliest_time,
    MAX(data_time) as latest_time,
    COUNT(*) as total_records
FROM acw_greenhouse_monitor;

-- 7. 检查指定大棚的数据（模拟API查询）
SELECT '7. 指定大棚数据检查' AS step;
SELECT
    gh_id,
    COUNT(*) as record_count,
    MIN(data_time) as start_time,
    MAX(data_time) as end_time
FROM acw_greenhouse_monitor
GROUP BY gh_id
ORDER BY gh_id;

-- 8. 检查最近24小时的数据（模拟API逻辑）
SELECT '8. 最近24小时数据' AS step;
SELECT
    DATE_FORMAT(data_time, '%m-%d %H:00') as hour_slot,
    COUNT(*) as record_count,
    ROUND(AVG(CAST(temperature AS DECIMAL(10,2))), 2) as avg_temperature,
    ROUND(AVG(CAST(humility AS DECIMAL(10,2))), 2) as avg_humidity,
    ROUND(AVG(CAST(smoke AS DECIMAL(10,2))), 2) as avg_smoke,
    ROUND(AVG(CAST(pm25 AS DECIMAL(10,2))), 2) as avg_pm25
FROM acw_greenhouse_monitor
WHERE gh_id = 1  -- 假设查询1号大棚
  AND data_time >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
GROUP BY DATE_FORMAT(data_time, '%m-%d %H:00')
ORDER BY hour_slot;

SELECT '=== 问题诊断结束 ===' AS info;