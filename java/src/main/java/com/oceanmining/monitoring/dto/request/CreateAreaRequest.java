package com.oceanmining.monitoring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建监控区域请求DTO
 */
@Data
public class CreateAreaRequest {
    
    /**
     * 区域名称
     */
    @NotBlank(message = "区域名称不能为空")
    private String name;
    
    /**
     * 多边形坐标
     * 格式: [[lng, lat], [lng, lat], ...]
     */
    @NotNull(message = "多边形坐标不能为空")
    private List<List<Double>> polygon;
    
    /**
     * 阈值配置
     */
    @NotNull(message = "阈值配置不能为空")
    private ThresholdsDTO thresholds;
    
    @Data
    public static class ThresholdsDTO {
        /**
         * 风速阈值 (m/s)
         */
        private BigDecimal windSpeed = BigDecimal.valueOf(15.0);
        
        /**
         * 浪高阈值 (m)
         */
        private BigDecimal waveHeight = BigDecimal.valueOf(3.0);
    }
}
