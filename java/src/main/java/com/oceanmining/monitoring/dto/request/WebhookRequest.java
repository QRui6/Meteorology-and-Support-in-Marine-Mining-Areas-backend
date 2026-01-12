package com.oceanmining.monitoring.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 船讯网Webhook推送请求DTO
 */
@Data
public class WebhookRequest {
    
    /**
     * 区域ID
     */
    @JsonProperty("area_id")
    private String areaId;
    
    /**
     * 事件类型: 1=进入, 2=离开
     */
    @JsonProperty("event_type")
    private Integer eventType;
    
    /**
     * 船舶MMSI号
     */
    private Long mmsi;
    
    /**
     * 船舶名称
     */
    @JsonProperty("ship_name")
    private String shipName;
    
    /**
     * 纬度
     */
    private Double lat;
    
    /**
     * 经度
     */
    private Double lng;
    
    /**
     * 事件时间
     */
    @JsonProperty("event_time")
    private String eventTime;
    
    /**
     * UTC事件时间
     */
    @JsonProperty("event_time_utc")
    private Long eventTimeUtc;
    
    /**
     * IMO号
     */
    private String imo;
    
    /**
     * 呼号
     */
    @JsonProperty("call_sign")
    private String callSign;
}
