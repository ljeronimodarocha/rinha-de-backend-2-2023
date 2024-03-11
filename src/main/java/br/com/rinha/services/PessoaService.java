package br.com.rinha.services;

import br.com.rinha.interfaces.PessoalRepository;
import br.com.rinha.models.Pessoa;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@NoArgsConstructor
@Service
public class PessoaService {

    private PessoalRepository repository;

    private RedisTemplate<String, Pessoa> redisTemplate;


    @Autowired
    public PessoaService(PessoalRepository repository, RedisTemplate<String, Pessoa> redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    public ResponseEntity<List<Pessoa>> buscarPessoas(String t) {
        return ResponseEntity.ok(this.repository.findByTermoDeBuscaV2(t));
    }

    public ResponseEntity<List<Pessoa>> listaPessoas() {
        return ResponseEntity.ok(this.repository.findAll());

    }

    public ResponseEntity<Object> cadastraPessoa(Pessoa pessoa) {
        Pessoa pessoalJaExiste = this.repository.findFirstByApelido(pessoa.getApelido());

        if (pessoalJaExiste != null) {
            return ResponseEntity
                    .status(UNPROCESSABLE_ENTITY) // Use o código de status adequado
                    .build();
        }
        pessoa.setId(UUID.randomUUID());
        pessoa.setTermoBusca(pessoa.getNome() + ", " + pessoa.getApelido() + ", " + pessoa.getStack());
        pessoalJaExiste = this.repository.save(pessoa);
        return ResponseEntity.status(CREATED).header("Location", "/pessoas/".concat(pessoa.getId().toString())).body(pessoalJaExiste);

    }

    public ResponseEntity<Object> buscaPessoaPeloID(UUID id) {
        Optional<Pessoa> pessoa = this.repository
                .findById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa);

        }
        return ResponseEntity.status(NOT_FOUND).build();

    }

    public Long contagem() {
        return this.repository.count();
    }
}
