package utils;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class ConfigReader {

    private static Map<String, Object> config;

    static {
        InputStream input =
                ConfigReader.class.getClassLoader()
                        .getResourceAsStream("config.yaml");

        Yaml yaml = new Yaml();
        config = yaml.load(input);
    }

    public static String getBrowser() {
        String browser = System.getProperty("browser");
        return browser != null ? browser : config.get("browser").toString();
    }

    public static String getUrl() {
        return config.get("url").toString();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(config.get("headless").toString());
    }

    public static int getImplicitWait() {
        return Integer.parseInt(config.get("implicitWait").toString());
    }
}