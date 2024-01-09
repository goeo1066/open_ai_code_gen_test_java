package com.goeo1066.sample.java_code_creator_sample;

import com.goeo1066.sample.java_code_creator_sample.protocols.ClassDesigner;
import com.goeo1066.sample.java_code_creator_sample.protocols.ClassGenerator;
import com.goeo1066.sample.java_code_creator_sample.records.generator.ClassGeneratorResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CodeService {
    private final ClassGenerator classGenerator;
    private final ClassDesigner classDesigner;

    public CodeService(ClassGenerator classGenerator, ClassDesigner classDesigner) {
        this.classGenerator = classGenerator;
        this.classDesigner = classDesigner;
    }

    public Mono<String> createClass(String purpose) {
        return classDesigner.requestClassDesign(purpose)
                .flatMap(classDesignerResponse -> classGenerator.generateClass(classDesignerResponse.fileSpec()))
                .map(ClassGeneratorResponse::response);
    }
}
