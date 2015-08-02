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

import io.cloudslang.web.client.FlowVo;

import java.util.TreeMap;

public interface FlowsService {

    TreeMap<String, FlowVo> getFlows(String classpath);

    FlowVo getFlow(String id, String classpath);

    void changeDefaultCp(String newCp);

}
