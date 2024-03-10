package br.com.rinha.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonJsonDeserializer extends JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List<String> list = new ArrayList<>();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.isArray()) {
            for (JsonNode elementNode : node) {
                if (elementNode.isInt() || elementNode.isBigInteger()) {
                    throw new JsonProcessingException("O valor deve ser string") {};
                }
                list.add(elementNode.asText());
            }
        }
        return list;
    }
}