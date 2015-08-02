/*******************************************************************************
 * (c) Copyright 2014 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package io.cloudslang.web.services;

import io.cloudslang.lang.compiler.SlangCompiler;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.lang.compiler.modeller.model.Executable;
import io.cloudslang.web.client.FlowVo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
public final class FlowsServiceImpl implements FlowsService {

    public static final String[] CSLANG_FILE_EXTENSIONS = {"sl", "sl.yaml", "sl.yml"};
    public static final String CSLANG_DEFAULT_CP = "cslang.default.cp";

    @Autowired
    private SlangCompiler slangCompiler;

    @Override
    @Cacheable
    public TreeMap<String, FlowVo> getFlows(String classpath) {
        String cp = getDefaultCp(classpath);

        Collection<File> cpFiles = FileUtils.listFiles(new File(cp), CSLANG_FILE_EXTENSIONS, true);
        Map<String, FlowVo> flows = cpFiles
                .stream()
                .map(this::fileToFlowVo)
                .collect(toMap(FlowVo::getId, Function.identity()));

        return new TreeMap<>(flows);

    }

    private String getDefaultCp(String classpath) {
        String cp = classpath;
        if (cp == null) {
            String localContent = System.getProperty("user.dir") + File.separator + "content";
            cp = System.getProperty(CSLANG_DEFAULT_CP, localContent);
        }
        return cp;
    }

    private FlowVo fileToFlowVo(File file) {
        SlangSource slangSource = SlangSource.fromFile(file);
        Executable executable = slangCompiler.preCompile(slangSource);
        return new FlowVo(
                executable.getName(),
                executable.getId(),
                file.getAbsolutePath(),
                executable.getInputs()
        );
    }

    @Override
    public FlowVo getFlow(String id, String classpath) {
        TreeMap<String, FlowVo> flows = getFlows(classpath);
        return flows.get(id);
    }

    @Override
    public void changeDefaultCp(String newCp) {
        System.setProperty(CSLANG_DEFAULT_CP, newCp);
    }
}
