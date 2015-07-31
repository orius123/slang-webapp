package io.cloudslang.web.client;

import io.cloudslang.web.entities.ExecutionSummaryEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/2/15
 * Time: 3:16 PM
 */
@SuppressWarnings("UnusedDeclaration")
public class ExecutionSummaryWebVo {

    private final Long executionId;
    private final String status;
    private final String result;
    private final String outputs;

    public ExecutionSummaryWebVo(ExecutionSummaryEntity executionSummaryEntity){
        this.executionId = executionSummaryEntity.getExecutionId();
        this.status = executionSummaryEntity.getStatus().name();
        this.result = executionSummaryEntity.getResult();
        this.outputs = executionSummaryEntity.getOutputs();
    }

    public ExecutionSummaryWebVo(Long executionId, String status, String result, String outputs) {
        this.executionId = executionId;
        this.status = status;
        this.result = result;
        this.outputs = outputs;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public String getOutputs() {
        return outputs;
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
