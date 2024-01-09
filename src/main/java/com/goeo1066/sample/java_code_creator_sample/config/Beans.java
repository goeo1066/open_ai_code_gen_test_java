package com.goeo1066.sample.java_code_creator_sample.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeo1066.sample.java_code_creator_sample.protocols.*;
import com.goeo1066.sample.java_code_creator_sample.records.designer.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Beans {
    @Bean
    public ClassDesigner classDesigner(
            @Qualifier("promptSpec") ClassDesignerResponse promptSpec,
            ObjectMapper objectMapper,
            OpenAiApiChatCompletable openAiApiChatCompletable
    ) throws JsonProcessingException {
        return new ClassDesignerImpl(promptSpec, objectMapper, openAiApiChatCompletable);
    }

    @Bean
    public ClassGenerator classGenerator(
            @Qualifier("promptSpec") ClassDesignerResponse promptSpec,
            ObjectMapper objectMapper,
            OpenAiApiChatCompletable openAiApiChatCompletable
    ) throws JsonProcessingException {
        return new ClassGeneratorImpl(promptSpec, objectMapper, openAiApiChatCompletable);
    }

    @Bean
    public OpenAiApiChatCompletable openAiApiChatCompletable(
            OpenAIConfiguration openAIConfiguration
    ) {
        return new OpenAiApiChatCompletion(openAIConfiguration);
    }

    @Bean("promptSpec")
    public ClassDesignerResponse promptSpec() {
        return new ClassDesignerResponse(
                new FileSpec(
                        "<fileName>",
                        new DependencySpec(List.of("<import statement except leading import keyword>")),
                        new ClassSpec(
                                "<className>",
                                List.of(new MethodSignature("<methodSignature>", "<description_or_guide_for_the_method>")),
                                "<description_or_guide_for_class>"
                        ),
                        "<description_or_guide_for_file>"
                )
        );
    }
}
