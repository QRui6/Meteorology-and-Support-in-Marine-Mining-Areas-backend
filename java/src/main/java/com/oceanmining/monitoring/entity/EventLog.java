package com.oceanmining.monitoring.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

/**
 * 事件日志实体
 * 对应数据库表：event_logs
 */
@Entity
@Table(name = "event_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 所属区域
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private MonitoringArea area;
    
    /**
     * 船舶MMSI号
     */
    private Long mmsi;
    
    /**
     * 事件类型 (enter/leave/warning/update)
     */
    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;
    
    /**
     * 事件数据 (JSONB格式)
     */
    @Type(JsonBinaryType.class)
    @Column(name = "event_data", columnDefinition = "jsonb")
    private String eventData;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
