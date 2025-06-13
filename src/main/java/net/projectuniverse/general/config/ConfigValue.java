package net.projectuniverse.general.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Represents a configuration value.
 */

public class ConfigValue {

    private String path, rawValue, value;
    private List<ConfigParam> params;

    public ConfigValue(String path, String rawValue, List<ConfigParam> params) {
        this.path = path;
        this.params = params;
        setRawValue(rawValue);
    }

    public ConfigValue(String path, String rawValue, ConfigParam... params) {
        this(path, rawValue, Arrays.stream(params).toList());
    }

    public ConfigValue(String path, String rawValue, ConfigParam param) { this(path, rawValue, List.of(param)); }
    public ConfigValue(String path, String rawValue) {
        this(path, rawValue, new ArrayList<>());
    }

    /**
     * Sets the value of the specified config parameter.
     *
     * @param name the name of the config parameter
     * @param value the new value to set
     * @return the updated ConfigValue object
     */
    public ConfigValue setConfigParamValue(String name, String value) {
        for(ConfigParam param : params) {
            if(param.getName().equals(name)) {
                param.setValue(value);
                setValue();
                break;
            }
        }
        return this;
    }

    /**
     * Sets the value of the specified config parameter.
     *
     * @param param the config parameter object containing the name and value to be set
     * @return the updated ConfigValue object
     */
    public ConfigValue setConfigParamValue(ConfigParam param) {
        return setConfigParamValue(param.getName(), param.getValue());
    }

    /**
     * Sets the value of the config parameter.
     *
     * This method sets the value of the config parameter by replacing the values of any placeholders
     * in the config parameter with their corresponding values from the params list. The updated value
     * is then assigned to the 'value' field of the config parameter.
     */
    public void setValue() {
        value = rawValue;
        params.forEach(param -> value = value.replace(param.getName(), param.getValue()));
    }

    /**
     * Sets the raw value of the config parameter.
     *
     * This method sets the raw value of the config parameter. The raw value is the initial value
     * before any placeholders are replaced with their corresponding values. After setting the raw value,
     * the method calls the setValue() method to update the 'value' field of the config parameter
     * with the updated value.
     *
     * @param rawValue the raw value to set for the config parameter
     */
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
