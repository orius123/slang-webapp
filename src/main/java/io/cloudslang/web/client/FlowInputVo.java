package io.cloudslang.web.client;

/**
 * Created by stoneo on 5/12/2015.
 */
public class FlowInputVo {

    private String name;

    private String defaultValue;

    private Boolean isRequired;

    public FlowInputVo(String name, String defaultValue, Boolean isRequired) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.isRequired = isRequired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean isRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
}
