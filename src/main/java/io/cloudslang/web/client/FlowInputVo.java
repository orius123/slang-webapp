package io.cloudslang.web.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by stoneo on 5/12/2015.
 */
public class FlowInputVo {

    private final String name;

    private final String defaultValue;

    private final Boolean required;

    public FlowInputVo(String name, String defaultValue, Boolean required) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Boolean isRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
