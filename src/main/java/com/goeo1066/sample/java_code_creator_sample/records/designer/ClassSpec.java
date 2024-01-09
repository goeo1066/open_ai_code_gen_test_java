package com.goeo1066.sample.java_code_creator_sample.records.designer;

import com.goeo1066.sample.java_code_creator_sample.Descriptable;

import java.util.List;

public record ClassSpec(
        String className,
        List<MethodSignature> methodSignatures,
        @Override
        String description
) implements Descriptable {
}
