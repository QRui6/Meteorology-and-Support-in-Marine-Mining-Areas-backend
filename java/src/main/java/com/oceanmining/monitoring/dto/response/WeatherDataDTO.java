package com.oceanmining.monitoring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 气象数据DTO
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataDTO {
    
    /**
     * 时间索引
     */
    private Integer timeIndex;
    
    /**
     * U分量数据
     */
    private ComponentData u;
    
    /**
     * V分量数据
     */
    private ComponentData v;
    
    /**
     * 经度网格数量
     */
    private Integer width;
    
    /**
     * 纬度网格数量
     */
    private Integer height;
    
    /**
     * 边界信息
     */
    private BoundsInfo bounds;
    
    /**
     * 分量数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentData {
        /**
         * 数据数组
         */
        private float[] array;
        
        /**
         * 最小值
         */
        private Float min;
        
        /**
         * 最大值
         */
        private Float max;
    }
    
    /**
     * 边界信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoundsInfo {
        /**
         * 西边界（经度最小值）
         */
        private Double west;
        
        /**
         * 南边界（纬度最小值）
         */
        private Double south;
        
        /**
         * 东边界（经度最大值）
         */
        private Double east;
        
        /**
         * 北边界（纬度最大值）
         */
        private Double north;
    }
}
