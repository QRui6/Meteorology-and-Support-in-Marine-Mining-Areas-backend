package com.oceanmining.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 船舶区域监控系统 - 主应用类
 * 
 * @author Ocean Mining Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class ShipMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipMonitoringApplication.class, args);
        System.out.println("""
            
            ========================================
            🚀 船舶监控系统启动成功！
            📡 API地址: http://localhost:8081
            🔌 WebSocket: ws://localhost:8081/ws
            ========================================
            """);
    }
}
