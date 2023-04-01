package net.projectuniverse.general.config;

public class ConfigParam {

    private static final char prefix = '#';
    private String name, value;

    public ConfigParam(String name, String value) {
        this.name = prefix + name + prefix;
        this.value = value;
    }

    private ConfigParam(ConfigParam param) {
        name = param.getName();
        value = param.getValue();
    }



    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public ConfigParam setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public ConfigParam clone() {
        return new ConfigParam(this);
    }
}
