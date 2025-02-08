package com.hamdymo.adhan.prayertime.facade;

import com.google.gson.Gson;
import com.hamdymo.adhan.prayertime.domain.model.Config;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@AllArgsConstructor
public class FileFacade {
    private Gson gson;

    public static final String CONFIG_TXT = "Config.txt";

    /**
     * This will read any txt file in the configuration directory of the package.  This is only for printing.
     * @param fileName
     * @throws IOException
     */
    public void viewFile(String fileName) throws Exception {
        String pathString = getProjectPatch(fileName);
        Path path = Paths.get(pathString);
        try {
            List<String> file = Files.readAllLines(path);
            for (String str : file) {
                System.out.println(str);
            }
        } catch (Exception e) {
            throw new Exception("File not found at" + pathString);
        }
    }

    public String getProjectPatch(String fileName) {
        String root = System.getProperty("user.dir");
        String os = System.getProperty("os.name");
        String fileDir = String.format("/configuration/%s", fileName);
//        if (os.equals("Mac OS X")) {
//            root = "/home/modev/workspace/prayertime";
//        }
        root = root+fileDir;
        return root;
    }

    public void addLineToFile(String textToAppend, String file) {
        String filePath = getProjectPatch(file);
        try {
            Files.write(Paths.get(filePath), textToAppend.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(filePath), System.lineSeparator().getBytes(), StandardOpenOption.APPEND); // Add newline
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    public void deleteFile(String fileName) throws IOException {
        String pathString = getProjectPatch(fileName);
        Path path = Paths.get(pathString);
        Files.deleteIfExists(path);
    }

    public void createFile(String fileName) throws IOException {
        String pathString = getProjectPatch(fileName);
        Path path = Paths.get(pathString);
        Files.createFile(path);
    }

    public Config getConfigObject() {
        try (FileReader reader = new FileReader(getProjectPatch(CONFIG_TXT))) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
