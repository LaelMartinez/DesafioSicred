package com.sicred.associado.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import com.sicred.associado.dto.AssociadoDTO;
import com.sicred.associado.entities.Associado;
import com.sicred.associado.repositories.AssociadoRepository;
import com.sicred.associado.services.exceptions.NonUniqueResultException;
import com.sicred.associado.services.exceptions.ResourceNotFoundException;

public class AssociadoServiceTests {

    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllShouldAssociadoList() {
      
        List<Associado> associadoList = new ArrayList<>();
        when(repository.findAll()).thenReturn(associadoList);

        List<AssociadoDTO> result = associadoService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void findByIdShouldReturnAssociadoWhenExist() {
        UUID id = UUID.randomUUID();
        Associado associado = new Associado();
        associado.setUuid(id);

        when(repository.findById(id)).thenReturn(Optional.of(associado));

        AssociadoDTO result = associadoService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getUuid());
    }

    @Test         
    public void findByDocumentoShouldReturnAssociadoWhenExist() {
        String documento = "1234567890";
        Associado associado = new Associado();
        associado.setDocumento(documento);

        when(repository.findByDocumento1(documento)).thenReturn(Optional.of(associado));

        AssociadoDTO result = associadoService.findByDocumento(documento);

        assertNotNull(result);
        assertEquals(documento, result.getDocumento());
    }

    @Test
    public void insertShouldInsertAssociadoWhenValidData() {
        AssociadoDTO dto = new AssociadoDTO();
        dto.setNome("Joao");
        dto.setDocumento("288712174831");
        dto.setTipoPessoa("PF");

        
        Associado entity = new Associado();

        when(repository.save(any(Associado.class))).thenReturn(entity);

        AssociadoDTO result = associadoService.insert(dto);

        assertNotNull(result);
    }

 
   
    
    @Test
    public void deleteShouldDeleteAssociadoWhenValidData() {
        UUID id = UUID.randomUUID();

        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> associadoService.delete(id));
    }

    @Test
    public void deleteNonExistentIdShouldReturnResourceNotFoundExceptionWhenIdNotExist() {
        UUID id = UUID.randomUUID();

        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(id);

        assertThrows(ResourceNotFoundException.class, () -> associadoService.delete(id));
    }

    @Test
    public void existeDocumentoShouldReturnTrueWhenExistAssociadoPorDocumento() {
        String documento = "1234567890";
        when(repository.findByDocumento1(documento)).thenReturn(Optional.of(new Associado()));

        boolean result = associadoService.existeDocumento(documento);

        assertTrue(result);
    }

   
    @Test
    public void verDocumentoShouldReturnNonUniqueResultExceptionWhenExistDocumentoAssociado() {
        String documento = "1234567890";
        when(repository.findByDocumento1(documento)).thenReturn(Optional.of(new Associado()));

        assertThrows(NonUniqueResultException.class, () -> associadoService.verDocumento(documento));
    }

    @Test
    public void documentoNonExistentShouldNotReturnThronWhenExistAssociadoPorDocumento() {
        String documento = "1234567890";
        when(repository.findByDocumento1(documento)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> associadoService.verDocumento(documento));
    }

    @Test
    public void verificarExistenciaAssociadoShouldReturnTrueWhenAssociadoExist() {
        UUID uuidAssociado = UUID.randomUUID();
        when(repository.findById(uuidAssociado)).thenReturn(Optional.of(new Associado()));

        boolean result = associadoService.verificarExistenciaAssociado(uuidAssociado);

        assertTrue(result);
    }


}
