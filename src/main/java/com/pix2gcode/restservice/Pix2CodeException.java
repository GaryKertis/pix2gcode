package com.pix2gcode.restservice;

public class Pix2CodeException extends RuntimeException {

    Pix2CodeException(String errorMessage) {
        super(errorMessage);
    }
}
