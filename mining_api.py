from flask import Flask, jsonify
from flask_cors import CORS
import psycopg2
import json

app = Flask(__name__)
CORS(app)  # 允许跨域请求

# 数据库连接配置
DB_CONFIG = {
    'host': 'localhost',
    'port': 5432,
    'database': 'ship_monitoring',
    'user': 'postgres',
    'password': '030525'
}

def get_db_connection():
    """获取数据库连接"""
    return psycopg2.connect(**DB_CONFIG)

@app.route('/api/mining-areas/geojson', methods=['GET'])
def get_mining_areas_geojson():
    """返回所有矿区数据的GeoJSON格式"""
    try:
        conn = get_db_connection()
        cur = conn.cursor()
        
        # 查询所有矿区数据
        cur.execute("""
            SELECT 
                area_id, category, mineral, area_km2, location,
                contractor, sponsor, date_range, status, color,
                coordinates
            FROM mining_areas
            ORDER BY area_id
        """)
        
        rows = cur.fetchall()
        
        # 构建GeoJSON
        features = []
        for row in rows:
            area_id, category, mineral, area_km2, location, contractor, sponsor, date_range, status, color, coordinates = row
            
            feature = {
                "type": "Feature",
                "geometry": {
                    "type": "Polygon",
                    "coordinates": coordinates
                },
                "properties": {
                    "id": area_id,
                    "category": category,
                    "mineral": mineral,
                    "area_km2": float(area_km2) if area_km2 else None,
                    "location": location,
                    "contractor": contractor,
                    "sponsor": sponsor,
                    "date_range": date_range,
                    "status": status,
                    "color": color
                }
            }
            features.append(feature)
        
        geojson = {
            "type": "FeatureCollection",
            "features": features
        }
        
        cur.close()
        conn.close()
        
        return jsonify(geojson)
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/mining-areas/stats', methods=['GET'])
def get_stats():
    """返回统计信息"""
    try:
        conn = get_db_connection()
        cur = conn.cursor()
        
        # 总数
        cur.execute("SELECT COUNT(*) FROM mining_areas")
        total = cur.fetchone()[0]
        
        # 按类别统计
        cur.execute("SELECT category, COUNT(*) FROM mining_areas GROUP BY category")
        by_category = {row[0]: row[1] for row in cur.fetchall()}
        
        # 按国家统计
        cur.execute("SELECT sponsor, COUNT(*) FROM mining_areas WHERE sponsor IS NOT NULL GROUP BY sponsor ORDER BY COUNT(*) DESC LIMIT 10")
        by_sponsor = {row[0]: row[1] for row in cur.fetchall()}
        
        cur.close()
        conn.close()
        
        return jsonify({
            "total": total,
            "by_category": by_category,
            "by_sponsor": by_sponsor
        })
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/health', methods=['GET'])
def health():
    """健康检查"""
    return jsonify({"status": "ok"})

if __name__ == '__main__':
    print("🚀 矿区API服务启动中...")
    print("📍 API地址: http://localhost:8082/api/mining-areas/geojson")
    print("📊 统计信息: http://localhost:8082/api/mining-areas/stats")
    app.run(host='0.0.0.0', port=8082, debug=True)
