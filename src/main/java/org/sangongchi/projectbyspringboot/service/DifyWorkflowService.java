package org.sangongchi.projectbyspringboot.service;

import org.sangongchi.projectbyspringboot.model.DifyWorkflowReBody;
import reactor.core.publisher.Flux;

/**
 * @author yangpei
 * @date 2026/3/18
 */
public interface DifyWorkflowService {
	Flux<String> runWorkflow(DifyWorkflowReBody bodyData, String isStreaming);
}
