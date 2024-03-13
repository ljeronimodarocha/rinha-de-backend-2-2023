package br.com.rinha.interfaces;

import br.com.rinha.models.Pessoa;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoalRepository extends CrudRepository<Pessoa, UUID> {

    @Query("select p.apelido from Pessoa p where p.apelido =  :apelido")
    String findFirstByApelido(@Param("apelido") String apelido);

    @Query("select p from Pessoa p " +
            "where p.apelido like %:term% " +
            "or p.nome ilike %:term% " +
            "or p.stack ilike %:term% ")
    List<Pessoa> findByTermoDeBusca(@Param("term") String term);

    @Query("select p from Pessoa p " +
            "where p.termoBusca ilike %:term% ")
    List<Pessoa> findByTermoDeBuscaV2(@Param("term") String term);

    @Override
    @NonNull
    List<Pessoa> findAll();
}
