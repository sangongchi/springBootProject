package org.sangongchi.projectbyspringboot.controller.springAi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 向量嵌入控制器
 * 使用阿里云通义千问 text-embedding-v3 模型进行文本向量化
 *
 * @author yangpei
 * @date 2026/4/16
 */
@Tag(name = "向量嵌入", description = "文本向量化处理接口")
@RestController
@RequestMapping("/embedding")
public class EmbeddingController {

	private final EmbeddingModel qwenEmbeddingModel;

	// Kimi 暂时没有提供独立的 Embedding API，如果需要可以后续扩展
	// private final EmbeddingModel kimiEmbeddingModel;

	public EmbeddingController(
			@Qualifier("qwenEmbeddingModel") EmbeddingModel qwenEmbeddingModel) {
		this.qwenEmbeddingModel = qwenEmbeddingModel;
	}

	/**
	 * 单文本向量化
	 */
	@Operation(summary = "单文本向量化", description = "将单条文本转换为向量表示")
	@GetMapping("/qwen")
	public EmbeddingResult embed(@RequestParam String text) {
		EmbeddingResponse response = qwenEmbeddingModel.call(
				new EmbeddingRequest(List.of(text),
						OpenAiEmbeddingOptions.builder()
								.model("text-embedding-v3")
								.build()));

		float[] vector = response.getResult().getOutput();
		List<Float> vectorList = new java.util.ArrayList<>();
		for (float v : vector) {
			vectorList.add(v);
		}
		return new EmbeddingResult(text, vectorList, vector.length);
	}

	/**
	 * 单文本向量化（POST JSON）
	 */
	@Operation(summary = "单文本向量化(POST)", description = "POST 方式将单条文本转换为向量表示")
	@PostMapping("/qwen")
	public EmbeddingResult embedPost(@RequestBody Map<String, String> request) {
		String text = request.get("text");
		return embed(text);
	}

	/**
	 * 批量文本向量化
	 */
	@Operation(summary = "批量文本向量化", description = "将多条文本批量转换为向量表示")
	@GetMapping("/qwen/batch")
	public BatchEmbeddingResult batchEmbed(@RequestParam List<String> texts) {
		EmbeddingResponse response = qwenEmbeddingModel.call(
				new EmbeddingRequest(texts,
						OpenAiEmbeddingOptions.builder()
								.model("text-embedding-v3")
								.build()));

		List<EmbeddingResult> results = response.getResults().stream()
				.map(r -> {
					float[] vec = r.getOutput();
					List<Float> vecList = new java.util.ArrayList<>();
					for (float v : vec) {
						vecList.add(v);
					}
					return new EmbeddingResult(texts.get(r.getIndex()), vecList, vec.length);
				})
				.toList();

		return new BatchEmbeddingResult(results, results.size());
	}

	/**
	 * 批量文本向量化（POST JSON）
	 */
	@Operation(summary = "批量文本向量化(POST)", description = "POST 方式将多条文本批量转换为向量表示")
	@PostMapping("/qwen/batch")
	public BatchEmbeddingResult batchEmbedPost(@RequestBody Map<String, List<String>> request) {
		List<String> texts = request.get("texts");
		return batchEmbed(texts);
	}

	/**
	 * 向量结果（单条）
	 */
	public record EmbeddingResult(
			String text,
			List<Float> embedding,
			int dimensions
	) {}

	/**
	 * 批量向量结果
	 */
	public record BatchEmbeddingResult(
			List<EmbeddingResult> results,
			int total
	) {}
}
