package com.oceanmining.monitoring.util;

import com.oceanmining.monitoring.dto.response.WeatherDataDTO;
import com.oceanmining.monitoring.dto.response.WeatherMetadataDTO;
import com.oceanmining.monitoring.entity.*;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

/**
 * 气象数据转换器
 * 用于实体与DTO之间的转换
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@Slf4j
public class WeatherDataConverter {
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    
    /**
     * 将WeatherMetadata实体转换为DTO
     * 
     * @param metadata 元数据实体
     * @return 元数据DTO
     */
    public static WeatherMetadataDTO toMetadataDTO(WeatherMetadata metadata) {
        if (metadata == null) {
            return null;
        }
        
        WeatherMetadataDTO.GridInfo gridInfo = WeatherMetadataDTO.GridInfo.builder()
                .lonSize(metadata.getGridLonSize())
                .latSize(metadata.getGridLatSize())
                .lonMin(metadata.getGridLonMin().doubleValue())
                .latMin(metadata.getGridLatMin().doubleValue())
                .lonMax(metadata.getGridLonMax().doubleValue())
                .latMax(metadata.getGridLatMax().doubleValue())
                .lonStep(metadata.getGridLonStep() != null ? metadata.getGridLonStep().doubleValue() : null)
                .latStep(metadata.getGridLatStep() != null ? metadata.getGridLatStep().doubleValue() : null)
                .build();
        
        return WeatherMetadataDTO.builder()
                .id(metadata.getId())
                .type(metadata.getDataType().getTypeCode())
                .grid(gridInfo)
                .startTime(metadata.getStartTime() != null ? metadata.getStartTime().format(DATE_TIME_FORMATTER) : null)
                .timeStepHours(metadata.getTimeStepHours())
                .frames(metadata.getTotalFrames())
                .dataSource(metadata.getDataSource())
                .build();
    }
    
    /**
     * 将WindData实体转换为DTO
     * 
     * @param windData 风场数据实体
     * @param metadata 元数据实体
     * @return 气象数据DTO
     */
    public static WeatherDataDTO toWeatherDataDTO(WindData windData, WeatherMetadata metadata) {
        if (windData == null || metadata == null) {
            return null;
        }
        
        float[] uArray = BinaryDataConverter.bytesToFloatArray(windData.getUComponent());
        float[] vArray = BinaryDataConverter.bytesToFloatArray(windData.getVComponent());
        
        return buildWeatherDataDTO(
                windData.getTimeIndex(),
                uArray, vArray,
                windData.getUMin(), windData.getUMax(),
                windData.getVMin(), windData.getVMax(),
                metadata
        );
    }
    
    /**
     * 将OceanCurrentData实体转换为DTO
     * 
     * @param currentData 洋流数据实体
     * @param metadata 元数据实体
     * @return 气象数据DTO
     */
    public static WeatherDataDTO toWeatherDataDTO(OceanCurrentData currentData, WeatherMetadata metadata) {
        if (currentData == null || metadata == null) {
            return null;
        }
        
        float[] uArray = BinaryDataConverter.bytesToFloatArray(currentData.getUComponent());
        float[] vArray = BinaryDataConverter.bytesToFloatArray(currentData.getVComponent());
        
        return buildWeatherDataDTO(
                currentData.getTimeIndex(),
                uArray, vArray,
                currentData.getUMin(), currentData.getUMax(),
                currentData.getVMin(), currentData.getVMax(),
                metadata
        );
    }
    
    /**
     * 将WaveData实体转换为DTO
     * 
     * @param waveData 波浪数据实体
     * @param metadata 元数据实体
     * @return 气象数据DTO
     */
    public static WeatherDataDTO toWeatherDataDTO(WaveData waveData, WeatherMetadata metadata) {
        if (waveData == null || metadata == null) {
            return null;
        }
        
        float[] uArray = BinaryDataConverter.bytesToFloatArray(waveData.getUComponent());
        float[] vArray = BinaryDataConverter.bytesToFloatArray(waveData.getVComponent());
        
        return buildWeatherDataDTO(
                waveData.getTimeIndex(),
                uArray, vArray,
                waveData.getUMin(), waveData.getUMax(),
                waveData.getVMin(), waveData.getVMax(),
                metadata
        );
    }
    
    /**
     * 构建WeatherDataDTO
     * 
     * @param timeIndex 时间索引
     * @param uArray U分量数组
     * @param vArray V分量数组
     * @param uMin U最小值
     * @param uMax U最大值
     * @param vMin V最小值
     * @param vMax V最大值
     * @param metadata 元数据
     * @return 气象数据DTO
     */
    private static WeatherDataDTO buildWeatherDataDTO(
            Integer timeIndex,
            float[] uArray, float[] vArray,
            Float uMin, Float uMax,
            Float vMin, Float vMax,
            WeatherMetadata metadata) {
        
        WeatherDataDTO.ComponentData uComponent = WeatherDataDTO.ComponentData.builder()
                .array(uArray)
                .min(uMin)
                .max(uMax)
                .build();
        
        WeatherDataDTO.ComponentData vComponent = WeatherDataDTO.ComponentData.builder()
                .array(vArray)
                .min(vMin)
                .max(vMax)
                .build();
        
        WeatherDataDTO.BoundsInfo bounds = WeatherDataDTO.BoundsInfo.builder()
                .west(metadata.getGridLonMin().doubleValue())
                .south(metadata.getGridLatMin().doubleValue())
                .east(metadata.getGridLonMax().doubleValue())
                .north(metadata.getGridLatMax().doubleValue())
                .build();
        
        return WeatherDataDTO.builder()
                .timeIndex(timeIndex)
                .u(uComponent)
                .v(vComponent)
                .width(metadata.getGridLonSize())
                .height(metadata.getGridLatSize())
                .bounds(bounds)
                .build();
    }
}
