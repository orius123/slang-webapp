package io.cloudslang.web.controllers;

import com.google.gson.Gson;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:12 AM
 */

@RestController
public class ExecutionsController {

    @Autowired
    private ExecutionsService service;

    private static Gson gson = new Gson();

    @ApiOperation(value = "Trigger a new execution",
            notes = "Something important")
    @RequestMapping(value = "/executions", method = RequestMethod.POST)
    public Long triggerExecution(@RequestBody ExecutionTriggeringVo executionTriggeringVo) {

        String slangDir = executionTriggeringVo.getSlangDir();

        Map<String, Serializable> inputs = new HashMap<>();
        if (MapUtils.isNotEmpty(executionTriggeringVo.getRunInputs())) {
            for (String key : executionTriggeringVo.getRunInputs()
                                                   .keySet()) {
                inputs.put(key, (Serializable) executionTriggeringVo.getRunInputs()
                                                                    .get(key));
            }
        }

        Map<String, Serializable> systemProperties = new HashMap<>();
        if (MapUtils.isNotEmpty(executionTriggeringVo.getSystemProperties())) {
            for (String key : executionTriggeringVo.getSystemProperties()
                                                   .keySet()) {
                inputs.put(key, (Serializable) executionTriggeringVo.getSystemProperties()
                                                                    .get(key));
            }
        }

        return service.triggerExecution(executionTriggeringVo.getSlangFilePath(),
                slangDir,
                inputs,
                systemProperties);

    }

    @ApiOperation(value = "Get execution",
            notes = "Something important")
    @RequestMapping(value = "/executions/{executionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ExecutionSummaryWebVo> getExecution(@PathVariable("executionId") Long executionId) {
        try {

            ExecutionSummaryEntity execution = service.getExecution(executionId);

            if (execution == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            ExecutionSummaryWebVo executionVo = new ExecutionSummaryWebVo(execution);
            return new ResponseEntity<>(executionVo, HttpStatus.OK);

        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
