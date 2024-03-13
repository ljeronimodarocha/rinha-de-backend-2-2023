package br.com.rinha.services;

import br.com.rinha.interfaces.PessoalRepository;
import br.com.rinha.models.Pessoa;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@NoArgsConstructor
@Service
public class PessoaService {

    private PessoalRepository repository;

    @Autowired
    public PessoaService(PessoalRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<Pessoa>> buscarPessoas(String t) {
        return ResponseEntity.ok(this.repository.findByTermoDeBuscaV2(t));
    }

    public ResponseEntity<List<Pessoa>> listaPessoas() {
        return ResponseEntity.ok(this.repository.findAll());

    }

    public ResponseEntity<Object> cadastraPessoa(Pessoa pessoa) {
        String apelidoJaExiste = this.repository.findFirstByApelido(pessoa.getApelido());

        if (apelidoJaExiste != null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        try {
            pessoa.setId(UUID.randomUUID());
            pessoa.setTermoBusca(pessoa.getNome() + ", " + pessoa.getApelido() + ", " + pessoa.getStack());
            return ResponseEntity.created(URI.create("/pessoas/" + pessoa.getId().toString())).body(this.repository.save(pessoa));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.unprocessableEntity().build();
        }

    }

    public ResponseEntity<Object> buscaPessoaPeloID(UUID id) {
        Optional<Pessoa> pessoa = this.repository
                .findById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa);

        }
        return ResponseEntity.notFound().build();

    }

    public Long contagem() {
        return this.repository.count();
    }
}
