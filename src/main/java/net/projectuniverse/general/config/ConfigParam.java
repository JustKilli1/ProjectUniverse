package net.projectuniverse.general.config;

/**
 * A class representing a configuration parameter.
 *
 * <p>
 * ConfigParam provides a representation of a configuration parameter that consists of a name and a value.
 * The name is surrounded by a prefix, which is a static final character '#'. This prefix is added to the name
 * when creating a new ConfigParam instance. The value of the parameter can be changed using the setValue method.
 * </p>
 *
 * <p>
 * ConfigParam objects are immutable, meaning that they cannot be modified after they are created. However,
 * the value of a ConfigParam object can be changed by creating a new instance with the desired value.
 * This can be done using the setValue method, which returns a new ConfigParam object with the updated value.
 * </p>
 *
 * <p>
 * ConfigParam objects can be cloned using the clone method, which creates a new ConfigParam object with the
 * same name and value as the original object. The toString method returns the name of the parameter.
 * </p>
 */

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

    @Override
    public String toString() {
        return name;
    }
}
