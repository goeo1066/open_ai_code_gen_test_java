package com.goeo1066.sample.java_code_creator_sample.protocols;

import com.goeo1066.sample.java_code_creator_sample.records.designer.ClassDesignerResponse;
import reactor.core.publisher.Mono;

public interface ClassDesigner {
    Mono<ClassDesignerResponse> requestClassDesign(String purpose);
}
