package com.pix2gcode.restservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
public class Pix2CodeController {


    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final StorageService storageService;
    private final Minigrep minigrep;

    @Autowired
    public Pix2CodeController(StorageService storageService, Minigrep minigrep) {
        this.storageService = storageService;
        this.minigrep = minigrep;
    }

    @GetMapping("/greeting")
    public Pix2Code greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Pix2Code(counter.incrementAndGet(), String.format(template, name));
    }

    @PostMapping("/upload")
    @ResponseBody
    public byte[] handleFileUpload(@RequestParam("file") MultipartFile file,
                          RedirectAttributes redirectAttributes) {
        String savedFile = storageService.store(file);
        log.debug("Saved file {}", savedFile);

        String convertedFile = minigrep.create(savedFile);
        log.debug("Converted file {}", convertedFile);

        return storageService.retrieve(convertedFile);
    }
}
