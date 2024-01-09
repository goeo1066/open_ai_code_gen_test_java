package com.goeo1066.sample.java_code_creator_sample.records.designer;

import com.goeo1066.sample.java_code_creator_sample.Descriptable;

public record FileSpec(
        String fileName, // including java,
        DependencySpec dependencySpec,
        ClassSpec classSpec,
        @Override
        String description
) implements Descriptable {
}
