package com.sicred.associado.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoletoPjDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID uuid;
 
    @NotBlank(message = "Valor não informado!")
    private BigDecimal valor;
    @NotBlank(message = "Data de vencimento não informada!")
    private Date vencimento;
    @NotBlank(message = "Associado não informado!")
    private UUID uuidAssociado;
    @NotBlank(message = "CNPJ não informado!")
	@CNPJ(message="CNPJ Inváido")
	@Size(max = 14)	
    private String documentoPagador;
    private String nomePagador;
    private String nomeFantasiaPagador;
    private String situacao;
}