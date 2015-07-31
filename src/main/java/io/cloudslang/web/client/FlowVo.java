package io.cloudslang.web.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by stoneo on 5/12/2015.
 **/
public class FlowVo {

    private final String name;

    private final String id;

    private final String path;

    public FlowVo(String name, String id, String path) {
        this.name = name;
        this.id = id;
        this.path = path;
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

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
