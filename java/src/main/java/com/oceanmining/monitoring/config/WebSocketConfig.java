package com.oceanmining.monitoring.config;

import com.oceanmining.monitoring.websocket.ShipMonitoringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置类
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(shipMonitoringWebSocketHandler(), "/ws")
                .setAllowedOrigins("*");  // 生产环境应该配置具体的域名
    }
    
    @Bean
    public ShipMonitoringWebSocketHandler shipMonitoringWebSocketHandler() {
        return new ShipMonitoringWebSocketHandler();
    }
}
