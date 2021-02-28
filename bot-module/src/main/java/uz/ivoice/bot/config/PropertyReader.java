package uz.ivoice.bot.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyReader {
    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return getProperty(key, "");
    }

    public String getProperty(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }

    public PropertyReader(String propertiesFileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (Objects.nonNull(inputStream)) {
                properties.load(inputStream);
            }
        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
        }
    }
}
