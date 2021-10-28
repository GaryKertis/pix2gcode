package com.pix2gcode.restservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Slf4j
public class StorageService {

    private String temp = "/tmp/";

    public String store(MultipartFile file) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

        String extension = "png";
        String path = String.format("%s%s-%s.%s", temp, file.getName(), timeStamp, extension) ;
        File dest = new File(path);
        try {
            file.transferTo(dest);
            return path;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new Pix2CodeException("Error saving the uploaded file!");
        }

    }

    public String readFile(String filename) {
        Path filePath = Paths.get(filename);
        try {
            return String.join("\n", Files.readAllLines(filePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new Pix2CodeException("Error retrieving the converted file!");
        }
    }

    public byte[] retrieve(String filename) {
        try {
            return FileUtils.readFileToByteArray(new File(filename));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new Pix2CodeException("Error retrieving the converted file!");
        }
    }
}
