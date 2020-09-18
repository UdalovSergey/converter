package com.github.udalovsergey.converter.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.github.udalovsergey.converter.domen.Order;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class CsvParser implements FileParser {
    private CsvMapper csvMapper = new CsvMapper();

    @Override
    public Stream<ParserResult> parse(File file) {
        ObjectReader objectReader = csvMapper
                .readerFor(Order.class)
                .with(csvMapper.schemaFor(Order.class));
        AtomicLong lineCounter = new AtomicLong(1);
        try {
            return Files.lines(file.toPath()).map(line -> {
                Order order;
                try {
                    order = objectReader.readValue(line);
                } catch (JsonProcessingException e) {
                    return new ParserResult(file.getName(), lineCounter.getAndIncrement(), e.getMessage());
                }
                return new ParserResult(order, file.getName(), lineCounter.getAndIncrement(), "OK");
            });
        } catch (IOException e) {
            throw new ParserException(e);
        }
    }
}
