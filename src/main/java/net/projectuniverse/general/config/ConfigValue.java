package net.projectuniverse.general.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigValue {

    private String path, rawValue, value;
    private List<ConfigParam> params;

    public ConfigValue(String path, String rawValue, List<ConfigParam> params) {
        this.path = path;
        this.params = params;
        setRawValue(rawValue);
    }

    public ConfigValue(String path, String rawValue, ConfigParam param) { this(path, rawValue, List.of(param)); }
    public ConfigValue(String path, String rawValue) {
        this(path, rawValue, new ArrayList<>());
    }

    public void setValue() {
        value = rawValue;
        params.forEach(param -> value = value.replace(param.getName(), param.getValue()));
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
        setValue();
    }

    public String getPath() {
        return path;
    }

    public String getRawValue() {
        return rawValue;
    }

    public String getValue() {
        return value;
    }

    public List<ConfigParam> getParams() {
        return params;
    }
    public Optional<ConfigParam> getParams(String paramName) {
        for(ConfigParam param : params) {
            if(param.getName().equals(paramName)) return Optional.of(param);
        }
        return Optional.empty();
    }

    public void addParam(ConfigParam param) {
        params.add(param);
        setValue();
    }

    @Override
    public ConfigValue clone() {
        return new ConfigValue(path, rawValue, params);
    }

}
