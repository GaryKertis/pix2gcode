package com.pix2gcode.restservice;

import lombok.Data;

@Data
public class MinigrepRequest {
    private final String filename;
    private final String pixelSize;
    private String diagonalIntervalSize = "1";
}
