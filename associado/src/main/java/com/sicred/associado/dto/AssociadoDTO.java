package com.sicred.associado.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.sicred.associado.entities.Associado;
import com.sicred.associado.validacao.UniqueDocumento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssociadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID uuid;
	
	@Column(unique = true)
	@UniqueDocumento
	@Size(max = 14)
	private String documento;
	@Size(max = 02)
	private String tipoPessoa;
	@Size(max = 50)
	private String nome;
	
	public AssociadoDTO(Associado entity) {
		uuid = entity.getUuid();
		documento = entity.getDocumento();
		tipoPessoa = entity.getTipoPessoa();
		nome = entity.getNome();
	}
}


