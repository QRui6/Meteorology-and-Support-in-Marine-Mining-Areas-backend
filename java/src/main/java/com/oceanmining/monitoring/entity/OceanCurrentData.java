package com.oceanmining.monitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 洋流数据实体
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Entity
@Table(name = "ocean_current_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OceanCurrentData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 元数据引用
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metadata_id", nullable = false)
    private WeatherMetadata metadata;
    
    /**
     * 时间索引
     */
    @Column(name = "time_index", nullable = false)
    private Integer timeIndex;
    
    /**
     * U分量（东西向）二进制数据
     */
    @Column(name = "u_component", nullable = false, columnDefinition = "bytea")
    private byte[] uComponent;
    
    /**
     * V分量（南北向）二进制数据
     */
    @Column(name = "v_component", nullable = false, columnDefinition = "bytea")
    private byte[] vComponent;
    
    /**
     * U分量最小值
     */
    @Column(name = "u_min", nullable = false)
    private Float uMin;
    
    /**
     * U分量最大值
     */
    @Column(name = "u_max", nullable = false)
    private Float uMax;
    
    /**
     * V分量最小值
     */
    @Column(name = "v_min", nullable = false)
    private Float vMin;
    
    /**
     * V分量最大值
     */
    @Column(name = "v_max", nullable = false)
    private Float vMax;
    
    /**
     * 数据点总数
     */
    @Column(name = "data_size", nullable = false)
    private Integer dataSize;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
