package br.com.rinha.controllers;

import br.com.rinha.models.Pessoa;
import br.com.rinha.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class PessoalController {

    private PessoaService pessoaService;

    @Autowired
    public PessoalController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> getAll(@RequestParam(required = false, name = "t") String t) {
        return t != null && !t.isEmpty() ? ResponseEntity.ok(pessoaService.buscarPessoas(t)) : ResponseEntity.ok(pessoaService.listaPessoas());
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Pessoa> addPessoas(@Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaCriada = this.pessoaService.cadastraPessoa(pessoa);
        return ResponseEntity.status(CREATED).header("Location", "/pessoas/".concat(pessoa.getId().toString())).body(pessoaCriada);
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Pessoa> buscaPessoaPeloID(@PathVariable("id") UUID id) {
        Pessoa pessoa = pessoaService.buscaPessoaPeloID(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Long> contagem() {
        return ResponseEntity.ok(pessoaService.contagem());
    }
}
