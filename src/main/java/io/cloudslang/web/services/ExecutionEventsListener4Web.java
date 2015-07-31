package io.cloudslang.web.services;

import io.cloudslang.lang.api.Slang;
import io.cloudslang.lang.entities.ScoreLangConstants;
import io.cloudslang.lang.runtime.events.LanguageEventData;
import io.cloudslang.score.events.ScoreEvent;
import io.cloudslang.score.events.ScoreEventListener;
import io.cloudslang.score.facade.execution.ExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.cloudslang.lang.entities.ScoreLangConstants.SLANG_EXECUTION_EXCEPTION;
import static io.cloudslang.lang.entities.ScoreLangConstants.EVENT_EXECUTION_FINISHED;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/4/15
 * Time: 1:34 PM
 */

@Component
public class ExecutionEventsListener4Web implements ScoreEventListener {

    @Autowired
    Slang slang;

    @Autowired
    ExecutionsService service;

    @PostConstruct
    public void init() {
        Set<String> eventTypes = new HashSet<>();

        eventTypes.add(SLANG_EXECUTION_EXCEPTION); //Runtime exception – flow finished in the middle
        eventTypes.add(EVENT_EXECUTION_FINISHED); //Flow completed

        slang.subscribeOnEvents(this, eventTypes);
    }

    @Override
    public void onEvent(ScoreEvent event) throws InterruptedException {

        @SuppressWarnings("unchecked")
        LanguageEventData data = (LanguageEventData) event.getData();

        Long executionId = data.getExecutionId();

        if (event.getEventType().equals(SLANG_EXECUTION_EXCEPTION)) {

            String exception = data.getException();
            service.updateExecution(executionId, ExecutionStatus.SYSTEM_FAILURE, exception, null);

        }
        else if (event.getEventType().equals(EVENT_EXECUTION_FINISHED)) {

            String result = data.getResult();
            String outputs = data.getOutputs().toString();

            service.updateExecution(executionId, ExecutionStatus.COMPLETED, result, outputs);
        }
    }
}
