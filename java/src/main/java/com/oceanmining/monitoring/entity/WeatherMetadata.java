package com.oceanmining.monitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 气象数据元数据实体
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Entity
@Table(name = "weather_metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherMetadata {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 数据类型
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_type_id", nullable = false)
    private WeatherDataType dataType;
    
    /**
     * 经度网格数量
     */
    @Column(name = "grid_lon_size", nullable = false)
    private Integer gridLonSize;
    
    /**
     * 纬度网格数量
     */
    @Column(name = "grid_lat_size", nullable = false)
    private Integer gridLatSize;
    
    /**
     * 经度最小值
     */
    @Column(name = "grid_lon_min", nullable = false, precision = 10, scale = 6)
    private BigDecimal gridLonMin;
    
    /**
     * 纬度最小值
     */
    @Column(name = "grid_lat_min", nullable = false, precision = 10, scale = 6)
    private BigDecimal gridLatMin;
    
    /**
     * 经度最大值
     */
    @Column(name = "grid_lon_max", nullable = false, precision = 10, scale = 6)
    private BigDecimal gridLonMax;
    
    /**
     * 纬度最大值
     */
    @Column(name = "grid_lat_max", nullable = false, precision = 10, scale = 6)
    private BigDecimal gridLatMax;
    
    /**
     * 经度步长
     */
    @Column(name = "grid_lon_step", precision = 10, scale = 6)
    private BigDecimal gridLonStep;
    
    /**
     * 纬度步长
     */
    @Column(name = "grid_lat_step", precision = 10, scale = 6)
    private BigDecimal gridLatStep;
    
    /**
     * 起始时间
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    /**
     * 时间步长（小时）
     */
    @Column(name = "time_step_hours")
    private Integer timeStepHours;
    
    /**
     * 总时间帧数
     */
    @Column(name = "total_frames", nullable = false)
    private Integer totalFrames;
    
    /**
     * 数据来源
     */
    @Column(name = "data_source", length = 100)
    private String dataSource;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
