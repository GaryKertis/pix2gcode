package com.pix2gcode.restservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Slf4j
public class Pix2CodeController {

    private final StorageService storageService;
    private final Minigrep minigrep;

    @Autowired
    public Pix2CodeController(StorageService storageService, Minigrep minigrep) {
        this.storageService = storageService;
        this.minigrep = minigrep;
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String ping() {
        return "Hello!";
    }

    @CrossOrigin
    @PostMapping("/upload")
    public Pix2Code greeting(@RequestParam("file") MultipartFile file,
                          RedirectAttributes redirectAttributes) {
        String savedFile = storageService.store(file);
        log.debug("Saved file {}", savedFile);

        String convertedFile = minigrep.create(savedFile);
        log.debug("Converted file {}", convertedFile);

        String fileContents = storageService.readFile(convertedFile);

        return new Pix2Code(convertedFile.hashCode(), fileContents);
    }

    @CrossOrigin
    @PostMapping("/uploadFile")
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
