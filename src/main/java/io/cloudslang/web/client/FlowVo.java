package io.cloudslang.web.client;

import io.cloudslang.lang.entities.bindings.Input;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Created by stoneo on 5/12/2015.
 **/
public class FlowVo {

    private final String name;

    private final String id;

    private final String path;

    private final List<Input> inputs;

    public FlowVo(String name, String id, String path, List<Input> inputs) {
        this.name = name;
        this.id = id;
        this.path = path;
        this.inputs = inputs;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public List<Input> getInputs() {
        return inputs;
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
