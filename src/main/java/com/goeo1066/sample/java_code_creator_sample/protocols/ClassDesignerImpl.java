package com.goeo1066.sample.java_code_creator_sample.protocols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeo1066.sample.java_code_creator_sample.records.designer.ClassDesignerResponse;
import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionMessage;
import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionRequest;
import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionResponse;
import reactor.core.publisher.Mono;

public class ClassDesignerImpl implements ClassDesigner {
    private final String promptSpecString;
    private final OpenAiApiChatCompletable openAiApiChatCompletable;
    private final ObjectMapper objectMapper;

    public ClassDesignerImpl(ClassDesignerResponse promptSpec,
                             ObjectMapper objectMapper,
                             OpenAiApiChatCompletable openAiApiChatCompletable
    ) throws JsonProcessingException {
        this.objectMapper = objectMapper;
        this.openAiApiChatCompletable = openAiApiChatCompletable;
        this.promptSpecString = objectMapper.writeValueAsString(promptSpec);
    }

    @Override
    public Mono<ClassDesignerResponse> requestClassDesign(String purpose) {
        try {
            String reqBody = makeBodyValue(purpose);
            return openAiApiChatCompletable.requestForSingleChoice(reqBody).flatMap(this::createClassDesignerResponse);
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

    private Mono<ClassDesignerResponse> createClassDesignerResponse(OpenAICompletionResponse.Choice choice) {
        try {
            return Mono.just(objectMapper.readValue(choice.message().content(), ClassDesignerResponse.class));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

    private String makeBodyValue(String purpose) throws JsonProcessingException {
        OpenAICompletionRequest request = createCompletionRequest(purpose);
        return objectMapper.writeValueAsString(request);
    }

    private OpenAICompletionRequest createCompletionRequest(String purpose) {
        OpenAICompletionMessage systemPrompt = createSystemPrompt(purpose);
        return OpenAICompletionRequest.ofDefault(systemPrompt);
    }

    private OpenAICompletionMessage createSystemPrompt(String purpose) {
        String prompt = ("You are a strict and excellent Java 11 class designer. " +
                "the purpose of the class is \"%s\". " +
                "Design a class in format: %s")
                .formatted(purpose, promptSpecString);
        return new OpenAICompletionMessage("system", prompt);
    }
}
