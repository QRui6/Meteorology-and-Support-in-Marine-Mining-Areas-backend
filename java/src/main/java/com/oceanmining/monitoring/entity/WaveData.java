package com.oceanmining.monitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 波浪数据实体
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Entity
@Table(name = "wave_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaveData {
    
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
     * Stokes drift U分量二进制数据
     */
    @Column(name = "u_component", nullable = false, columnDefinition = "bytea")
    private byte[] uComponent;
    
    /**
     * Stokes drift V分量二进制数据
     */
    @Column(name = "v_component", nullable = false, columnDefinition = "bytea")
    private byte[] vComponent;
    
    /**
     * 波高二进制数据（可选）
     */
    @Column(name = "wave_height", columnDefinition = "bytea")
    private byte[] waveHeight;
    
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
     * 波高最小值
     */
    @Column(name = "hs_min")
    private Float hsMin;
    
    /**
     * 波高最大值
     */
    @Column(name = "hs_max")
    private Float hsMax;
    
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
