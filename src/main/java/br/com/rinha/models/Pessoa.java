package br.com.rinha.models;

import br.com.rinha.config.PersonJsonDeserializer;
import br.com.rinha.config.PersonJsonSerializer;
import br.com.rinha.validators.string.StringOnly;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
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

    @Size(max = 32)
    @NotNull(message = "Campo obrigatório")
    @NotEmpty(message = "Campo obrigatório")
    @Column(unique = true)
    private String apelido;

    @Size(max = 100)
    @NotNull(message = "Campo obrigatório")
    @NotEmpty(message = "Campo obrigatório")
    @StringOnly
    private String nome;

    private String nascimento;

    //@StringOnly
    @JsonDeserialize(using = PersonJsonDeserializer.class)
    @JsonSerialize(using = PersonJsonSerializer.class)
    private String stack;

    @JsonIgnore
    private String termoBusca;

}
