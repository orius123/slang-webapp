package org.openscore.web.controllers;

import com.google.gson.Gson;
import org.openscore.web.client.ExecutionSummaryWebVo;
import org.openscore.web.client.ExecutionTriggeringVo;
import org.openscore.web.client.FlowInputVo;
import org.openscore.web.client.FlowVo;
import org.openscore.web.entities.ExecutionSummaryEntity;
import org.openscore.web.services.ExecutionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:12 AM
 */

@RestController
@EnableAutoConfiguration
public class ExecutionsController {

    @Autowired
    ExecutionsService service;

    private static Gson gson = new Gson();

    @RequestMapping(value = "/executions", method = RequestMethod.POST)
    public Long triggerExecution(@RequestBody String executionTriggeringVoStr) {

        ExecutionTriggeringVo executionTriggeringVo = gson.fromJson(executionTriggeringVoStr, ExecutionTriggeringVo.class);

        String slangDir = executionTriggeringVo.getSlangDir();

        Map<String, Serializable> inputs = new HashMap<>();
        if(executionTriggeringVo.getRunInputs() != null){
            for(String key : executionTriggeringVo.getRunInputs().keySet()){
                inputs.put(key, (Serializable) executionTriggeringVo.getRunInputs().get(key));
            }
        }
        Map<String, Serializable> systemProperties = new HashMap<>();
        if(executionTriggeringVo.getSystemProperties()!= null){
            for(String key : executionTriggeringVo.getSystemProperties().keySet()){
                inputs.put(key, (Serializable) executionTriggeringVo.getSystemProperties().get(key));
            }
        }
        return service.triggerExecution(executionTriggeringVo.getSlangFilePath(),
                slangDir,
                inputs,
                systemProperties);
    }

    @RequestMapping(value = "/executions/{executionId}", method = RequestMethod.GET)
         @ResponseBody
         public ResponseEntity<ExecutionSummaryWebVo> getExecution(@PathVariable("executionId") Long executionId) {
        try {
            ExecutionSummaryEntity execution = service.getExecution(executionId);

            ExecutionSummaryWebVo executionVo = new ExecutionSummaryWebVo(
                    execution.getExecutionId(),
                    execution.getStatus().name(),
                    execution.getResult(),
                    execution.getOutputs());

            //noinspection ConstantConditions
            if (execution != null) {
                return new ResponseEntity<>(executionVo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/flows", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FlowVo>> getAllFlows() {
        List<FlowVo> flows = new ArrayList<>();
        flows.add(new FlowVo("flow1", "id1"));
        flows.add(new FlowVo("flow2", "id2"));
        flows.add(new FlowVo("flow3", "id3"));
        flows.add(new FlowVo("flow4", "id4"));
        flows.add(new FlowVo("flow5", "id5"));
        return new ResponseEntity<>(flows, HttpStatus.OK);
    }

    @RequestMapping(value = "/flow/{flowId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FlowInputVo>> getFlowInputs(@PathVariable("flowId") String flowId) {
        List<FlowInputVo> flowInputs1 = new ArrayList<>();
        flowInputs1.add(new FlowInputVo("input1", "value1", true));
        flowInputs1.add(new FlowInputVo("input2", "value2", false));
        flowInputs1.add(new FlowInputVo("input3", "", true));

        List<FlowInputVo> flowInputs2 = new ArrayList<>();
        flowInputs2.add(new FlowInputVo("input4", "", true));
        flowInputs2.add(new FlowInputVo("input5", "", false));
        flowInputs2.add(new FlowInputVo("input6", "value3", true));
        flowInputs2.add(new FlowInputVo("input7", "5", true));

        if(flowId.equals("id1"))
            return  new ResponseEntity<>(flowInputs1, HttpStatus.OK);
        else
            return  new ResponseEntity<>(flowInputs2, HttpStatus.OK);
    }
}
