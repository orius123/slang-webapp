package io.cloudslang.web.entities;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/2/15
 * Time: 2:17 PM
 */
import io.cloudslang.engine.data.AbstractIdentifiable;
import io.cloudslang.score.facade.execution.ExecutionStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name = "EXECUTION_SUMMARY")
public class ExecutionSummaryEntity extends AbstractIdentifiable {

    @Column(name = "EXECUTION_ID", nullable = false)
    private Long executionId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private ExecutionStatus status;

    @Column(name = "RESULT")
    private String result;

    @Column(name = "OUTPUTS")
    private String outputs;

    private ExecutionSummaryEntity(){}

    public ExecutionSummaryEntity(Long executionId, ExecutionStatus status) {
        this.executionId = executionId;
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExecutionSummaryEntity that = (ExecutionSummaryEntity) o;

        return new EqualsBuilder()
                .append(executionId, that.executionId)
                .append(status, that.status)
                .append(result, that.result)
                .append(outputs, that.outputs)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(executionId)
                .append(status)
                .append(result)
                .append(outputs)
                .toHashCode();
    }
}


