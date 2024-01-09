package com.goeo1066.sample.java_code_creator_sample.records.designer;

import com.goeo1066.sample.java_code_creator_sample.Descriptable;

public record MethodSignature(
        String methodSignature,
        @Override
        String description
) implements Descriptable {
}
