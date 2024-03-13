package br.com.rinha.config;

import br.com.rinha.util.Utils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonJsonDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        StringBuilder builder = new StringBuilder();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.isArray()) {
            for (JsonNode elementNode : node) {
                if (!elementNode.isTextual() || Utils.isNumeric(elementNode.asText())) {
                    throw new IllegalArgumentException();
                }
                if (!builder.isEmpty()) builder.append(", ");
                builder.append(elementNode.asText());
            }
        }
        return builder.toString();
    }

}