package br.com.rinha.controllers;

import br.com.rinha.models.Pessoa;
import br.com.rinha.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PessoalController {

    private PessoaService pessoaService;

    @Autowired
    public PessoalController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> getAll(@RequestParam(required = false, name = "t") String t) {
        return t != null && !t.isEmpty() ? pessoaService.buscarPessoas(t) : pessoaService.listaPessoas();
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Object> addPessoas(@Valid @RequestBody Pessoa pessoa) {
        return this.pessoaService.cadastraPessoa(pessoa);
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Object> buscaPessoaPeloID(@PathVariable("id") UUID id) {
        return pessoaService.buscaPessoaPeloID(id);
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Long> contagem() {
        return ResponseEntity.ok(pessoaService.contagem());
    }
}
