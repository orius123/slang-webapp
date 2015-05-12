package org.openscore.web.client;

/**
 * Created by stoneo on 5/12/2015.
 **/
public class FlowVO {

    private String name;

    private String id;

    public FlowVO(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
