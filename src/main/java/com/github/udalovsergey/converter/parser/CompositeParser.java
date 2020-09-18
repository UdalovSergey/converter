package com.github.udalovsergey.converter.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.String.format;

@Component
public class CompositeParser implements FileParser {

    private final FileParser csvParser;
    private final FileParser jsonParser;

    @Autowired
    public CompositeParser(ObjectMapper objectMapper) {
        this.csvParser = new CsvParser();
        this.jsonParser = new JsonParser(objectMapper);
    }


    @Override
    public Stream<ParserResult> parse(File file) {
        String fileExtension = getExtensionByStringHandling(file.getName())
                .orElseThrow(() -> new ParserException("File must have an extension"));
        switch (fileExtension) {
            case "csv":
                return csvParser.parse(file);
            case "json":
                return jsonParser.parse(file);
            default:
                throw new ParserException(format("Unsupported file format [%s]", fileExtension));
        }
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf('.') + 1));
    }
}
