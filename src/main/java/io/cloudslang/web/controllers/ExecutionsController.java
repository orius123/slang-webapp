package io.cloudslang.web.controllers;

import com.google.gson.Gson;
import io.cloudslang.score.facade.execution.ExecutionStatus;
import io.cloudslang.web.client.ExecutionSummaryWebVo;
import io.cloudslang.web.client.ExecutionTriggeringVo;
import io.cloudslang.web.client.FlowInputVo;
import io.cloudslang.web.client.FlowVo;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import io.cloudslang.web.services.ExecutionsService;
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
//
//        String slangDir = executionTriggeringVo.getSlangDir();
//
//        Map<String, Serializable> inputs = new HashMap<>();
//        if(executionTriggeringVo.getRunInputs() != null){
//            for(String key : executionTriggeringVo.getRunInputs().keySet()){
//                inputs.put(key, (Serializable) executionTriggeringVo.getRunInputs().get(key));
//            }
//        }
//        Map<String, Serializable> systemProperties = new HashMap<>();
//        if(executionTriggeringVo.getSystemProperties()!= null){
//            for(String key : executionTriggeringVo.getSystemProperties().keySet()){
//                inputs.put(key, (Serializable) executionTriggeringVo.getSystemProperties().get(key));
//            }
//        }
//        return service.triggerExecution(executionTriggeringVo.getSlangFilePath(),
//                slangDir,
//                inputs,
//                systemProperties);
        //todo: temp hard coded solution
        String filePath = executionTriggeringVo.getSlangFilePath();
        if(filePath.equals("/content/io/cloudslang/base/comparisons/less_than_percentage.sl")){
            return 1l;
        }
        return 2l;
    }

    @RequestMapping(value = "/executions/{executionId}", method = RequestMethod.GET)
         @ResponseBody
         public ResponseEntity<ExecutionSummaryWebVo> getExecution(@PathVariable("executionId") Long executionId) {
        ExecutionSummaryWebVo executionVo;
        if(executionId == 1l){
            executionVo = new ExecutionSummaryWebVo(executionId, ExecutionStatus.COMPLETED.name(), "SUCCESS", null);
        }else{
            executionVo = new ExecutionSummaryWebVo(executionId, ExecutionStatus.COMPLETED.name(), "FAILURE", null);
        }
        return new ResponseEntity<>(executionVo, HttpStatus.OK);
//        try {
//            ExecutionSummaryEntity execution = service.getExecution(executionId);
//
//            //noinspection ConstantConditions
//            if (execution != null) {
//                ExecutionSummaryWebVo executionVo = new ExecutionSummaryWebVo(
//                        execution.getExecutionId(),
//                        execution.getStatus().name(),
//                        execution.getResult(),
//                        execution.getOutputs());
//                return new ResponseEntity<>(executionVo, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (IllegalArgumentException ex) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @RequestMapping(value = "/flows", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FlowVo>> getAllFlows() {
        List<FlowVo> flows = new ArrayList<>();
        flows.add(new FlowVo("less_than_percentage", "content.io.cloudslang.base.comparisons.less_than_percentage", "/content/io/cloudslang/base/comparisons/less_than_percentage.sl"));
        flows.add(new FlowVo("print_text", "content.io.cloudslang.base.print.print_text", "/content/io/cloudslang/base/print/print_text.sl"));
        flows.add(new FlowVo("flow3", "id3", "/content/io/cloudslang/base/print/print_text.sl"));
        flows.add(new FlowVo("flow4", "id4", "/content/io/cloudslang/base/print/print_text.sl"));
        flows.add(new FlowVo("flow5", "id5", "/content/io/cloudslang/base/print/print_text.sl"));
        return new ResponseEntity<>(flows, HttpStatus.OK);
    }

    @RequestMapping(value = "/flow/{flowId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FlowInputVo>> getFlowInputs(@PathVariable("flowId") String flowId) {
        List<FlowInputVo> flowInputs1 = new ArrayList<>();
        flowInputs1.add(new FlowInputVo("first_percentage", "", true));
        flowInputs1.add(new FlowInputVo("second_percentage", "", true));

        List<FlowInputVo> flowInputs2 = new ArrayList<>();
        flowInputs2.add(new FlowInputVo("input4", "", true));
        flowInputs2.add(new FlowInputVo("input5", "", false));
        flowInputs2.add(new FlowInputVo("input6", "value3", true));
        flowInputs2.add(new FlowInputVo("input7", "5", true));

        if(flowId.equals("content.io.cloudslang.base.comparisons.less_than_percentage"))
            return  new ResponseEntity<>(flowInputs1, HttpStatus.OK);
        else
            return  new ResponseEntity<>(flowInputs2, HttpStatus.OK);
    }

}
