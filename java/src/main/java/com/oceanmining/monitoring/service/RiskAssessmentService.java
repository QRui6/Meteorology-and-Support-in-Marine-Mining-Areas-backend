package com.oceanmining.monitoring.service;

import cn.hutool.json.JSONObject;
import com.oceanmining.monitoring.entity.MonitoringArea;
import com.oceanmining.monitoring.enums.RiskLevel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 风险评估服务
 */
@Slf4j
@Service
public class RiskAssessmentService {

    /**
     * 评估风险
     */
    public RiskAssessment assessRisk(MonitoringArea area, JSONObject weather) {
        RiskAssessment assessment = new RiskAssessment();
        List<RiskItem> risks = new ArrayList<>();
        
        BigDecimal windSpeed = weather.getBigDecimal("windspeed", BigDecimal.ZERO);
        BigDecimal waveHeight = weather.getBigDecimal("waveheight", BigDecimal.ZERO);

        // 风速检查
        if (windSpeed.compareTo(area.getThresholdWindSpeed()) > 0) {
            RiskItem windRisk = new RiskItem();
            windRisk.setType("wind");
            windRisk.setValue(windSpeed);
            windRisk.setThreshold(area.getThresholdWindSpeed());
            risks.add(windRisk);
            assessment.setLevel(RiskLevel.HIGH);
        }

        // 浪高检查
        if (waveHeight.compareTo(area.getThresholdWaveHeight()) > 0) {
            RiskItem waveRisk = new RiskItem();
            waveRisk.setType("wave");
            waveRisk.setValue(waveHeight);
            waveRisk.setThreshold(area.getThresholdWaveHeight());
            risks.add(waveRisk);
            
            if (assessment.getLevel() != RiskLevel.HIGH) {
                assessment.setLevel(RiskLevel.MEDIUM);
            }
        }

        if (risks.isEmpty()) {
            assessment.setLevel(RiskLevel.SAFE);
        }

        assessment.setWarning(!risks.isEmpty());
        assessment.setRisks(risks);
        assessment.setWeather(weather);

        return assessment;
    }

    @Data
    public static class RiskAssessment {
        private RiskLevel level = RiskLevel.SAFE;
        private boolean isWarning = false;
        private List<RiskItem> risks = new ArrayList<>();
        private JSONObject weather;
    }

    @Data
    public static class RiskItem {
        private String type;
        private BigDecimal value;
        private BigDecimal threshold;
    }
}
