package br.com.rinha.services;

import br.com.rinha.interfaces.PessoalRepository;
import br.com.rinha.models.Exception.UsuarioJaRegistrado;
import br.com.rinha.models.Exception.UsuarioNaoEncontrado;
import br.com.rinha.models.Pessoa;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.CannotSerializeTransactionException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Cacheable(value = "pessoasCache")
    public List<Pessoa> buscarPessoas(String t) {
        return List.of();
    }

    public List<Pessoa> listaPessoas() {
        return (List<Pessoa>) this.repository.findAll();

    }

    @CachePut(value = "pessoaApelidoCache", key = "#pessoa.apelido")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Pessoa cadastraPessoa(Pessoa pessoa) {
        if (redisTemplate.opsForValue().get(pessoa.getApelido()) != null) {
            throw new UsuarioJaRegistrado("Pessoa já cadastrado");
        }
        pessoa.setId(UUID.randomUUID());
        Pessoa pessoalJaExiste = this.repository.findFirstByApelido(pessoa.getApelido());
        if (pessoalJaExiste != null) {
            throw new UsuarioJaRegistrado("Pessoa já cadastrado");
        }
        int maxTentativas = 3;
        for (int tentativa = 1; tentativa <= maxTentativas; tentativa++) {
            try {
                pessoalJaExiste = this.repository.save(pessoa);
                redisTemplate.opsForValue().set(pessoa.getApelido(), pessoa);
                break;
            } catch (CannotSerializeTransactionException | CannotAcquireLockException e) {
                if (tentativa == maxTentativas) throw e;
            }
        }

        return pessoalJaExiste;
    }

    @Cacheable(value = "pessoaCache")
    public Pessoa buscaPessoaPeloID(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontrado("Usuário não encontrado"));
    }

    public Long contagem() {
        return this.repository.count();
    }


    private void cachePessoaCadastrada(String id, Pessoa pessoa) {
        String key = "pessoaId::" + id;
        redisTemplate.opsForValue().set(key, pessoa, 2, TimeUnit.MINUTES);
        // Define um tempo de vida para o cache negativo para evitar armazenar indefinidamente.
    }
}
