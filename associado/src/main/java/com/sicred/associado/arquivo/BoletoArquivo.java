package com.sicred.associado.arquivo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoletoArquivo {
    private String documentoAssociado; 
    private String identificadorBoleto; 
    private BigDecimal valor; 

    public String getDocumentoAssociadoFormatado() {
        return String.format("%14s", documentoAssociado).replace(' ', '0');
    }

    public String getIdentificadorBoletoFormatado() {
        return String.format("%-14s", identificadorBoleto);
    }

    public String getValorFormatado() {
        return String.format("%019d", valor.movePointRight(2).longValue());
    }
}
