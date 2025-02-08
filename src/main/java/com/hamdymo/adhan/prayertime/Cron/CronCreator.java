package com.hamdymo.adhan.prayertime.Cron;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@AllArgsConstructor
public class CronCreator {

    private static final String CRONTAB_TXT = "Crontab.txt";
    private FileFacade fileFacade;

    public void addCronJobToContabFile(String cron) {
        fileFacade.addLineToFile(cron, CRONTAB_TXT);
    }

    public void createCronjobFileToCronjob() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"/usr/bin/crontab", "<", fileFacade.getFilenamePath(CRONTAB_TXT)};
        Process proc = rt.exec(commands);
        System.out.printf("%s %s %s",commands[0],commands[1],commands[2]);
    }

//    public void testingCronJobs() throws IOException, InterruptedException {
//        Runtime runtime = Runtime.getRuntime();
//        String[] commands = {"crontab", "-e"};
//
//        Process proc = runtime.exec(commands);
//
//        InputStream stdIn = proc.getInputStream();
//        InputStreamReader isr = new InputStreamReader(stdIn);
//        BufferedReader br = new BufferedReader(isr);
//
//        String line = null;
//        System.out.println("<OUTPUT>");
//
//        while ((line = br.readLine()) != null)
//            System.out.println(line);
//
//        System.out.println("</OUTPUT>");
//        int exitVal = proc.waitFor();
//        System.out.println("Process exitValue: " + exitVal);
//    }

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
}
