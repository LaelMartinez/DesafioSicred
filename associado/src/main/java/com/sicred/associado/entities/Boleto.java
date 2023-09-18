package com.sicred.associado.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity

@Table(uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"uuid", "uuidAssociado"})
	})
public class Boleto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID uuid;
 
    private BigDecimal valor;
    private Date vencimento;
    private UUID uuidAssociado;
    private String documentoPagador;
    private String nomePagador;
    private String nomeFantasiaPagador;
    private String situacao;
}