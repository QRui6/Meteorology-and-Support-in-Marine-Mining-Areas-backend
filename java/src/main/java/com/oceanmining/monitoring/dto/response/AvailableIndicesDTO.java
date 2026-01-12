package com.oceanmining.monitoring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 可用时间索引DTO
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableIndicesDTO {
    
    /**
     * 数据类型
     */
    private String type;
    
    /**
     * 可用的时间索引列表
     */
    private List<Integer> availableIndices;
    
    /**
     * 总帧数
     */
    private Integer totalFrames;
}
