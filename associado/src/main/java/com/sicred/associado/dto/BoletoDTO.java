package com.sicred.associado.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoletoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID uuid;
    private BigDecimal valor;
    private UUID uuidAssociado;
 	@CPF(message="CPF Inváido")
	@Size(max = 11)	
    @NotBlank(message = "CPF não informado!")
    private String documentoPagador;
}