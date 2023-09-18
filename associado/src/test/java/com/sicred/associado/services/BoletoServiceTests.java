package com.sicred.associado.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sicred.associado.dto.BoletoDTO;
import com.sicred.associado.dto.BoletoPfDTO;
import com.sicred.associado.entities.Boleto;
import com.sicred.associado.repositories.BoletoRepository;

public class BoletoServiceTests {

    @InjectMocks
    private BoletoService boletoService;

    @Mock
    private BoletoRepository boletoRepository;

    @Mock
    private AssociadoService associadoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarBoletoPf() {
        BoletoPfDTO boletoPfDTO = new BoletoPfDTO();
        boletoPfDTO.setUuid(UUID.randomUUID());
        boletoPfDTO.setUuidAssociado(UUID.randomUUID());
        boletoPfDTO.setValor(new BigDecimal("100.00")); // Usando BigDecimal para representar valor decimal
        boletoPfDTO.setVencimento(new Date());
        boletoPfDTO.setDocumentoPagador("1234567890");
        boletoPfDTO.setNomePagador("Nome");
        boletoPfDTO.setNomeFantasiaPagador("Nome Fantasia");

        when(boletoRepository.save(any(Boleto.class))).thenReturn(new Boleto());

        Boleto result = boletoService.criarBoletoPf(boletoPfDTO);

        assertNotNull(result);
    }

    // Teste para criarBoletoPj é semelhante ao teste criarBoletoPf, mas com comportamento específico.

    @Test
    public void testRealizarPagamentoBoletoExistente() {
        BoletoDTO boletoDTO = new BoletoDTO();
        boletoDTO.setUuid(UUID.randomUUID());
        boletoDTO.setUuidAssociado(UUID.randomUUID());
        boletoDTO.setValor(new BigDecimal("100.00")); 

        Boleto boletoExistente = new Boleto();
        boletoExistente.setSituacao("PENDENTE");

        when(boletoRepository.findByUuidAndUuidAssociado(boletoDTO.getUuid(), boletoDTO.getUuidAssociado()))
                .thenReturn(Optional.of(boletoExistente));
        when(boletoRepository.save(any(Boleto.class))).thenReturn(new Boleto());

        ResponseEntity<String> response = boletoService.realizarPagamento(boletoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pagamento realizado com sucesso.", response.getBody());
    }

    // Teste para realizarPagamento com outros cenários, como boleto já pago ou valor divergente, deve ser criado.

    @Test
    public void testRealizarPagamentoBoletoNaoExistente() {
        BoletoDTO boletoDTO = new BoletoDTO();
        boletoDTO.setUuid(UUID.randomUUID());
        boletoDTO.setUuidAssociado(UUID.randomUUID());
        boletoDTO.setValor(new BigDecimal("100.00")); // Usando BigDecimal para representar valor decimal

        when(boletoRepository.findByUuidAndUuidAssociado(boletoDTO.getUuid(), boletoDTO.getUuidAssociado()))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = boletoService.realizarPagamento(boletoDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Teste para realizarPagamento com outros cenários, como boleto já pago ou valor divergente, deve ser criado.
}


