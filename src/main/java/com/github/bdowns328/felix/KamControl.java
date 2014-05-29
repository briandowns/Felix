package com.github.bdowns328.felix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KamControl {
    /**
     * Reload the Kamailio Dispatcher module data.
     * @return string
     */
    private String restartKamailioDaemon() {
        String restartCommand = "kamctl dispatcher reload";
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(restartCommand);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return(output.toString());
    }
}
