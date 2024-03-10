package br.com.rinha.interfaces;

import br.com.rinha.models.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoalRepository extends CrudRepository<Pessoa, UUID> {
    Pessoa findFirstByApelido(String apelido);

    @Query("select p from Pessoa p " +
            "where p.apelido like %:term% " +
            "or p.nome like %:term% " +
            "or p.nome like %:term% ")
    List<Pessoa> findByTermoDeBusca(@Param("term") String term);
}
