package com.goeo1066.sample.java_code_creator_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaCodeCreatorSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCodeCreatorSampleApplication.class, args);
    }

//    @Bean
//    ApplicationRunner applicationRunner(
//            ClassDesigner classDesigner,
//            ClassGenerator classGenerator
//    ) {
//        return args -> {
//            String purpose = "Create a calculator domain entity. controller will call method of the class. get only single integer and get current result every time call one of the operator methods";
//            ClassDesignerResponse classDesignerResponse = classDesigner.requestClassDesign(purpose);
//            System.out.println(classDesignerResponse);
//            ClassGeneratorResponse classGeneratorResponse = classGenerator.generateClass(classDesignerResponse.fileSpec());
//            System.out.println(classGeneratorResponse);
//        };
//    }
}
