package net.projectuniverse.general.config;

/**
 * A builder class for creating ConfigValue objects.
 * <p>
 * This class provides a convenient way to construct ConfigValue objects by allowing
 * method chaining to set various properties of the ConfigValue.
 * </p>
 */

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
