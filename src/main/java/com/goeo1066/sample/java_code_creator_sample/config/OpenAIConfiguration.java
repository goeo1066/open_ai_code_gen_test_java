package com.goeo1066.sample.java_code_creator_sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.InvalidParameterException;

@Configuration
public class OpenAIConfiguration {
    private final String openaiApiKey;

    public OpenAIConfiguration(
            @Value("${openai.apikey:}") String openaiApiKey
    ) {
        if (openaiApiKey == null || openaiApiKey.trim().isEmpty()) {
            throw new InvalidParameterException();
        }
        this.openaiApiKey = openaiApiKey;
    }

    public String getOpenaiApiKey() {
        return openaiApiKey;
    }
}
