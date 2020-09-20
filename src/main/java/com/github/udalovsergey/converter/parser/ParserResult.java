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

    private Validator validator = Validation.
            buildDefaultValidatorFactory()
            .getValidator();

    private Order order;
    private String fileName;
    private long line;
    private String result;


    public ParserResult(Order order, String fileName, long line) {
        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        if (!constraintViolations.isEmpty()) {
             this.result = constraintViolations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(";"));
        }else {
            this.result = "OK";
        }
        this.order = order;
        this.fileName = fileName;
        this.line = line;
    }

    public ParserResult(String fileName, long line, String result) {
        this.fileName = fileName;
        this.line = line;
        this.result = result;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
