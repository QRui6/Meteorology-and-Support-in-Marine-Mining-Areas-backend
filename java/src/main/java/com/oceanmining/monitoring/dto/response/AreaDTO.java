package com.oceanmining.monitoring.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 监控区域响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDTO {
    
    /**
     * 区域ID
     */
    private Long id;
    
    /**
     * 船讯网区域ID
     */
    private String areaId;
    
    /**
     * 区域名称
     */
    private String name;
    
    /**
     * 多边形坐标
     */
    private List<List<Double>> polygon;
    
    /**
     * 阈值配置
     */
    private ThresholdsDTO thresholds;
    
    /**
     * 是否激活
     */
    private Boolean isActive;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 区域内船舶数量
     */
    private Integer shipCount;
    
    /**
     * 预警数量
     */
    private Integer warningCount;
    
    /**
     * 区域边界
     */
    private BoundsDTO bounds;
    
    /**
     * 区域面积（平方公里）
     */
    private String area;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThresholdsDTO {
        private BigDecimal windSpeed;
        private BigDecimal waveHeight;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoundsDTO {
        private String minLng;
        private String maxLng;
        private String minLat;
        private String maxLat;
    }
}
