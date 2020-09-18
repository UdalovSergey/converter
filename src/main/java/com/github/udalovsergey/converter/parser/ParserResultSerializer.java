package com.github.udalovsergey.converter.parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ParserResultSerializer extends StdSerializer<ParserResult> {
    protected ParserResultSerializer() {
        super(ParserResult.class);
    }

    @Override
    public void serialize(ParserResult result, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if(result.getOrder() != null) {
            jsonGenerator.writeNumberField("id", result.getOrder().getOrderId());
            jsonGenerator.writeNumberField("amount", result.getOrder().getAmount());
            jsonGenerator.writeStringField("comment", result.getOrder().getComment());
        }
        jsonGenerator.writeStringField("filename", result.getFileName());
        jsonGenerator.writeNumberField("line", result.getLine());
        jsonGenerator.writeStringField("result", result.getResult());
    }
}
