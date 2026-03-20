package org.sangongchi.projectbyspringboot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.sangongchi.projectbyspringboot.model.DifyWorkflowReBody;
import org.sangongchi.projectbyspringboot.service.DifyChatService;
import org.sangongchi.projectbyspringboot.service.DifyWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangpei
 * @date 2026/3/17
 */
@RestController
@RequestMapping("/dify")
public class DifyController {

	@Autowired
	private DifyChatService chatService;
	@Autowired
	private DifyWorkflowService workflowService;

	@PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter chat(@RequestParam String prompt,
	                       @RequestParam(required = false, defaultValue = "") String conversationId,
	                       @RequestParam(required = false, defaultValue = "1") String isStreaming)

	{
		return chatService.chat(prompt, conversationId, isStreaming);

	}

	@PostMapping(value = "/workflow", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> runWorkflow(
			@RequestBody DifyWorkflowReBody bodyData,
			@RequestParam(required = false, defaultValue = "1") String isStreaming){
		return workflowService.runWorkflow(bodyData, isStreaming);
	}
}
