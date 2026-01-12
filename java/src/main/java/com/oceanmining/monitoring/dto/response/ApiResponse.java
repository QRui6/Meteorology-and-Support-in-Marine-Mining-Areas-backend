package com.oceanmining.monitoring.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API响应格式
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    /**
     * 是否成功
     */
    private Boolean success;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 错误信息
     */
    private String error;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }
    
    /**
     * 成功响应（无数据）
     */
    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .success(true)
                .timestamp(System.currentTimeMillis())
                .build();
    }
    
    /**
     * 错误响应
     */
    public static <T> ApiResponse<T> error(String error) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(error)
                .timestamp(System.currentTimeMillis())
                .build();
    }
    
    /**
     * 错误响应（带异常）
     */
    public static <T> ApiResponse<T> error(Exception e) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
