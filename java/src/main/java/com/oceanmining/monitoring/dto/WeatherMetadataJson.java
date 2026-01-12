package com.oceanmining.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * 气象数据元数据JSON格式
 * 用于解析meta.json文件
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Data
public class WeatherMetadataJson {
    
    private GridInfo grid;
    private Integer frames;
    
    @JsonProperty("time_step_hours")
    private Integer timeStepHours;
    
    @JsonProperty("start_time")
    private String startTime;
    
    @JsonProperty("data_source")
    private String dataSource;
    
    private Map<String, String> variables;
    
    @Data
    public static class GridInfo {
        @JsonProperty("lat_size")
        private Integer latSize;
        
        @JsonProperty("lon_size")
        private Integer lonSize;
        
        @JsonProperty("lat_min")
        private Double latMin;
        
        @JsonProperty("lat_max")
        private Double latMax;
        
        @JsonProperty("lon_min")
        private Double lonMin;
        
        @JsonProperty("lon_max")
        private Double lonMax;
        
        @JsonProperty("lat_step")
        private Double latStep;
        
        @JsonProperty("lon_step")
        private Double lonStep;
    }
}
