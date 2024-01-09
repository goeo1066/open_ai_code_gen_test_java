package com.goeo1066.sample.java_code_creator_sample.protocols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.goeo1066.sample.java_code_creator_sample.records.designer.FileSpec;
import com.goeo1066.sample.java_code_creator_sample.records.generator.ClassGeneratorResponse;
import reactor.core.publisher.Mono;

public interface ClassGenerator {
    Mono<ClassGeneratorResponse> generateClass(FileSpec fileSpec);
}
