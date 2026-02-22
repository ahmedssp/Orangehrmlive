package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class TestDataUtil {

    public static JsonNode readJson(String relativePath) {
        try {
            String path = System.getProperty("user.dir") + File.separator + relativePath;
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + relativePath, e);
        }
    }
}