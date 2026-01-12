import json
from collections import Counter

# 读取GeoJSON文件
with open('../public/data/ocean_mining_final.geojson', 'r', encoding='utf-8') as f:
    data = json.load(f)

features = data['features']

# 检查geometry类型
geometry_types = Counter([f['geometry']['type'] for f in features])
print("Geometry类型统计:")
for gtype, count in geometry_types.items():
    print(f"  {gtype}: {count}")

# 检查同一个ID的features
from collections import defaultdict
id_features = defaultdict(list)
for f in features:
    id_val = f['properties'].get('id', '')
    id_features[id_val].append(f)

# 显示一个有多个features的ID
sample_id = 'BMJPMN1'
print(f"\n示例: ID '{sample_id}' 有 {len(id_features[sample_id])} 个features")
for i, f in enumerate(id_features[sample_id][:3]):
    coords = f['geometry']['coordinates']
    print(f"  Feature {i+1}: {len(coords[0])} 个坐标点")
