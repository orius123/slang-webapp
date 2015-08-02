package io.cloudslang.web.controllers;

import io.cloudslang.web.client.FlowVo;
import io.cloudslang.web.services.FlowsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:12 AM
 */

@RestController
public final class FlowsController {

    @Autowired
    private FlowsService flowsService;

    @ApiOperation(value = "Get flows", notes = "Something important")
    @RequestMapping(value = "/flows", method = RequestMethod.GET)
    public Map<String, FlowVo> getAllFlows(
            @RequestParam(value = "classpath", required = false) String classpath) {
        return flowsService.getFlows(classpath);
    }

    @ApiOperation(value = "Get flows", notes = "Something important")
    @RequestMapping(value = "/flows/cp", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeClasspath(
            @RequestParam(value = "classpath") String classpath) {
        flowsService.changeDefaultCp(classpath);
    }

    @ApiOperation(value = "Get flow",
            notes = "Something important")
    @RequestMapping(value = "/flows/{flowId:.+}", method = RequestMethod.GET)
    public FlowVo getFlow(@PathVariable("flowId") String flowId) {
        return flowsService.getFlow(flowId, null);
    }

}
