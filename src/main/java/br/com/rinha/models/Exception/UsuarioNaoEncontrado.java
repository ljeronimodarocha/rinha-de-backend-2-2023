package br.com.rinha.models.Exception;

public class UsuarioNaoEncontrado extends RuntimeException {

    private final String message;

    public UsuarioNaoEncontrado(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                '}';
    }
}
