
package com.trackerimmo.config;

public class ConfigKey<T> {

    private final String key;
    private final T defaultValue;

    ConfigKey(String key,T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }
    
    ConfigKey(String key) {
        this.key = key;
        this.defaultValue = null;
    }

    String getKey() {
        return key;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

}
