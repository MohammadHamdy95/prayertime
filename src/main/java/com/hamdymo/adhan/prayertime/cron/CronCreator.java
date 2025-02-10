package com.hamdymo.adhan.prayertime.cron;

import com.hamdymo.adhan.prayertime.domain.model.CronSchedule;
import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class CronCreator {

    private static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;

    /**
     * Used to add Cronjobs to the txt file.
     *
     * @param cron the job that we would to add to crontext file.
     */
    public void addCronJobToContabFile(String cron) {
        fileFacade.addLineToFile(cron, CRONTAB_TXT);
    }

    /**
     * Used to add Cronjobs to the txt file.
     *
     * @param cronSchedule the job that we would to add to crontext file.
     */
    public void addLinesToCronTabFile(CronSchedule cronSchedule) {
        // Fjar Adhan
        fileFacade.addLineToFile("# FAJR ADHAN", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getFajrAdhan(), CRONTAB_TXT);

        // Fjar Iqamah
        fileFacade.addLineToFile("# FAJR IQAMAH", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getFajrIqamah(), CRONTAB_TXT);

        //Dhuhr Adhan
        fileFacade.addLineToFile("# DHUHR ADHAN", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getDhuhrAdhan(), CRONTAB_TXT);
        //Dhuhr Iqamah
        fileFacade.addLineToFile("# DHUHR IQAMAH", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getDhuhrIqamah(), CRONTAB_TXT);

        //Asr Adhan
        fileFacade.addLineToFile("# ASR ADHAN", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getAsrAdhan(), CRONTAB_TXT);

        //Asr Iqamah
        fileFacade.addLineToFile("# ASR IQAMAH", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getAsrIqamah(), CRONTAB_TXT);

        //Maghrib Adhan
        fileFacade.addLineToFile("# MAGHRIB ADHAN", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getMaghribAdhan(), CRONTAB_TXT);

        //Maghrib Iqamah
        fileFacade.addLineToFile("# MAGHRIB IQAMAH", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getMaghribIqamah(), CRONTAB_TXT);

        //Isha Adhan
        fileFacade.addLineToFile("# ISHA ADHAN", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getIshaAdhan(), CRONTAB_TXT);

        //Isha Iqamah
        fileFacade.addLineToFile("# ISHA IQAMAH", CRONTAB_TXT);
        fileFacade.addLineToFile(cronSchedule.getIshaIqamah(), CRONTAB_TXT);

    }

    public void addLinesToCronTabFileUpdated(CronSchedule cronSchedule) {

        Map<String, String> schedules = Map.of(
                "FAJR ADHAN", cronSchedule.getFajrAdhan(),
                "FAJR IQAMAH", cronSchedule.getFajrIqamah(),
                "DHUHR ADHAN", cronSchedule.getDhuhrAdhan(),
                "DHUHR IQAMAH", cronSchedule.getDhuhrIqamah(),
                "ASR ADHAN", cronSchedule.getAsrAdhan(),
                "ASR IQAMAH", cronSchedule.getAsrIqamah(),
                "MAGHRIB ADHAN", cronSchedule.getMaghribAdhan(),
                "MAGHRIB IQAMAH", cronSchedule.getMaghribIqamah(),
                "ISHA ADHAN", cronSchedule.getIshaAdhan(),
                "ISHA IQAMAH", cronSchedule.getIshaIqamah()
        );

        schedules.forEach((label, cron) -> {
            fileFacade.addLineToFile("# " + label, CRONTAB_TXT);
            fileFacade.addLineToFile(cron, CRONTAB_TXT);
        });
    }

    /**
     * Add job to rerun application at 11:57 pm local time, to reset cronjobs and create new ones
     * for the next day
     */
    public void addRerunCronjob() throws IOException {
        String test = String.format("""
                03 00 * * * cd %s && %s run
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
        String os = System.getProperty("os.name");
        System.out.println("We are not mac, so we are skipping adding cronjobs");
        if (os.contains("Mac")) {
            return;
        }

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
