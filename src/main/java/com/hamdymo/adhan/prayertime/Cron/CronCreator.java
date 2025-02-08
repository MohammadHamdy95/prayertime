package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class CronCreator {

    private static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;

    /**
     * Used to add Cronjobs to the txt file.
     * @param cron the job that we would to add to crontext file.
     */
    public void addCronJobToContabFile(String cron) {
        fileFacade.addLineToFile(cron, CRONTAB_TXT);
    }

    /**
     * Add job to rerun application at 11:57 pm local time, to reset cronjobs and create new ones
     * for the next day
     */
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
        String[] commands = {"which", "gradle"};
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        return stdInput.readLine();
    }

    public void createCrontabFromFile() throws IOException, InterruptedException {

        String filePath = fileFacade.getFilenamePath(CRONTAB_TXT);
        StringBuilder cronEntries = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cronEntries.append(line).append("\n");
            }
        }

        String cronCommand = String.format("echo \"%s\" | crontab -", cronEntries.toString());

        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cronCommand);
        processBuilder.redirectErrorStream(true); // Redirect error stream for debugging

        Process process = processBuilder.start();

        try (java.io.InputStream inputStream = process.getInputStream();
             java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A")) {
            String output = s.hasNext() ? s.next() : "";
        }

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
