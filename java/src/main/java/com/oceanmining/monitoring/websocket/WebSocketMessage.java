package com.oceanmining.monitoring.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket消息格式
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    
    /**
     * 消息类型
     * area_created - 区域创建
     * area_deleted - 区域删除
     * ship_enter - 船舶进入
     * ship_leave - 船舶离开
     * ship_update - 船舶状态更新
     * warning - 预警
     */
    private String type;
    
    /**
     * 消息负载
     */
    private Object payload;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 创建消息
     */
    public static WebSocketMessage create(String type, Object payload) {
        return WebSocketMessage.builder()
                .type(type)
                .payload(payload)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
