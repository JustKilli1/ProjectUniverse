package net.projectuniverse.general.config;

public class ConfigParam {

    private static final char prefix = '#';
    private String name, value;

    public ConfigParam(String name, String value) {
        this.name = prefix + name + prefix;
        this.value = value;
    }



    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
