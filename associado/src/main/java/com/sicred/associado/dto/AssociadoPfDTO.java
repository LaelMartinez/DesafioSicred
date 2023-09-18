package com.sicred.associado.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.sicred.associado.entities.Associado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssociadoPfDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID uuid;
    @NotBlank(message = "CPF não informado!")
	@CPF(message="CPF Inváido")
	@Size(max = 11)
	private String documento;
	@Size(max = 02)
    @NotBlank(message = "Tipo pessoa não informado!")
	private String tipoPessoa;
	@Size(max = 50)
	@NotBlank(message = "Nome não informado!")
	private String nome;
	
	public AssociadoPfDTO(Associado entity) {
		uuid = entity.getUuid();
		documento = entity.getDocumento();
		tipoPessoa = entity.getTipoPessoa();
		nome = entity.getNome();
	}

	public AssociadoPfDTO(@Valid AssociadoPfDTO dto) {
		uuid = dto.getUuid();
		documento = dto.getDocumento();
		tipoPessoa = dto.getTipoPessoa();
		nome = dto.getNome();
	}
}


