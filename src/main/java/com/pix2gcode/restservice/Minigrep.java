package com.pix2gcode.restservice;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Minigrep {

    public String create(String filename) {
        try {
            //TODO: Dynamic values.
            Process process = new ProcessBuilder("/Users/garykertis/dev/minigrep/target/debug/minigrep", filename, "4", "1").start();
            log.debug("Waiting for minigrep...");

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
