package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class CronCreator {

    private static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;

    public void addCronJobToContabFile(String cron) {
        fileFacade.addLineToFile(cron, CRONTAB_TXT);
    }

    public void addRerunCronjob() throws IOException {
        String test = String.format("""
                57 11 * * * cd %s && %s run
                """, getProjectDirectory(), getGradleLocation());
        addCronJobToContabFile(test);
    }
    private String getProjectDirectory() {
        String root = System.getProperty("user.dir");
        return root;
    }

    private String getGradleLocation() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"which","gradle"};
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        return stdInput.readLine();
    }

    public void createCrontabFromFile() throws IOException, InterruptedException {

        // 1. Read the cron entries from the file.
        String filePath = fileFacade.getFilenamePath(CRONTAB_TXT);
        StringBuilder cronEntries = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // You might want to add validation here to check the format of each line.
                // For example, using a regular expression to match cron syntax.
                cronEntries.append(line).append("\n");
            }
        }

        // 2. Construct the cron command.
        String cronCommand = String.format("echo \"%s\" | crontab -", cronEntries.toString());

        // 3. Execute the command using ProcessBuilder.
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cronCommand);
        processBuilder.redirectErrorStream(true); // Redirect error stream for debugging

        Process process = processBuilder.start();

        // 4. Handle process output (recommended for debugging).
        try (java.io.InputStream inputStream = process.getInputStream();
             java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A")) {
            String output = s.hasNext() ? s.next() : "";
            System.out.println("Crontab update output:\n" + output);
        }

        // 5. Wait for the process to complete and check the exit code.
        boolean finished = process.waitFor(10, TimeUnit.SECONDS); // Timeout

        if (!finished) {
            process.destroyForcibly();
            throw new IOException("Crontab update timed out.");
        }

        int exitCode = process.exitValue();
        if (exitCode != 0) {
            throw new IOException("Crontab update failed with exit code: " + exitCode + ". Check output for errors.");
        }

        System.out.println("Crontab updated successfully from file: " + filePath);
    }
}
