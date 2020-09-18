package com.github.udalovsergey.converter.parser;

public class ParserException extends RuntimeException {

    public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message) {
        super(message);
    }
}
