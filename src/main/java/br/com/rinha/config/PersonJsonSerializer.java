package br.com.rinha.config;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PersonJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String[] list = s.split(", "); // Supondo que sua string esteja separada por vírgulas
        jsonGenerator.writeStartArray(); // Inicia um array JSON
        for (String element : list) {
            jsonGenerator.writeString(element); // Escreve cada elemento da lista no array JSON
        }
        jsonGenerator.writeEndArray(); // Finaliza o array JSON
    }
}