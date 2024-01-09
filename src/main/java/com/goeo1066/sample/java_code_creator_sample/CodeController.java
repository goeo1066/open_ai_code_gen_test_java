package com.goeo1066.sample.java_code_creator_sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.goeo1066.sample.java_code_creator_sample.records.CodeRequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/code")
public class CodeController {
    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @PostMapping
    public Mono<String> create(@RequestBody CodeRequestBody body) throws JsonProcessingException {
        return codeService.createClass(body.getPurpose());
    }
}
