package com.pix2gcode.restservice;

public class Pix2Code {

    private final long id;
    private final String content;

    public Pix2Code(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

