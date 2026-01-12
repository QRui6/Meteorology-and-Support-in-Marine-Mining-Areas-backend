# 海洋矿区 API 使用说明

## 概述

该模块提供海洋矿区数据的存储和查询功能,支持标准 GeoJSON 格式输出,可与 Cesium 前端无缝集成。

## 数据库初始化

### 1. 执行 SQL 脚本

```bash
psql -U postgres -d ship_monitoring -f backend/database_mining_areas.sql
```

这将创建:
- `mining_areas` 表
- 空间索引
- 查询函数
- 统计视图

## 数据导入

### 方式一: 通过 API 导入 (推荐)

启动应用后,调用导入接口:

```bash
curl -X POST http://localhost:8080/api/mining-areas/import
```

响应示例:
```json
{
  "success": true,
  "count": 150,
  "message": "Successfully imported 150 mining areas"
}
```

### 方式二: 手动 SQL 导入

如果需要手动导入,可以编写 SQL 脚本读取 GeoJSON 文件。

## API 接口

### 1. 获取所有矿区 (GeoJSON 格式)

**前端使用 - 替换原有文件加载**

```javascript
// 原来: 从文件加载
dataSource = await loadGeoJson(viewer, '/data/ocean_mining_final.geojson', options);

// 改为: 从 API 加载
dataSource = await loadGeoJson(viewer, 'http://localhost:8080/api/mining-areas/geojson', options);
```

**接口详情**

```
GET /api/mining-areas/geojson
```

**查询参数**:
- `category` (可选): 按类别筛选 (APEI/Exploration)
- `sponsor` (可选): 按赞助国筛选
- `status` (可选): 按状态筛选 (Protected/Active)

**示例**:
```bash
# 获取所有矿区
curl http://localhost:8080/api/mining-areas/geojson

# 获取环境保护区
curl http://localhost:8080/api/mining-areas/geojson?category=APEI

# 获取中国赞助的矿区
curl http://localhost:8080/api/mining-areas/geojson?sponsor=中国

# 获取活跃状态的勘探区
curl http://localhost:8080/api/mining-areas/geojson?category=Exploration&status=Active
```

**响应格式** (标准 GeoJSON):
```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "properties": {
        "id": "APEI-10",
        "category": "APEI",
        "mineral": "环境特别受关注区",
        "area_km2": 160000,
        "location": "太平洋 (CCZ)",
        "contractor": "环境特别受关注区 (APEI-10)",
        "sponsor": "全人类共同继承财产",
        "date_range": "永久保护",
        "status": "Protected",
        "color": "#2E8B57"
      },
      "geometry": {
        "type": "Polygon",
        "coordinates": [[[lng, lat], [lng, lat], ...]]
      }
    }
  ]
}
```

### 2. 获取所有矿区 (列表格式)

```
GET /api/mining-areas
```

返回 MiningArea 对象数组。

### 3. 按 ID 查询矿区

```
GET /api/mining-areas/{id}
GET /api/mining-areas/area/{areaId}
```

示例:
```bash
curl http://localhost:8080/api/mining-areas/area/APEI-10
```

### 4. 按类别查询

```
GET /api/mining-areas/category/{category}
```

示例:
```bash
curl http://localhost:8080/api/mining-areas/category/APEI
curl http://localhost:8080/api/mining-areas/category/Exploration
```

### 5. 按赞助国查询

```
GET /api/mining-areas/sponsor/{sponsor}
```

示例:
```bash
curl http://localhost:8080/api/mining-areas/sponsor/中国
curl http://localhost:8080/api/mining-areas/sponsor/德国
```

### 6. 空间查询 - 查找点所在的矿区

```
GET /api/mining-areas/point?lng={longitude}&lat={latitude}
```

示例:
```bash
curl "http://localhost:8080/api/mining-areas/point?lng=-140.5&lat=12.3"
```

### 7. 获取元数据

```
GET /api/mining-areas/metadata/categories   # 所有类别
GET /api/mining-areas/metadata/sponsors     # 所有赞助国
GET /api/mining-areas/metadata/statuses     # 所有状态
```

## 前端集成

### 修改 MapContainer.vue

只需修改一行代码:

```javascript
// 在 loadMiningData() 函数中
// 原来:
dataSource = await loadGeoJson(viewer, '/data/ocean_mining_final.geojson', {
    strokeColor: Cesium.Color.WHITE,
    fillColor: Cesium.Color.RED.withAlpha(0.5),
    strokeWidth: 2,
    clampToGround: false
});

// 改为:
dataSource = await loadGeoJson(viewer, 'http://localhost:8080/api/mining-areas/geojson', {
    strokeColor: Cesium.Color.WHITE,
    fillColor: Cesium.Color.RED.withAlpha(0.5),
    strokeWidth: 2,
    clampToGround: false
});
```

### 添加筛选功能 (可选)

```javascript
// 按类别筛选
dataSource = await loadGeoJson(
    viewer, 
    'http://localhost:8080/api/mining-areas/geojson?category=APEI',
    options
);

// 按国家筛选
dataSource = await loadGeoJson(
    viewer, 
    'http://localhost:8080/api/mining-areas/geojson?sponsor=中国',
    options
);
```

## 数据库查询示例

### SQL 查询

```sql
-- 查询所有矿区
SELECT * FROM mining_areas;

-- 按类别统计
SELECT * FROM v_mining_area_statistics;

-- 按国家统计
SELECT * FROM v_mining_area_by_sponsor;

-- 空间查询: 查找点所在的矿区
SELECT * FROM find_mining_areas_at_point(-140.5, 12.3);

-- 查询某个国家的矿区总面积
SELECT sponsor, SUM(area_km2) as total_area
FROM mining_areas
GROUP BY sponsor
ORDER BY total_area DESC;
```

## 性能优化

1. **空间索引**: 已自动创建 GIST 空间索引,提高空间查询性能
2. **普通索引**: 为常用查询字段创建了索引
3. **缓存**: 建议在生产环境中添加 Redis 缓存 GeoJSON 响应

## 故障排查

### 问题 1: 导入失败

**检查**:
- GeoJSON 文件是否存在于 `src/main/resources/static/data/`
- 数据库表是否已创建
- 数据库连接是否正常

### 问题 2: 前端无法加载数据

**检查**:
- 后端服务是否启动
- CORS 配置是否正确
- 浏览器控制台是否有错误信息

### 问题 3: 空间查询不准确

**检查**:
- PostGIS 扩展是否已安装
- geometry 字段是否正确生成
- 坐标系是否为 EPSG:4326

## 扩展功能

### 1. 添加船舶-矿区关联查询

可以查询船舶是否进入矿区:

```java
@GetMapping("/ships-in-areas")
public ResponseEntity<Map<String, List<Ship>>> getShipsInMiningAreas() {
    // 实现逻辑: 查询所有船舶,判断是否在矿区内
}
```

### 2. 添加矿区预警

当船舶进入特定矿区时发出警告:

```java
@Service
public class MiningAreaAlertService {
    public void checkShipInRestrictedArea(Ship ship) {
        List<MiningArea> areas = miningAreaService.findAreasAtPoint(
            ship.getLongitude(), 
            ship.getLatitude()
        );
        // 检查是否为受保护区域
        // 发送警告
    }
}
```

## 总结

该模块提供了完整的海洋矿区数据管理功能:
- ✅ 标准 GeoJSON 格式输出
- ✅ 与 Cesium 前端无缝集成
- ✅ 支持空间查询
- ✅ 支持多种筛选条件
- ✅ 性能优化 (索引、空间索引)
- ✅ 易于扩展

前端只需修改一行代码即可从 API 加载数据,完全兼容现有功能!
