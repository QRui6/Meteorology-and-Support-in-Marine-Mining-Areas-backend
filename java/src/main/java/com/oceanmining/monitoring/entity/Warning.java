package com.oceanmining.monitoring.entity;

import com.oceanmining.monitoring.enums.WarningSeverity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

/**
 * 预警记录实体
 * 对应数据库表：warnings
 */
@Entity
@Table(name = "warnings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warning {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 所属区域
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    private MonitoringArea area;
    
    /**
     * 船舶MMSI号
     */
    @Column(nullable = false)
    private Long mmsi;
    
    /**
     * 预警类型 (wind/wave/typhoon等)
     */
    @Column(name = "warning_type", length = 50)
    private String warningType;
    
    /**
     * 严重程度
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "warning_severity")
    private WarningSeverity severity;
    
    /**
     * 预警消息
     */
    @Column(columnDefinition = "TEXT")
    private String message;
    
    /**
     * 气象数据 (JSONB格式)
     */
    @Type(JsonBinaryType.class)
    @Column(name = "weather_data", columnDefinition = "jsonb")
    private String weatherData;
    
    /**
     * 是否已解决
     */
    @Column(name = "is_resolved")
    private Boolean isResolved = false;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 解决时间
     */
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (isResolved == null) {
            isResolved = false;
        }
        if (severity == null) {
            severity = WarningSeverity.LOW;
        }
    }
    
    /**
     * 解决预警
     */
    public void resolve() {
        this.isResolved = true;
        this.resolvedAt = LocalDateTime.now();
    }
}
