package com.pix2gcode.restservice;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Minigrep {

    public String create(MinigrepRequest minigrepRequest) {
        try {

            Process process = new ProcessBuilder("minigrep",
                    minigrepRequest.getFilename(),
                    minigrepRequest.getPixelSize(),
                    minigrepRequest.getDiagonalIntervalSize()
            ).start();
            log.debug("Waiting for minigrep... {}", minigrepRequest);

            process.waitFor(10, TimeUnit.SECONDS);

            if (process.exitValue() != 0) {
                log.debug("Exit value: {}", process.exitValue());
                String error = handleMinigrepResponse(process.getErrorStream());
                throw new IOException(error);
            }

            return handleMinigrepResponse(process.getInputStream());

        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new Pix2CodeException("Error creating the file!");
        }
    }

    private String handleMinigrepResponse(InputStream response) throws IOException {
        InputStreamReader esr = new InputStreamReader(response);
        BufferedReader ebr = new BufferedReader(esr);
        String line;
        String result = "";
        while ((line = ebr.readLine()) != null) {
            result += line;
        }
        return result;
    }
}
