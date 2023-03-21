package net.projectuniverse.general.config;

public class ConfigValueBuilder {

    private ConfigValue configValue;

    public ConfigValueBuilder(String path, String rawValue) {
        configValue = new ConfigValue(path, rawValue);
    }

    public ConfigValueBuilder addParameter(ConfigParam param) {
        configValue.addParam(param);
        return this;
    }
    public ConfigValueBuilder addParameter(String name, String value) {
        configValue.addParam(new ConfigParam(name, value));
        return this;
    }
    public ConfigValueBuilder setRawValue(String rawValue) {
        configValue.setRawValue(rawValue);
        return this;
    }
    public ConfigValue build() { return configValue; }

}
