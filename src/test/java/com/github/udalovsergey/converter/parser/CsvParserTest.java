package com.github.udalovsergey.converter.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

class CsvParserTest {

    private static ObjectMapper objectMapper;
    private static FileParser csvParser;
    private static FileParser jsonParser;

    @BeforeAll
    public static void before() {
        objectMapper = new ObjectMapper();
        csvParser = new CsvParser();
        jsonParser = new JsonParser(objectMapper);
    }

    @Test
    public void parseCsv() throws IOException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "test.csv");
        Stream<ParserResult> parse = csvParser.parse(file);
        parse.forEach(result -> {
            try {
                System.out.println(objectMapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void parseJson() throws IOException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "test.json");
        Stream<ParserResult> parse = jsonParser.parse(file);
        parse.forEach(result -> {
            try {
                System.out.println(objectMapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}