package com.github.udalovsergey.converter.parser;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.udalovsergey.converter.domen.Order;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@JsonSerialize(using = ParserResultSerializer.class)
public class ParserResult {

    private static final Validator VALIDATOR = Validation
            .buildDefaultValidatorFactory()
            .getValidator();

    private final Order order;
    private final String fileName;
    private final long line;
    private final String result;

    private ParserResult(Order order, String fileName, long line, String result) {
        this.order = order;
        this.fileName = fileName;
        this.line = line;
        this.result = result;
    }

    static ParserResult ofOrder(Order order, String fileName, long line) {
        Set<ConstraintViolation<Order>> constraintViolations = VALIDATOR.validate(order);
        if (!constraintViolations.isEmpty()) {
            String result = constraintViolations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            return new ParserResult(null, fileName, line, result);
        } else {
            return new ParserResult(order, fileName, line, "OK");
        }
    }

    static ParserResult ofParsingFailure(String fileName, long line, String result) {
        return new ParserResult(null, fileName, line, result);
    }

    public Order getOrder() {
        return order;
    }

    public String getFileName() {
        return fileName;
    }

    public long getLine() {
        return line;
    }

    public String getResult() {
        return result;
    }

}
