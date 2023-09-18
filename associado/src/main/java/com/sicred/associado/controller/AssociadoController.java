package com.sicred.associado.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sicred.associado.dto.AssociadoDTO;
import com.sicred.associado.dto.AssociadoPfDTO;
import com.sicred.associado.dto.AssociadoPjDTO;
import com.sicred.associado.entities.Boleto;
import com.sicred.associado.services.AssociadoService;
import com.sicred.associado.services.BoletoService;

@RestController
@RequestMapping(value = "/associados")
public class AssociadoController {

	@Autowired
	private AssociadoService service;
	@Autowired
	private BoletoService serviceBoleto;
	

	@GetMapping
	public ResponseEntity<List<AssociadoDTO>> findAll() {
		List<AssociadoDTO> listDTO = service.findAll();
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AssociadoDTO> findById(@PathVariable UUID id) {
		AssociadoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/documento/{documento}")
	public ResponseEntity<AssociadoDTO> findByDocumento(@PathVariable String documento) {
		AssociadoDTO dto = service.findByDocumento(documento);
		return ResponseEntity.ok().body(dto);
	}

	@ResponseBody
	@PostMapping(value = "**/associadoPf")
	public ResponseEntity<AssociadoDTO> insertPf(@Valid @RequestBody AssociadoPfDTO dto) {
		service.verDocumento(dto.getDocumento());
		AssociadoDTO newDto = new AssociadoDTO();
		service.copyPfToDTO(dto, newDto);
		AssociadoDTO retornoDto = service.insert(newDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(retornoDto.getUuid())
				.toUri();
		return ResponseEntity.created(uri).body(retornoDto);
	}

	@ResponseBody
	@PostMapping(value = "**/associadoPj")
	public ResponseEntity<AssociadoDTO> insertPj(@Valid @RequestBody AssociadoPjDTO dto) {
		service.verDocumento(dto.getDocumento());
		AssociadoDTO newDto = new AssociadoDTO();
		service.copyPjToDTO(dto, newDto);
		AssociadoDTO retornoDto = service.insert(newDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(retornoDto.getUuid())
				.toUri();
		return ResponseEntity.created(uri).body(retornoDto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<AssociadoDTO> update(@PathVariable UUID id, @RequestBody AssociadoDTO dto) {
		AssociadoDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}

	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
	    List<Boleto> boletos = serviceBoleto.ListarBoletosUUID(id);
	    for (Boleto boleto : boletos) {
	        if ("PENDENTE".equals(boleto.getSituacao())) {
	            throw new IllegalStateException("Não é possível deletar, Associado com pagamento pendente!");
	        }
	    }
	    service.delete(id);
	    return ResponseEntity.noContent().build();
	}
	
}
