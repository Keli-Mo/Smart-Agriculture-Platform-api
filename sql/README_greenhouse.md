# 大棚环境数据SQL文件说明

## 文件说明

这个目录包含了大棚环境监测系统的数据库文件：

### 1. `greenhouse_data.sql` - 大棚基础数据文件
包含以下内容：
- 大棚信息表 (`acw_greenhouse`) 的创建和数据
- 大棚监测数据表 (`acw_greenhouse_monitor`) 的创建和数据
- 4个示范大棚的基础信息
- 每座大棚3天的环境监测数据（每天3个时间点）
- 扩展表结构（光照监测、土壤监测）的SQL注释

### 2. `test_greenhouse_data.sql` - 数据验证文件
用于验证数据正确性和完整性，包含：
- 表结构验证
- 数据统计分析
- 环境数据范围检查
- 最新数据查询

## 数据结构

### 大棚信息表 (acw_greenhouse)
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 大棚ID |
| name | varchar | 大棚名称 |
| location | varchar | 详细位置 |
| length/width/height | varchar | 长宽高尺寸 |
| area | varchar | 种植面积 |
| crop | varchar | 种植作物 |
| lng/lat | varchar | 经纬度坐标 |
| picture | varchar | 图片路径 |
| remark | varchar | 备注说明 |

### 大棚监测数据表 (acw_greenhouse_monitor)
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 监测记录ID |
| gh_id | bigint | 关联大棚ID |
| device_no | varchar | 设备编号 |
| temperature | varchar | 温度 (°C) |
| humility | varchar | 湿度 (%) |
| smoke | varchar | 烟雾浓度 |
| pm25 | varchar | PM2.5 |
| data_time | datetime | 监测时间 |
| remark | varchar | 备注说明 |

## 使用步骤

### 1. 执行基础数据SQL
```bash
mysql -u your_username -p your_database < greenhouse_data.sql
```

### 2. 验证数据
```bash
mysql -u your_username -p your_database < test_greenhouse_data.sql
```

### 3. 在应用程序中查看
启动应用后访问：
- 大棚管理：`http://localhost:5502/acw/greenhouse`
- 监测数据：`http://localhost:5502/acw/greenhouse-monitor`

## 数据内容

### 示范大棚
1. **一号智能温室** - 番茄种植，现代化温控
2. **二号生态大棚** - 黄瓜种植，生态模式
3. **三号育苗温室** - 专业育苗，补光系统
4. **四号实验大棚** - 科研试验，新品种测试

### 环境数据范围
- **温度**: 20-30°C (根据不同作物需求)
- **湿度**: 55-80% (育苗区较高，成株区适中)
- **PM2.5**: 8-20 μg/m³ (良好至轻度污染)
- **烟雾**: 0.00-0.02 (极低浓度)

## 扩展功能

SQL文件中包含了扩展表结构的注释：
- `acw_light_monitor` - 光照监测数据
- `acw_soil_monitor` - 土壤监测数据

如需使用，只需取消注释并执行相应SQL。

## 注意事项

1. 确保数据库字符集为 `utf8mb4` 以支持中文
2. 外键约束可能需要根据实际数据库配置调整
3. 时间数据以2025年3月为基准，可根据需要修改
4. 坐标数据为北京地区示例，可根据实际情况调整

## 数据分析

可以通过以下SQL进行数据分析：

```sql
-- 查看各大棚的平均环境指标
SELECT
    gh.name,
    AVG(CAST(m.temperature as DECIMAL)) as avg_temp,
    AVG(CAST(m.humility as DECIMAL)) as avg_humidity,
    COUNT(*) as data_points
FROM acw_greenhouse gh
JOIN acw_greenhouse_monitor m ON gh.id = m.gh_id
GROUP BY gh.id, gh.name;

-- 查看环境异常数据
SELECT
    gh.name,
    m.*
FROM acw_greenhouse_monitor m
JOIN acw_greenhouse gh ON m.gh_id = gh.id
WHERE CAST(m.temperature as DECIMAL) > 30
   OR CAST(m.humility as DECIMAL) > 80
   OR CAST(m.pm25 as DECIMAL) > 25;
```