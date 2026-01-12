package com.oceanmining.monitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 气象数据类型实体
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Entity
@Table(name = "weather_data_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 数据类型代码: wind, ocean_current, wave
     */
    @Column(name = "type_code", unique = true, nullable = false, length = 20)
    private String typeCode;
    
    /**
     * 数据类型名称
     */
    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;
    
    /**
     * 描述信息
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
