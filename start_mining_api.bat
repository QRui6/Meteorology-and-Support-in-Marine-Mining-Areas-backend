@echo off
echo ========================================
echo 启动矿区数据API服务
echo ========================================
echo.
echo 检查Python环境...
python --version
echo.
echo 检查依赖包...
pip show flask >nul 2>&1
if errorlevel 1 (
    echo 安装Flask...
    pip install flask flask-cors psycopg2-binary
)
echo.
echo 启动API服务...
echo API地址: http://localhost:8082/api/mining-areas/geojson
echo.
python mining_api.py
