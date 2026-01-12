package com.oceanmining.monitoring.dto;

import lombok.Data;

/**
 * 网格数据结构
 */
@Data
public class GridData {
    private int width;
    private int height;
    private double west;
    private double south;
    private double east;
    private double north;
    private float[][] u;  // U分量二维数组
    private float[][] v;  // V分量二维数组

    public GridData(int width, int height, double west, double south, double east, double north) {
        this.width = width;
        this.height = height;
        this.west = west;
        this.south = south;
        this.east = east;
        this.north = north;
        this.u = new float[height][width];
        this.v = new float[height][width];
    }
}
