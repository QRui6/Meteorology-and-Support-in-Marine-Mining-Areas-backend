import json
from collections import Counter

# 读取GeoJSON文件
with open('../public/data/ocean_mining_final.geojson', 'r', encoding='utf-8') as f:
    data = json.load(f)

features = data['features']
print(f"总features数: {len(features)}")

# 提取所有ID
ids = [f['properties'].get('id', '') for f in features]
print(f"唯一ID数: {len(set(ids))}")

# 找出重复的ID
id_counts = Counter(ids)
duplicates = {k: v for k, v in id_counts.items() if v > 1}

if duplicates:
    print(f"\n发现 {len(duplicates)} 个重复的ID:")
    for id_val, count in sorted(duplicates.items(), key=lambda x: x[1], reverse=True):
        print(f"  {id_val}: 出现 {count} 次")
else:
    print("\n没有重复的ID")

# 检查是否有空ID
empty_ids = sum(1 for id_val in ids if not id_val)
if empty_ids > 0:
    print(f"\n警告: 有 {empty_ids} 个空ID")

# 显示一些示例数据
print("\n前5个features的ID:")
for i, f in enumerate(features[:5]):
    print(f"  {i+1}. {f['properties'].get('id', 'NO_ID')}")
