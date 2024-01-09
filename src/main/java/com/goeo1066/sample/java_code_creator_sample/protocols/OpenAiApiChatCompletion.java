package com.goeo1066.sample.java_code_creator_sample.protocols;

import com.goeo1066.sample.java_code_creator_sample.config.OpenAIConfiguration;
import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

public class OpenAiApiChatCompletion implements OpenAiApiChatCompletable {
    private final ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // set to unlimited memory size
            .build();
    private final WebClient webClientBase = WebClient.builder().baseUrl("https://api.openai.com").exchangeStrategies(exchangeStrategies).build();

    private final OpenAIConfiguration openAIConfiguration;

    public OpenAiApiChatCompletion(OpenAIConfiguration openAIConfiguration) {
        this.openAIConfiguration = openAIConfiguration;
    }

    @Override
    public Mono<OpenAICompletionResponse> request(String requestBody) {
        final String endpoint = "/v1/chat/completions";
        List<String> apiKey = Collections.singletonList(String.format("Bearer %s", openAIConfiguration.getOpenaiApiKey()));

//        ResponseEntity<OpenAICompletionResponse> response =
        return webClientBase.post().uri(uriBuilder -> uriBuilder.path(endpoint).build()).headers(httpHeaders -> {
            httpHeaders.put("Authorization", apiKey);
            httpHeaders.put("Content-Type", Collections.singletonList("application/json"));
        }).bodyValue(requestBody).exchangeToMono(clientResponse -> clientResponse.toEntity(OpenAICompletionResponse.class)).flatMap(response -> {
            if (response != null && response.getStatusCode().value() == 200) {
                OpenAICompletionResponse resBody = response.getBody();
                if (resBody != null && resBody.choices() != null) {
                    return Mono.just(resBody);
                }
            }
            return Mono.empty();
        });
    }

    @Override
    public Mono<OpenAICompletionResponse.Choice> requestForSingleChoice(String requestBody) {
        return request(requestBody).flatMap(resBody -> {
            if (resBody != null && resBody.choices() != null && !resBody.choices().isEmpty()) {
                return Mono.just(resBody.choices().get(0));
            }
            return Mono.empty();
        });
    }
}
