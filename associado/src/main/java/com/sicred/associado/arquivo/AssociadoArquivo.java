package com.sicred.associado.arquivo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociadoArquivo {
    private String documento; 

    public String getDocumentoFormatado() {
        return String.format("%14s", documento).replace(' ', '0');
    }
}