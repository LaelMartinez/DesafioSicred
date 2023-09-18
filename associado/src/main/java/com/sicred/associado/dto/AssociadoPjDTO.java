package com.sicred.associado.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.sicred.associado.entities.Associado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssociadoPjDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID uuid;
    @NotBlank(message = "CNPJ não informado!")
	@CNPJ(message="CNPJ Inváido")
	@Size(max = 14)
	private String documento;
	@Size(max = 02)
    @NotBlank(message = "Tipo pessoa não informado!")
	private String tipoPessoa;
	@Size(max = 50)
    @NotBlank(message = "Nome não informado!")
	private String nome;
	
	public AssociadoPjDTO(Associado entity) {
		uuid = entity.getUuid();
		documento = entity.getDocumento();
		tipoPessoa = entity.getTipoPessoa();
		nome = entity.getNome();
	}

	public AssociadoPjDTO(@Valid AssociadoPjDTO dto) {
		uuid = dto.getUuid();
		documento = dto.getDocumento();
		tipoPessoa = dto.getTipoPessoa();
		nome = dto.getNome();
	}

}


