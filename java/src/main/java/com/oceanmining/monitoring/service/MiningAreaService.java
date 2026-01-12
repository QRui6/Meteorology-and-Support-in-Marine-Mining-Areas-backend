package com.oceanmining.monitoring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oceanmining.monitoring.dto.geojson.GeoJsonFeature;
import com.oceanmining.monitoring.dto.geojson.GeoJsonFeatureCollection;
import com.oceanmining.monitoring.dto.geojson.GeoJsonGeometry;
import com.oceanmining.monitoring.entity.MiningArea;
import com.oceanmining.monitoring.repository.MiningAreaRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.LinearRing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MiningAreaService {
    
    private static final Logger logger = LoggerFactory.getLogger(MiningAreaService.class);
    
    @Autowired
    private MiningAreaRepository repository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private final GeometryFactory geometryFactory = new GeometryFactory();
    
    @Transactional
    public int importFromGeoJson() throws IOException {
        logger.info("开始从GeoJSON文件导入矿区数据...");
        
        ClassPathResource resource = new ClassPathResource("static/data/ocean_mining_final.geojson");
        JsonNode rootNode = objectMapper.readTree(resource.getInputStream());
        
        JsonNode features = rootNode.get("features");
        if (features == null || !features.isArray()) {
            throw new IOException("无效的GeoJSON格式");
        }
        
        int count = 0;
        for (JsonNode feature : features) {
            try {
                MiningArea area = parseMiningArea(feature);
                repository.save(area);
                count++;
            } catch (Exception e) {
                logger.error("解析矿区数据失败: {}", e.getMessage());
            }
        }
        
        logger.info("成功导入 {} 个矿区", count);
        return count;
    }
    
    private MiningArea parseMiningArea(JsonNode feature) {
        MiningArea area = new MiningArea();
        
        JsonNode properties = feature.get("properties");
        if (properties != null) {
            area.setAreaId(getStringValue(properties, "id"));
            area.setCategory(getStringValue(properties, "category"));
            area.setMineral(getStringValue(properties, "mineral"));
            area.setLocation(getStringValue(properties, "location"));
            area.setContractor(getStringValue(properties, "contractor"));
            area.setSponsor(getStringValue(properties, "sponsor"));
            area.setDateRange(getStringValue(properties, "date_range"));
            area.setStatus(getStringValue(properties, "status"));
            area.setColor(getStringValue(properties, "color"));
            
            JsonNode areaKm2Node = properties.get("area_km2");
            if (areaKm2Node != null && !areaKm2Node.isNull()) {
                area.setAreaKm2(new BigDecimal(areaKm2Node.asText()));
            }
        }
        
        JsonNode geometry = feature.get("geometry");
        if (geometry != null) {
            area.setCoordinates(geometry.get("coordinates"));
            area.setGeometry(parseGeometry(geometry));
        }
        
        return area;
    }
    
    private Polygon parseGeometry(JsonNode geometry) {
        try {
            String type = geometry.get("type").asText();
            JsonNode coordinates = geometry.get("coordinates");
            
            if ("Polygon".equals(type) && coordinates.isArray() && coordinates.size() > 0) {
                JsonNode ring = coordinates.get(0);
                List<Coordinate> coords = new ArrayList<>();
                
                for (JsonNode point : ring) {
                    double lon = point.get(0).asDouble();
                    double lat = point.get(1).asDouble();
                    coords.add(new Coordinate(lon, lat));
                }
                
                Coordinate[] coordArray = coords.toArray(new Coordinate[0]);
                LinearRing linearRing = geometryFactory.createLinearRing(coordArray);
                Polygon polygon = geometryFactory.createPolygon(linearRing);
                polygon.setSRID(4326);
                
                return polygon;
            }
        } catch (Exception e) {
            logger.error("解析几何对象失败: {}", e.getMessage());
        }
        return null;
    }
    
    private String getStringValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : null;
    }
    
    public GeoJsonFeatureCollection getAllAsGeoJson() {
        List<MiningArea> areas = repository.findAll();
        List<GeoJsonFeature> features = areas.stream()
                .map(this::toGeoJsonFeature)
                .collect(Collectors.toList());
        
        return new GeoJsonFeatureCollection(features);
    }
    
    private GeoJsonFeature toGeoJsonFeature(MiningArea area) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", area.getAreaId());
        properties.put("category", area.getCategory());
        properties.put("mineral", area.getMineral());
        properties.put("area_km2", area.getAreaKm2());
        properties.put("location", area.getLocation());
        properties.put("contractor", area.getContractor());
        properties.put("sponsor", area.getSponsor());
        properties.put("date_range", area.getDateRange());
        properties.put("status", area.getStatus());
        properties.put("color", area.getColor());
        
        GeoJsonGeometry geometry = new GeoJsonGeometry("Polygon", 
            objectMapper.convertValue(area.getCoordinates(), Object.class));
        
        return new GeoJsonFeature(geometry, properties);
    }
    
    public List<MiningArea> findAll() {
        return repository.findAll();
    }
    
    public Optional<MiningArea> findByAreaId(String areaId) {
        return repository.findByAreaId(areaId);
    }
    
    public List<MiningArea> findByCategory(String category) {
        return repository.findByCategory(category);
    }
    
    public List<MiningArea> findBySponsor(String sponsor) {
        return repository.findBySponsor(sponsor);
    }
    
    public List<String> getAllCategories() {
        return repository.findAllCategories();
    }
    
    public List<String> getAllSponsors() {
        return repository.findAllSponsors();
    }
}
