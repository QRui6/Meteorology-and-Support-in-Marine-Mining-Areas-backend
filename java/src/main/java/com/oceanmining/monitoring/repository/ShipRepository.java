package com.oceanmining.monitoring.repository;

import com.oceanmining.monitoring.entity.AreaShip;
import com.oceanmining.monitoring.enums.ShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 区域内船舶Repository
 */
@Repository
public interface ShipRepository extends JpaRepository<AreaShip, Long> {
    
    /**
     * 根据区域ID和状态查询船舶
     */
    List<AreaShip> findByArea_IdAndStatus(Long areaId, ShipStatus status);
    
    /**
     * 根据区域ID查询所有在区域内或预警状态的船舶
     */
    @Query("SELECT s FROM AreaShip s WHERE s.area.id = :areaId " +
           "AND s.status IN ('IN_AREA', 'WARNING') " +
           "ORDER BY s.enterTime DESC")
    List<AreaShip> findActiveShipsInArea(@Param("areaId") Long areaId);
    
    /**
     * 根据MMSI和区域ID查询船舶
     */
    Optional<AreaShip> findByMmsiAndArea_IdAndStatus(Long mmsi, Long areaId, ShipStatus status);
    
    /**
     * 根据MMSI查询所有记录
     */
    List<AreaShip> findByMmsi(Long mmsi);
    
    /**
     * 统计区域内船舶数量
     */
    long countByArea_IdAndStatus(Long areaId, ShipStatus status);
    
    /**
     * 查询所有在区域内的船舶
     */
    List<AreaShip> findByStatus(ShipStatus status);
    
    /**
     * 查询预警状态的船舶
     */
    @Query("SELECT s FROM AreaShip s WHERE s.status = 'WARNING' " +
           "ORDER BY s.riskLevel DESC, s.enterTime DESC")
    List<AreaShip> findWarningShips();
    
    /**
     * 根据区域ID、MMSI和状态查询船舶（返回Optional）
     */
    Optional<AreaShip> findByArea_IdAndMmsiAndStatus(Long areaId, Long mmsi, ShipStatus status);
    
    /**
     * 根据区域ID和多个状态查询船舶
     */
    List<AreaShip> findByArea_IdAndStatusIn(Long areaId, List<ShipStatus> statuses);
    
    /**
     * 根据多个状态查询船舶
     */
    List<AreaShip> findByStatusIn(List<ShipStatus> statuses);
}
