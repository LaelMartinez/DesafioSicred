package com.sicred.associado.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sicred.associado.dto.AssociadoDTO;
import com.sicred.associado.dto.AssociadoPfDTO;
import com.sicred.associado.dto.AssociadoPjDTO;
import com.sicred.associado.entities.Associado;
import com.sicred.associado.repositories.AssociadoRepository;
import com.sicred.associado.services.exceptions.DatabaseException;
import com.sicred.associado.services.exceptions.NonUniqueResultException;
import com.sicred.associado.services.exceptions.ResourceNotFoundException;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repository;

	@Transactional(readOnly = true)
	public List<AssociadoDTO> findAll() {
		List<Associado> list = repository.findAll();
		List<AssociadoDTO> listDTO = new ArrayList<>();
		for (Associado associado : list) {
			AssociadoDTO associadoDTO = new AssociadoDTO();
			copyEntityToDTO(associadoDTO, associado);
			listDTO.add(associadoDTO);
		}
		return listDTO;
	}

	@Transactional(readOnly = true)
	public AssociadoDTO findById(UUID id) {
		Optional<Associado> obj = repository.findById(id);
		Associado entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new AssociadoDTO(entity);
	}

	@Transactional(readOnly = true)
	public AssociadoDTO findByDocumento(String documento) {
		Optional<Associado> obj = repository.findByDocumento1(documento);
		Associado entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new AssociadoDTO(entity);
	}

	@Transactional
	public AssociadoDTO insert(AssociadoDTO dto) {
		Associado entity = new Associado();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new AssociadoDTO(entity);
	}

	@Transactional
	public AssociadoDTO update(UUID id, AssociadoDTO dto) {
		try {
			Optional<Associado> obj = repository.findById(id);
			Associado entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			
			return new AssociadoDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(UUID id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(AssociadoDTO dto, Associado entity) {
		entity.setDocumento(dto.getDocumento());
		entity.setTipoPessoa(dto.getTipoPessoa());
		entity.setNome(dto.getNome());
	}

	private void copyEntityToDTO(AssociadoDTO dto, Associado entity) {
		dto.setUuid(entity.getUuid());
		dto.setDocumento(entity.getDocumento());
		dto.setTipoPessoa(entity.getTipoPessoa());
		dto.setNome(entity.getNome());
	}

	public void copyPjToDTO(AssociadoPjDTO pjdto, AssociadoDTO dto) {
		dto.setUuid(pjdto.getUuid());
		dto.setDocumento(pjdto.getDocumento());
		dto.setTipoPessoa(pjdto.getTipoPessoa());
		dto.setNome(pjdto.getNome());
	}

	public void copyPfToDTO(AssociadoPfDTO pfdto, AssociadoDTO dto) {
		dto.setUuid(pfdto.getUuid());
		dto.setDocumento(pfdto.getDocumento());
		dto.setTipoPessoa(pfdto.getTipoPessoa());
		dto.setNome(pfdto.getNome());
	}

	public void verDocumento(String value) {
		Optional<Associado> obj = repository.findByDocumento1(value);
		if (obj.isPresent()) {
			throw new NonUniqueResultException("Documento já existe");
		}
	}

	public boolean existeDocumento(String value) {
		Optional<Associado> obj = repository.findByDocumento1(value);
		if (obj != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verificarExistenciaAssociado(UUID uuidAssociado) {
        Optional<Associado> associadoExistente = repository.findById(uuidAssociado);
		if (associadoExistente != null) {
			return true;
		} else {
			return false;
		}
	}

}
