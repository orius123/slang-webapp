package io.cloudslang.web.controllers;

import com.google.gson.Gson;
import io.cloudslang.web.client.ExecutionSummaryWebVo;
import io.cloudslang.web.client.ExecutionTriggeringVo;
import io.cloudslang.web.client.FlowInputVo;
import io.cloudslang.web.client.FlowVo;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import io.cloudslang.web.services.ExecutionsService;
import io.swagger.annotations.ApiOperation;
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
public class FlowsController {

    @ApiOperation(value="Get flows",
            notes="Something important")
    @RequestMapping(value = "/flows", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FlowVo>> getAllFlows() {
        List<FlowVo> flows = new ArrayList<>();
        flows.add(new FlowVo("turn_on_boiler", "content.io.cloudslang.smarthome.turn_on_boiler", "/content/io/cloudslang/smarthome/turn_on_boiler.sl"));
        flows.add(new FlowVo("turn_on_music", "content.io.cloudslang.smarthome.turn_on_music", "/content/io/cloudslang/smarthome/turn_on_music.sl"));
        flows.add(new FlowVo("turn_off_all_lights", "content.io.cloudslang.smarthome.turn_off_all_lights", "/content/io/cloudslang/smarthome/turn_off_all_lights.sl"));
        flows.add(new FlowVo("leave_home", "content.io.cloudslang.smarthome.leave_home", "/content/io/cloudslang/smarthome/leave_home.sl"));
        return new ResponseEntity<>(flows, HttpStatus.OK);
    }

    @ApiOperation(value="Get flow",
            notes="Something important")
    @RequestMapping(value = "/flow/{flowId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FlowInputVo>> getFlowInputs(@PathVariable("flowId") String flowId) {
        List<FlowInputVo> flowInputs1 = new ArrayList<>();
        flowInputs1.add(new FlowInputVo("minutes", "", true));

        List<FlowInputVo> flowInputs2 = new ArrayList<>();

        if(flowId.equals("content.io.cloudslang.smarthome.turn_on_boiler"))
            return  new ResponseEntity<>(flowInputs1, HttpStatus.OK);
        else
            return  new ResponseEntity<>(flowInputs2, HttpStatus.OK);
    }

}
