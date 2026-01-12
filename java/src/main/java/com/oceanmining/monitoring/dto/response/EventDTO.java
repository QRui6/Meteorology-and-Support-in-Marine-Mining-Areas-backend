package com.oceanmining.monitoring.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 事件响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDTO {
    
    /**
     * 事件ID
     */
    private String id;
    
    /**
     * 事件类型
     */
    private String type;
    
    /**
     * 船舶MMSI
     */
    private Long mmsi;
    
    /**
     * 事件数据
     */
    private Map<String, Object> data;
    
    /**
     * 事件时间
     */
    private LocalDateTime time;
    
    /**
     * 事件分类 (event/warning)
     */
    private String category;
    
    /**
     * 预警类型（仅当category为warning时）
     */
    private String warningType;
    
    /**
     * 严重程度（仅当category为warning时）
     */
    private String severity;
    
    /**
     * 预警消息（仅当category为warning时）
     */
    private String message;
    
    /**
     * 气象数据（仅当category为warning时）
     */
    private Map<String, Object> weatherData;
    
    /**
     * 是否已解决（仅当category为warning时）
     */
    private Boolean isResolved;
    
    /**
     * 解决时间（仅当category为warning时）
     */
    private LocalDateTime resolvedAt;
}
