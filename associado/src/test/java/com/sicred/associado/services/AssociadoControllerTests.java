package com.sicred.associado.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sicred.associado.controller.AssociadoController;
import com.sicred.associado.dto.AssociadoDTO;
import com.sicred.associado.entities.Boleto;

public class AssociadoControllerTests {

    @InjectMocks
    private AssociadoController associadoController;

    @Mock
    private AssociadoService associadoService;

    @Mock
    private BoletoService boletoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllShouldReturnAssociadosList() {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        when(associadoService.findAll()).thenReturn(associadoDTOList);

        ResponseEntity<List<AssociadoDTO>> response = associadoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(associadoDTOList, response.getBody());
    }

    @Test
    public void findByIdShouldReturnAssociadoById() {
        UUID id = UUID.randomUUID();
        AssociadoDTO associadoDTO = new AssociadoDTO();
        when(associadoService.findById(id)).thenReturn(associadoDTO);

        ResponseEntity<AssociadoDTO> response = associadoController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(associadoDTO, response.getBody());
    }

    @Test
    public void findByDocumentoSouldReturnAssociadoByDocumento() {
        String documento = "1234567890";
        AssociadoDTO associadoDTO = new AssociadoDTO();
        when(associadoService.findByDocumento(documento)).thenReturn(associadoDTO);

        ResponseEntity<AssociadoDTO> response = associadoController.findByDocumento(documento);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(associadoDTO, response.getBody());
    }


    @Test
    public void updateShouldReturnAssociadoUpdate() {
        UUID id = UUID.randomUUID();
        AssociadoDTO entityDTO = new AssociadoDTO();
        entityDTO.setUuid(id);
        entityDTO.setNome("Lael");
        entityDTO.setDocumento("28712174831");
        entityDTO.setTipoPessoa("PF");
        associadoService.insert(entityDTO);
        
        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setNome("Lael Martinez");

        when(associadoService.update(id, associadoDTO)).thenReturn(associadoDTO);

        ResponseEntity<AssociadoDTO> response = associadoController.update(id, associadoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(associadoDTO, response.getBody());
    }

    @Test
    public void deleteSouldDeleteWhenBoletoExist() {
        UUID id = UUID.randomUUID();
        List<Boleto> boletos = new ArrayList<>();
        when(boletoService.ListarBoletosUUID(id)).thenReturn(boletos);
        doNothing().when(associadoService).delete(id);

        ResponseEntity<String> response = associadoController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteWithPendingBoletosShouldThrowException() {
        UUID id = UUID.randomUUID();
        List<Boleto> boletos = new ArrayList<>();
        Boleto boleto = new Boleto();
        boleto.setSituacao("PENDENTE");
        boletos.add(boleto);
        when(boletoService.ListarBoletosUUID(id)).thenReturn(boletos);

        assertThrows(IllegalStateException.class, () -> associadoController.delete(id));
    }
}
