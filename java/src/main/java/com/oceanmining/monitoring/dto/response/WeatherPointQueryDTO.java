package com.oceanmining.monitoring.dto.response;

import lombok.Data;

/**
 * 气象点查询响应 DTO
 */
@Data
public class WeatherPointQueryDTO {
    private LocationInfo location;
    private String timestamp;
    private WeatherVector wind;
    private WeatherVector wave;
    private WeatherVector current;

    @Data
    public static class LocationInfo {
        private double lat;
        private double lon;

        public LocationInfo(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }

    @Data
    public static class WeatherVector {
        private double u;           // U分量
        private double v;           // V分量
        private double speed;       // 速度/强度
        private double direction;   // 方向（度）
        private Double height;      // 浪高（仅波浪数据有效）

        public WeatherVector(double u, double v, double speed, double direction) {
            this.u = u;
            this.v = v;
            this.speed = speed;
            this.direction = direction;
        }
    }
}
