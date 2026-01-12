import json
import psycopg2
from psycopg2.extras import Json

# 数据库连接配置
DB_CONFIG = {
    'host': 'localhost',
    'port': 5432,
    'database': 'ship_monitoring',
    'user': 'postgres',
    'password': '030525'
}

def import_geojson_to_db():
    """从GeoJSON文件导入数据到PostgreSQL数据库"""
    
    # 读取GeoJSON文件
    print("正在读取GeoJSON文件...")
    with open('../public/data/ocean_mining_final.geojson', 'r', encoding='utf-8') as f:
        geojson_data = json.load(f)
    
    # 连接数据库
    print("正在连接数据库...")
    conn = psycopg2.connect(**DB_CONFIG)
    cur = conn.cursor()
    
    # 清空现有数据
    print("正在清空现有数据...")
    cur.execute("TRUNCATE TABLE mining_areas RESTART IDENTITY CASCADE;")
    
    # 插入数据 - 保持原始ID,允许重复
    print("正在导入数据...")
    print(f"总共有 {len(geojson_data['features'])} 个polygon要导入")
    count = 0
    
    for idx, feature in enumerate(geojson_data['features']):
        properties = feature.get('properties', {})
        geometry = feature.get('geometry', {})
        coordinates = geometry.get('coordinates', [])
        
        # 提取属性 - 保持原始ID
        area_id = properties.get('id', '')
        
        category = properties.get('category', '')
        mineral = properties.get('mineral')
        area_km2 = properties.get('area_km2')
        location = properties.get('location')
        contractor = properties.get('contractor')
        sponsor = properties.get('sponsor')
        date_range = properties.get('date_range')
        status = properties.get('status')
        color = properties.get('color')
        
        # 构建WKT格式的几何对象
        if coordinates and len(coordinates) > 0:
            # 获取外环坐标
            outer_ring = coordinates[0]
            # 构建POLYGON的WKT格式
            coords_str = ','.join([f"{lon} {lat}" for lon, lat in outer_ring])
            wkt = f"POLYGON(({coords_str}))"
            
            # 插入数据
            insert_sql = """
                INSERT INTO mining_areas (
                    area_id, category, mineral, area_km2, location, 
                    contractor, sponsor, date_range, status, color, 
                    coordinates, geometry
                ) VALUES (
                    %s, %s, %s, %s, %s, 
                    %s, %s, %s, %s, %s, 
                    %s, ST_GeomFromText(%s, 4326)
                )
            """
            
            cur.execute(insert_sql, (
                area_id, category, mineral, area_km2, location,
                contractor, sponsor, date_range, status, color,
                Json(coordinates), wkt
            ))
            
            count += 1
            if count % 100 == 0:
                print(f"已导入 {count} 条数据...")
    
    # 提交事务
    conn.commit()
    
    print(f"\n✅ 成功导入 {count} 条矿区数据!")
    
    # 验证数据
    cur.execute("SELECT COUNT(*) FROM mining_areas;")
    total = cur.fetchone()[0]
    print(f"数据库中共有 {total} 条记录")
    
    # 显示一些统计信息
    cur.execute("SELECT category, COUNT(*) FROM mining_areas GROUP BY category;")
    categories = cur.fetchall()
    print("\n按类别统计:")
    for cat, cnt in categories:
        print(f"  {cat}: {cnt} 个")
    
    # 关闭连接
    cur.close()
    conn.close()
    print("\n数据导入完成!")

if __name__ == '__main__':
    try:
        import_geojson_to_db()
    except Exception as e:
        print(f"❌ 导入失败: {e}")
        import traceback
        traceback.print_exc()
