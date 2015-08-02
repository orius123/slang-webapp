package io.cloudslang.web.controllers;

import io.cloudslang.web.client.ExecutionSummaryWebVo;
import io.cloudslang.web.client.ExecutionTriggeringVo;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import io.cloudslang.web.services.ExecutionsService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:12 AM
 */

@RestController
public final class ExecutionsController {

    @Autowired
    private ExecutionsService service;

    @ApiOperation(value = "Trigger a new execution", notes = "Something important")
    @RequestMapping(value = "/executions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Long triggerExecution(@RequestBody ExecutionTriggeringVo executionTriggeringVo) {

        String slangDir = executionTriggeringVo.getSlangDir();

        Map<String, Serializable> inputs =
                transformToSerializable(executionTriggeringVo.getRunInputs());

        Map<String, Serializable> systemProperties =
                transformToSerializable(executionTriggeringVo.getSystemProperties());

        return service.triggerExecution(
                executionTriggeringVo.getSlangFilePath(),
                slangDir,
                inputs,
                systemProperties
        );

    }

    private Map<String, Serializable> transformToSerializable(Map<String, Object> objectsMap) {
        if (MapUtils.isEmpty(objectsMap)) {
            return null;
        }

        return objectsMap
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, e -> (Serializable) e.getValue()));
    }

    @ApiOperation(value = "Get execution", notes = "Something important")
    @RequestMapping(value = "/executions/{executionId}", method = RequestMethod.GET)
    public ExecutionSummaryWebVo getExecution(@PathVariable("executionId") Long executionId) {
        ExecutionSummaryEntity execution = service.getExecution(executionId);

        if (execution == null) {
            throw new GlobalControllerExceptionHandler.NotFoundException();
        }

        return new ExecutionSummaryWebVo(execution);
    }

}
