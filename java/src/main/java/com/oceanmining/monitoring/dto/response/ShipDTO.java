package com.oceanmining.monitoring.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oceanmining.monitoring.enums.RiskLevel;
import com.oceanmining.monitoring.enums.ShipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 船舶信息响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipDTO {
    
    /**
     * 船舶MMSI号
     */
    private Long mmsi;
    
    /**
     * 船舶名称
     */
    private String shipName;
    
    /**
     * 船舶中文名
     */
    private String shipCnName;
    
    /**
     * 进入时间
     */
    private LocalDateTime enterTime;
    
    /**
     * 离开时间
     */
    private LocalDateTime leaveTime;
    
    /**
     * 状态
     */
    private ShipStatus status;
    
    /**
     * 最后位置
     */
    private PositionDTO lastPosition;
    
    /**
     * 最新气象数据
     */
    private WeatherDTO lastWeather;
    
    /**
     * 风险等级
     */
    private RiskLevel riskLevel;
    
    /**
     * IMO号
     */
    private String imo;
    
    /**
     * 呼号
     */
    private String callSign;
    
    /**
     * 船舶类型
     */
    private String shipType;
    
    /**
     * 船长
     */
    private Double length;
    
    /**
     * 船宽
     */
    private Double width;
    
    /**
     * 吃水
     */
    private Double draught;
    
    /**
     * 目的地
     */
    private String destination;
    
    /**
     * 预计到达时间
     */
    private String eta;
    
    /**
     * 对地速度
     */
    private Double sog;
    
    /**
     * 对地航向
     */
    private Double cog;
    
    /**
     * 船首向
     */
    private Double hdg;
    
    /**
     * 纬度
     */
    private Double lat;
    
    /**
     * 经度
     */
    private Double lng;
    
    /**
     * 最后更新时间
     */
    private String lastTime;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionDTO {
        private Double lat;
        private Double lng;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherDTO {
        private Double windSpeed;
        private Double waveHeight;
        private Double temperature;
        private String windDir;
        private Double humidity;
        private Double pressure;
        private Double visibility;
        private String publishTime;
    }
}
