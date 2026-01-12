package com.oceanmining.monitoring.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 监控区域实体
 * 对应数据库表：monitoring_areas
 */
@Entity
@Table(name = "monitoring_areas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringArea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 船讯网返回的区域ID
     */
    @Column(name = "area_id", unique = true, nullable = false, length = 50)
    private String areaId;
    
    /**
     * 区域名称
     */
    @Column(nullable = false, length = 100)
    private String name;
    
    /**
     * 多边形坐标 (JSONB格式)
     * 存储格式: [[lng,lat],[lng,lat],...]
     */
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private String polygon;
    
    /**
     * PostGIS几何对象
     * 用于空间查询
     */
    @Column(columnDefinition = "geometry(Polygon,4326)")
    private Polygon geometry;
    
    /**
     * 风速阈值 (m/s)
     */
    @Column(name = "threshold_wind_speed", precision = 5, scale = 2)
    private BigDecimal thresholdWindSpeed;
    
    /**
     * 浪高阈值 (m)
     */
    @Column(name = "threshold_wave_height", precision = 5, scale = 2)
    private BigDecimal thresholdWaveHeight;
    
    /**
     * 是否激活
     */
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 区域内的船舶列表（一对多关系）
     */
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AreaShip> ships;
    
    /**
     * 区域的预警列表（一对多关系）
     */
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Warning> warnings;
    
    /**
     * 区域的事件日志列表（一对多关系）
     */
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventLog> eventLogs;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
