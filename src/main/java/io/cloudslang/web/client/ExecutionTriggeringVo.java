package io.cloudslang.web.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:26 AM
 */
public class ExecutionTriggeringVo {

    private String flowId;
    private String classpath;
    private Map<String, Object> runInputs;
    private Map<String, Object> systemProperties;

    private ExecutionTriggeringVo(){}

    public ExecutionTriggeringVo(String slangFilePath, String slangDir,
                                 Map<String, Object> runInputs,
                                 Map<String, Object> systemProperties) {
        this.flowId = slangFilePath;
        this.classpath = slangDir;
        this.runInputs = runInputs;
        this.systemProperties = systemProperties;
    }

    public String getFlowId() {
        return flowId;
    }

    public String getClasspath() {
        return classpath;
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
