package com.sicred.associado.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sicred.associado.dto.BoletoDTO;
import com.sicred.associado.dto.BoletoPfDTO;
import com.sicred.associado.dto.BoletoPjDTO;
import com.sicred.associado.entities.Boleto;
import com.sicred.associado.services.BoletoService;

@RestController
@RequestMapping("/boletos")
public class BoletoController {
     
	@Autowired
	private BoletoService service;

	@GetMapping("/associado/uuid/{uuidAssociado}")
	public ResponseEntity<List<Boleto>> listarBoletosPorUUIDAssociado(@PathVariable UUID uuidAssociado) {
		List<Boleto> boletos = service.ListarBoletosUUID(uuidAssociado);
		return ResponseEntity.ok(boletos);
	}

	@GetMapping("/associado/documento/{documentoAssociado}")
	public ResponseEntity<List<Boleto>> listarBoletosPorDocumentoAssociado(@PathVariable String documentoAssociado) {
		List<Boleto> boletos = service.ListarBoletosDocumento(documentoAssociado);
		return ResponseEntity.ok(boletos);
	}

	@ResponseBody
	@PostMapping("**/criarBoletoPf")
	public ResponseEntity<?> criarBoletoPf(@Valid @RequestBody BoletoPfDTO boleto) {
		try {
			Boleto novoBoleto = service.criarBoletoPf(boleto);
			return ResponseEntity.status(HttpStatus.CREATED).body(novoBoleto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping("**/criarBoletoPj")
	public ResponseEntity<?> criarBoletoPf(@Valid @RequestBody BoletoPjDTO boleto) {
		try {
			Boleto novoBoleto = service.criarBoletoPj(boleto);
			return ResponseEntity.status(HttpStatus.CREATED).body(novoBoleto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping("/pagamento")
	public ResponseEntity<String> realizarPagamento(@RequestBody BoletoDTO boleto) {

		return service.realizarPagamento(boleto);
	}

	@GetMapping("/gerarArquivoBoletos")
	public void gerarArquivoBoletos() {
     	System.out.println("gerarArquivoBoleto");
		service.gerarBoleto();
	}

	
}
