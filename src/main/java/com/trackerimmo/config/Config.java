
package com.trackerimmo.config;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

@Component
public class Config {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	
    private final Properties properties = new Properties();

    private boolean useEnvironmentVariables;

	private String configFile;
    
    public Config(Environment env) {
    	try {
    		configFile = env.getProperty("spring.config.file");
			init(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Config error : " + e.getMessage());
		}
    }

    public void init(String file) throws IOException {
        try {
            Properties mainProperties = new Properties();
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file)) {
                mainProperties.loadFromXML(inputStream);
            }

            String defaultConfigFile = mainProperties.getProperty("config.default");
            if (defaultConfigFile != null) {
                try (InputStream inputStream = new FileInputStream(defaultConfigFile)) {
                    properties.loadFromXML(inputStream);
                }
            }

            properties.putAll(mainProperties); // override defaults

            useEnvironmentVariables = Boolean.parseBoolean(System.getenv("CONFIG_USE_ENVIRONMENT_VARIABLES"))
                    || Boolean.parseBoolean(properties.getProperty("config.useEnvironmentVariables"));
        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException("Configuration file is not a valid XML document", e);
        }
    }

    public boolean hasKey(ConfigKey<?> key) {
        return hasKey(key.getKey());
    }

//    @Deprecated
    public boolean hasKey(String key) {
        return useEnvironmentVariables && System.getenv().containsKey(getEnvironmentVariableName(key))
                || properties.containsKey(key);
    }

    public String getString(ConfigKey<String> key) {
        return getString(key.getKey(), key.getDefaultValue());
    }

    @Deprecated
    public String getString(String key) {
        if (useEnvironmentVariables) {
            String value = System.getenv(getEnvironmentVariableName(key));
            if (value != null && !value.isEmpty()) {
                return value;
            }
        }
        return properties.getProperty(key);
    }

    public String getString(ConfigKey<String> key, String defaultValue) {
        return getString(key.getKey(), defaultValue);
    }

    @Deprecated
    public String getString(String key, String defaultValue) {
        return hasKey(key) ? getString(key) : defaultValue;
    }

    public boolean getBoolean(ConfigKey<Boolean> key) {
        return getBoolean(key.getKey());
    }

    @Deprecated
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }

    public int getInteger(ConfigKey<Integer> key) {
        String value = getString(key.getKey());
        if (value != null) {
            return Integer.parseInt(value);
        } else {
            Integer defaultValue = key.getDefaultValue();
            if (defaultValue != null) {
                return defaultValue;
            } else {
                return 0;
            }
        }
    }

    public int getInteger(ConfigKey<Integer> key, int defaultValue) {
        return getInteger(key.getKey(), defaultValue);
    }

    @Deprecated
    public int getInteger(String key, int defaultValue) {
        return hasKey(key) ? Integer.parseInt(getString(key)) : defaultValue;
    }

    public long getLong(ConfigKey<Long> key) {
        String value = getString(key.getKey());
        if (value != null) {
            return Long.parseLong(value);
        } else {
            Long defaultValue = key.getDefaultValue();
            if (defaultValue != null) {
                return defaultValue;
            } else {
                return 0;
            }
        }
    }

    public double getDouble(ConfigKey<Double> key) {
        String value = getString(key.getKey());
        if (value != null) {
            return Double.parseDouble(value);
        } else {
            Double defaultValue = key.getDefaultValue();
            if (defaultValue != null) {
                return defaultValue;
            } else {
                return 0;
            }
        }
    }

    @VisibleForTesting
    public void setString(ConfigKey<?> key, String value) {
        properties.put(key.getKey(), value);
    }

    static String getEnvironmentVariableName(String key) {
        return key.replaceAll("\\.", "_").replaceAll("(\\p{Lu})", "_$1").toUpperCase();
    }

}
