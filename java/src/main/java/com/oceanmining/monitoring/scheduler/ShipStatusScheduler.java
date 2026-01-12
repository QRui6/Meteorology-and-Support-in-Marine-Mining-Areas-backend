package com.oceanmining.monitoring.scheduler;

import com.oceanmining.monitoring.entity.AreaShip;
import com.oceanmining.monitoring.entity.MonitoringArea;
import com.oceanmining.monitoring.enums.ShipStatus;
import com.oceanmining.monitoring.repository.AreaRepository;
import com.oceanmining.monitoring.repository.ShipRepository;
import com.oceanmining.monitoring.service.ShipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 船舶状态定时更新任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShipStatusScheduler {

    private final AreaRepository areaRepository;
    private final ShipRepository shipRepository;
    private final ShipService shipService;

    /**
     * 定时更新区域内船舶状态（每10分钟）
     */
    @Scheduled(fixedRate = 600000) // 10分钟 = 600000毫秒
    public void updateShipStatus() {
        log.info("⏰ 执行定时更新任务...");

        try {
            // 获取所有活跃区域
            List<MonitoringArea> areas = areaRepository.findByIsActiveTrue();

            for (MonitoringArea area : areas) {
                // 获取区域内的船舶
                List<AreaShip> ships = shipRepository.findByArea_IdAndStatus(
                        area.getId(), ShipStatus.IN_AREA
                );

                for (AreaShip ship : ships) {
                    try {
                        shipService.updateShipStatus(ship);
                    } catch (Exception e) {
                        log.error("更新船舶状态失败: mmsi={}", ship.getMmsi(), e);
                    }
                }
            }

            log.info("✅ 定时更新完成");

        } catch (Exception e) {
            log.error("定时更新失败", e);
        }
    }
}
