package com.goeo1066.sample.java_code_creator_sample.protocols;

import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionResponse;
import reactor.core.publisher.Mono;

public interface OpenAiApiChatCompletable {
    Mono<OpenAICompletionResponse> request(String requestBody);

    Mono<OpenAICompletionResponse.Choice> requestForSingleChoice(String requestBody);
}
