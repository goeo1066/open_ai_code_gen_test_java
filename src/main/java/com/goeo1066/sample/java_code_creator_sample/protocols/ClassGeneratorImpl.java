package com.goeo1066.sample.java_code_creator_sample.protocols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeo1066.sample.java_code_creator_sample.records.designer.ClassDesignerResponse;
import com.goeo1066.sample.java_code_creator_sample.records.designer.FileSpec;
import com.goeo1066.sample.java_code_creator_sample.records.generator.ClassGeneratorResponse;
import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionMessage;
import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionRequest;
import com.goeo1066.sample.java_code_creator_sample.records.openai.OpenAICompletionResponse;
import reactor.core.publisher.Mono;

public class ClassGeneratorImpl implements ClassGenerator {
    private final String promptSpecString;
    private final ObjectMapper objectMapper;
    private final OpenAiApiChatCompletable openAiApiChatCompletable;

    public ClassGeneratorImpl(ClassDesignerResponse promptSpec, ObjectMapper objectMapper, OpenAiApiChatCompletable openAiApiChatCompletable) throws JsonProcessingException {
        this.objectMapper = objectMapper;
        this.openAiApiChatCompletable = openAiApiChatCompletable;
        this.promptSpecString = objectMapper.writeValueAsString(promptSpec);
    }

    @Override
    public Mono<ClassGeneratorResponse> generateClass(FileSpec fileSpec) {
        try {
            String reqBody = makeBodyValue(fileSpec);
            return openAiApiChatCompletable.requestForSingleChoice(reqBody)
                    .flatMap(this::createClassGeneratorResponse)
                    .map(ClassGeneratorResponse::new);
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

    private Mono<String> createClassGeneratorResponse(OpenAICompletionResponse.Choice choice) {
        return Mono.just(choice.message().content());
    }

    private String makeBodyValue(FileSpec fileSpec) throws JsonProcessingException {
        OpenAICompletionRequest request = createCompletionRequest(fileSpec);
        return objectMapper.writeValueAsString(request);
    }

    private OpenAICompletionRequest createCompletionRequest(FileSpec fileSpec) throws JsonProcessingException {
        OpenAICompletionMessage systemPrompt = createSystemPrompt();
        OpenAICompletionMessage userPrompt = createUserPrompt(fileSpec);
        return OpenAICompletionRequest.ofDefault(systemPrompt, userPrompt);
    }

    private OpenAICompletionMessage createUserPrompt(FileSpec fileSpec) throws JsonProcessingException {
        String prompt = objectMapper.writeValueAsString(fileSpec);
        return new OpenAICompletionMessage("user", prompt);
    }

    private OpenAICompletionMessage createSystemPrompt() {
        String prompt = ("You are an excellent Java Class Writer. " +
                "Write one whole .java file in pure text not in markdown. " +
                "user will prompt in format: %s").formatted(promptSpecString);
        return new OpenAICompletionMessage("system", prompt);
    }
}
