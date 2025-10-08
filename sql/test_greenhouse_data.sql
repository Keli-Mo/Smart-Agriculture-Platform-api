-- 测试大棚数据SQL文件
-- 这个文件用于验证大棚数据的正确性

-- 检查数据库是否存在
SELECT '大棚基础数据表验证开始' AS info;

-- 验证大棚信息表结构
DESCRIBE acw_greenhouse;

-- 验证大棚监测数据表结构
DESCRIBE acw_greenhouse_monitor;

-- 检查大棚基础数据
SELECT '=== 大棚基础信息 ===' AS section;
SELECT id, name, location, area, crop,
       CONCAT(length, '×', width, '×', height, 'm') as dimensions,
       CONCAT(lat, ', ', lng) as coordinates
FROM acw_greenhouse
ORDER BY id;

-- 检查大棚数量
SELECT '=== 大棚统计信息 ===' AS section;
SELECT COUNT(*) as total_greenhouses FROM acw_greenhouse;

-- 检查环境监测数据
SELECT '=== 环境监测数据概览 ===' AS section;
SELECT
    gh.name as greenhouse_name,
    COUNT(m.id) as total_records,
    ROUND(AVG(CAST(m.temperature as DECIMAL(5,1))), 1) as avg_temperature,
    ROUND(AVG(CAST(m.humility as DECIMAL(5,1))), 1) as avg_humidity,
    ROUND(AVG(CAST(m.pm25 as DECIMAL(5,1))), 1) as avg_pm25,
    MIN(m.data_time) as earliest_record,
    MAX(m.data_time) as latest_record
FROM acw_greenhouse gh
LEFT JOIN acw_greenhouse_monitor m ON gh.id = m.gh_id
GROUP BY gh.id, gh.name
ORDER BY gh.id;

-- 检查最新的环境数据
SELECT '=== 最新环境数据 ===' AS section;
SELECT
    gh.name,
    m.temperature,
    m.humility,
    m.smoke,
    m.pm25,
    m.data_time,
    m.remark
FROM acw_greenhouse_monitor m
JOIN acw_greenhouse gh ON m.gh_id = gh.id
WHERE m.data_time >= DATE_SUB(NOW(), INTERVAL 3 DAY)
ORDER BY m.data_time DESC
LIMIT 10;

-- 检查温度范围
SELECT '=== 温度范围分析 ===' AS section;
SELECT
    gh.name,
    MIN(CAST(m.temperature as DECIMAL(5,1))) as min_temp,
    MAX(CAST(m.temperature as DECIMAL(5,1))) as max_temp,
    ROUND(AVG(CAST(m.temperature as DECIMAL(5,1))), 1) as avg_temp
FROM acw_greenhouse gh
JOIN acw_greenhouse_monitor m ON gh.id = m.gh_id
GROUP BY gh.id, gh.name
ORDER BY gh.id;

-- 检查湿度范围
SELECT '=== 湿度范围分析 ===' AS section;
SELECT
    gh.name,
    MIN(CAST(m.humility as DECIMAL(5,1))) as min_humidity,
    MAX(CAST(m.humility as DECIMAL(5,1))) as max_humidity,
    ROUND(AVG(CAST(m.humility as DECIMAL(5,1))), 1) as avg_humidity
FROM acw_greenhouse gh
JOIN acw_greenhouse_monitor m ON gh.id = m.gh_id
GROUP BY gh.id, gh.name
ORDER BY gh.id;

SELECT '大棚数据验证完成' AS info;