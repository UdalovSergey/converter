package com.github.udalovsergey.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.udalovsergey.converter.parser.FileParser;
import com.github.udalovsergey.converter.parser.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ConverterRunner implements ApplicationRunner {

    private final FileParser compositeParser;
    private final ObjectMapper objectMapper;

    @Autowired
    public ConverterRunner(FileParser compositeParser, ObjectMapper objectMapper) {
        this.compositeParser = compositeParser;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        args.getNonOptionArgs()
                .parallelStream()
                .forEach(fileName -> compositeParser.parse(new File(fileName))
                        .forEach(result -> {
                            try {
                                System.out.println(objectMapper.writeValueAsString(result));
                            } catch (JsonProcessingException e) {
                                throw new ParserException(e);
                            }
                        }));
    }
}
