package com.github.udalovsergey.converter.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.github.udalovsergey.converter.domen.Order;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser implements FileParser {
    private CsvMapper csvMapper = new CsvMapper()
            .configure(com.fasterxml.jackson.dataformat.csv.CsvParser.Feature.FAIL_ON_MISSING_COLUMNS, true);
    private Validator validator = Validation.
            buildDefaultValidatorFactory()
            .getValidator();

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
                    Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
                    if (!constraintViolations.isEmpty()) {
                        return new ParserResult(file.getName(), lineCounter.getAndIncrement(), constraintViolations
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining(";")));
                    }
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
