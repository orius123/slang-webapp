package io.cloudslang.web.services;

import io.cloudslang.lang.api.Slang;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.score.facade.execution.ExecutionStatus;
import io.cloudslang.web.client.FlowVo;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import io.cloudslang.web.repositories.ExecutionSummaryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 10:44 AM
 */

@Service
public final class ExecutionsServiceImpl implements ExecutionsService {

    @Autowired
    private Slang slang;

    @Autowired
    private FlowsService flowsService;

    @Autowired
    private ExecutionSummaryRepository repository;

    @Override
    @Transactional
    public Long triggerExecution(String id,
                                     String classpath,
                                     Map<String, ? extends Serializable> runInputs,
                                     Map<String, ? extends Serializable> systemProperties) {

        FlowVo flowVo = flowsService.getFlow(id, classpath);
        File slangFile = new File(flowVo.getPath());

        SlangSource flowSource = SlangSource.fromFile(slangFile);
        Long executionId = slang.compileAndRun(flowSource, getDependencies(classpath), runInputs, systemProperties);

        ExecutionSummaryEntity execution = new ExecutionSummaryEntity(
                executionId, ExecutionStatus.RUNNING
        );

        repository.save(execution);

        return execution.getExecutionId();
    }

    @Override
    @Transactional(readOnly = true)
    public ExecutionSummaryEntity getExecution(Long executionId) {
        return repository.findByExecutionId(executionId);
    }

    @Override
    @Transactional
    public void updateExecution(Long executionId, ExecutionStatus status, String result, String outputs) {
        ExecutionSummaryEntity execution = repository.findByExecutionId(executionId);
        execution.setStatus(status);
        execution.setResult(result);
        execution.setOutputs(outputs);
    }

    private Set<SlangSource> getDependencies(String slangDir) {

        Set<SlangSource> slangDependencies = new HashSet<>();

        if (StringUtils.isEmpty(slangDir)) {
            return null;
        }

        Set<File> files = new HashSet<>();
        try {
            files = getAllFilesRecursively(
                    new File(getClass().getResource(slangDir).toURI()), new HashSet<>());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        slangDependencies.addAll(files.stream()
                                      .map(SlangSource::fromFile)
                                      .collect(Collectors.toList()));

        return slangDependencies;
    }

    private static Set<File> getAllFilesRecursively(File directory, Set<File> result) {
        File[] filesInDir = directory.listFiles();
        //If it is a file (filesInDir == null in case the directory is a file) - add it to list and return
        if (filesInDir == null) {
            result.add(directory);
            return result;
        }
        //If it is a directory - do recursive call for each child
        else {
            for (File file : filesInDir) {
                result.addAll(getAllFilesRecursively(file, result));
            }
            return result;
        }
    }
}
