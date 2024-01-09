package com.goeo1066.sample.java_code_creator_sample.records.openai;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OpenAICompletionRequest(
        Integer seed,
        String model,
        ResponseFormat responseFormat,
        List<OpenAICompletionMessage> messages
) {
    private static final ResponseFormat JSON_RESPONSE_FORMAT = new ResponseFormat("json_object");

    public static OpenAICompletionRequest ofDefault(OpenAICompletionMessage... messages) {

        return new OpenAICompletionRequest(
                54321,
                "gpt-3.5-turbo",
                null,
                List.of(messages)
        );
    }

    public static OpenAICompletionRequest ofDefault(int seed, List<OpenAICompletionMessage> messages) {
        return new OpenAICompletionRequest(
                seed,
                "gpt-3.5-turbo",
                null,
                messages
        );
    }

    public static ResponseFormat newResponseFormat(String type) {
        return new ResponseFormat(type);
    }


    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ResponseFormat(
            String type
    ) {
    }

}
