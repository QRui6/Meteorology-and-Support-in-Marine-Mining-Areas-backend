package com.oceanmining.monitoring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 气象数据元数据DTO
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherMetadataDTO {
    
    /**
     * 元数据ID
     */
    private Long id;
    
    /**
     * 数据类型
     */
    private String type;
    
    /**
     * 网格信息
     */
    private GridInfo grid;
    
    /**
     * 起始时间
     */
    private String startTime;
    
    /**
     * 时间步长（小时）
     */
    private Integer timeStepHours;
    
    /**
     * 总帧数
     */
    private Integer frames;
    
    /**
     * 数据来源
     */
    private String dataSource;
    
    /**
     * 网格信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GridInfo {
        /**
         * 经度网格数量
         */
        private Integer lonSize;
        
        /**
         * 纬度网格数量
         */
        private Integer latSize;
        
        /**
         * 经度最小值
         */
        private Double lonMin;
        
        /**
         * 纬度最小值
         */
        private Double latMin;
        
        /**
         * 经度最大值
         */
        private Double lonMax;
        
        /**
         * 纬度最大值
         */
        private Double latMax;
        
        /**
         * 经度步长
         */
        private Double lonStep;
        
        /**
         * 纬度步长
         */
        private Double latStep;
    }
}
