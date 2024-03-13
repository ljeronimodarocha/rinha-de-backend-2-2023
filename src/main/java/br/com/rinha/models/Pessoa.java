package br.com.rinha.models;

import br.com.rinha.config.DateDeSerializer;
import br.com.rinha.config.PersonJsonDeserializer;
import br.com.rinha.config.PersonJsonSerializer;
import br.com.rinha.validators.string.StringOnly;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "pessoa")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa implements Serializable {


    @Serial
    private static final long serialVersionUID = 5001150500274082154L;

    @Id
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "Campo obrigatório")
    @NotEmpty(message = "Campo obrigatório")
    @Column(unique = true, nullable = false, length = 32)
    private String apelido;

    @NotNull(message = "Campo obrigatório")
    @NotEmpty(message = "Campo obrigatório")
    @StringOnly
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @JsonDeserialize(using = DateDeSerializer.class)
    @NotNull(message = "Campo obrigatório")
    private LocalDate nascimento;

    //@StringOnly
    @JsonDeserialize(using = PersonJsonDeserializer.class)
    @JsonSerialize(using = PersonJsonSerializer.class)
    @Column(name = "stack", columnDefinition = "text", nullable = false)
    private String stack;

    @JsonIgnore
    private String termoBusca;

}
