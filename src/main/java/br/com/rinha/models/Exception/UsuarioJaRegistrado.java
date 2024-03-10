package br.com.rinha.models.Exception;

import lombok.Getter;


@Getter
public class UsuarioJaRegistrado extends RuntimeException {


    private final String message;

    public UsuarioJaRegistrado(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                '}';
    }
}
