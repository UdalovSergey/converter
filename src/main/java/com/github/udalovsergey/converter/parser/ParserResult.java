package com.github.udalovsergey.converter.parser;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.udalovsergey.converter.domen.Order;

@JsonSerialize(using = ParserResultSerializer.class)
public class ParserResult {

    private Order order;
    private String fileName;
    private long line;
    private String result;


    public ParserResult(Order order, String fileName, long line, String result) {
        this.order = order;
        this.fileName = fileName;
        this.line = line;
        this.result = result;
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
