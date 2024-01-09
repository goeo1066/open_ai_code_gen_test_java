package com.goeo1066.sample.java_code_creator_sample;

import com.goeo1066.sample.java_code_creator_sample.protocols.ClassDesigner;
import com.goeo1066.sample.java_code_creator_sample.protocols.ClassGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaCodeCreatorSampleApplicationTests {

    @Test
    void calculator() {
        Calculator calculator = new Calculator();
        calculator.add(10);
        assert calculator.getResult() == 10;

        calculator.add(20);
        assert calculator.getResult() == 30;

        calculator.subtract(10);
        assert calculator.getResult() == 20;

        calculator.divide(2);
        assert calculator.getResult() == 10;

        calculator.multiply(2);
        assert calculator.getResult() == 20;
    }
}
