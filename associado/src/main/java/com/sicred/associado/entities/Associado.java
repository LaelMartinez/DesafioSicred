package com.sicred.associado.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.sicred.associado.dto.AssociadoDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

@Entity
@Table(name = "tb_associado")
public class Associado  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID uuid;
	
	@Column(unique = true)
	@Size(max = 14)
	private String documento;
	@Size(max = 02)
	private String tipoPessoa;
	@Size(max = 50)
	private String nome;
	
	public Associado(AssociadoDTO entity) {
		uuid = entity.getUuid();
		documento = entity.getDocumento();
		tipoPessoa = entity.getTipoPessoa();
		nome = entity.getNome();
	}


}
