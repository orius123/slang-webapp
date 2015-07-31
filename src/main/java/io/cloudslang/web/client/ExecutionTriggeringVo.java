package io.cloudslang.web.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:26 AM
 */
public class ExecutionTriggeringVo {

    private String slangFilePath;
    private String slangDir;
    private Map<String, Object> runInputs;
    private Map<String, Object> systemProperties;

    private ExecutionTriggeringVo(){}

    public ExecutionTriggeringVo(String slangFilePath, String slangDir,
                                 Map<String, Object> runInputs,
                                 Map<String, Object> systemProperties) {
        this.slangFilePath = slangFilePath;
        this.slangDir = slangDir;
        this.runInputs = runInputs;
        this.systemProperties = systemProperties;
    }

    public String getSlangFilePath() {
        return slangFilePath;
    }

    public String getSlangDir() {
        return slangDir;
    }

    public Map<String, Object> getRunInputs() {
        return runInputs;
    }

    public Map<String, Object> getSystemProperties() {
        return systemProperties;
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
