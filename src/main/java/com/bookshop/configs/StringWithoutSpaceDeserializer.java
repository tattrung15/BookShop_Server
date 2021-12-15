package com.bookshop.configs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class StringWithoutSpaceDeserializer extends StdDeserializer<String> {
    protected StringWithoutSpaceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        return p.getText() != null ? p.getText().trim() : null;
    }
}
