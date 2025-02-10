package com.hamdymo.adhan.prayertime.facade;

import com.google.gson.Gson;
import com.hamdymo.adhan.prayertime.domain.model.Config;
import com.hamdymo.adhan.prayertime.domain.model.IqamahOffset;
import com.hamdymo.adhan.prayertime.domain.model.User;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class FileFacade {
    public static final String CONFIG_TXT = "Config.txt";
    public static final String IQAMAH_OFFSETS_TXT = "IqamahOffsets.txt";
    public static final String USERS_JSON = "Users.json";
    public static final String USERS = "users";
    private Gson gson;

    /**
     * This will read any txt file in the configuration directory of the package.  This is only for printing.
     *
     * @param fileName
     * @throws IOException
     */
    public void viewFile(String fileName) throws Exception {
        String pathString = getFilenamePath(fileName);
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

    public String getFilenamePath(String fileName) {
        String root = System.getProperty("user.dir");
        String fileDir = String.format("/configuration/%s", fileName);
        root = root + fileDir;
        return root;
    }

    public void addLineToFile(String textToAppend, String file) {
        String filePath = getFilenamePath(file);
        try {
            Files.write(Paths.get(filePath), textToAppend.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(filePath), System.lineSeparator().getBytes(), StandardOpenOption.APPEND); // Add newline
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    public void deleteFile(String fileName) throws IOException {
        String pathString = getFilenamePath(fileName);
        Path path = Paths.get(pathString);
        Files.deleteIfExists(path);
    }

    public void createFile(String fileName) throws IOException {
        String pathString = getFilenamePath(fileName);
        Path path = Paths.get(pathString);
        Files.createFile(path);
    }

    public String getConfigCity() {
        //Zip code takes priority over city name.
        try (FileReader reader = new FileReader(getFilenamePath(CONFIG_TXT))) {
            Config config = gson.fromJson(reader, Config.class);
            return config.getZipCode() != null ? config.getZipCode() : config.getCity();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Config getConfigFile() {
        try (FileReader reader = new FileReader(getFilenamePath(CONFIG_TXT))) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IqamahOffset getIqamahOffsetConfig() {
        try (FileReader reader = new FileReader(getFilenamePath(IQAMAH_OFFSETS_TXT))) {
            return gson.fromJson(reader, IqamahOffset.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,User> getUsers() throws IOException {
        String filenamePath = getFilenamePath(USERS_JSON);
        Path path = Paths.get(filenamePath);
        String json = Files.readString(path);
        JSONObject test = new JSONObject(json);
        JSONArray cfDatabase  = test.getJSONArray(USERS);
        Map<String, User> collect = IntStream
                .range(0, cfDatabase.length())
                .mapToObj(cfDatabase::getJSONObject)
                .map(cfCustomer -> gson.fromJson(String.valueOf(cfCustomer), User.class))
                .collect(Collectors.toMap(User::getName, cfCustomer -> cfCustomer));
        return collect;
    }
}
