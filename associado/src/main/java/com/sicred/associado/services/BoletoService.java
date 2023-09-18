package com.sicred.associado.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sicred.associado.arquivo.BoletoArquivo;
import com.sicred.associado.dto.BoletoDTO;
import com.sicred.associado.dto.BoletoPfDTO;
import com.sicred.associado.dto.BoletoPjDTO;
import com.sicred.associado.entities.Boleto;
import com.sicred.associado.repositories.BoletoRepository;

@Service
public class BoletoService {

    @Autowired
    private BoletoRepository repository;

    @Autowired
    private AssociadoService associadoService;

    public List<Boleto> ListarBoletosUUID(UUID idAssociado) {
    	return repository.findByUuidAssociado(idAssociado);
    }
    public List<Boleto> ListarBoletosDocumento(String documentoAssociado) {
    	return repository.findByDocumentoPagador(documentoAssociado);
    }
    
    public Boleto criarBoletoPf(BoletoPfDTO boleto) {
        if (!verificarUnicidadeUuidAssociado(boleto.getUuid(), boleto.getUuidAssociado())) {
            throw new IllegalArgumentException("A combinação UUID e UUID do associado não é única!");
        }

        if (!associadoService.verificarExistenciaAssociado(boleto.getUuidAssociado())) {
            throw new IllegalArgumentException("O associado especificado não existe!");
        }

        Date dataAtual = new Date();
        if (boleto.getVencimento().before(dataAtual)) {
            throw new IllegalArgumentException("A data de vencimento não pode ser menor que a data atual.");
        }
  
        Boleto novoBoleto = new Boleto();
        novoBoleto.setUuid(boleto.getUuid());
        novoBoleto.setValor(boleto.getValor());
        novoBoleto.setVencimento(boleto.getVencimento());
        novoBoleto.setUuidAssociado(boleto.getUuidAssociado());
        novoBoleto.setDocumentoPagador(boleto.getDocumentoPagador());
        novoBoleto.setNomePagador(boleto.getNomePagador());
        novoBoleto.setNomeFantasiaPagador(boleto.getNomeFantasiaPagador());
        novoBoleto.setSituacao("PENDENTE"); 
        novoBoleto = repository.save(novoBoleto);
        return novoBoleto;
    }

    public Boleto criarBoletoPj(BoletoPjDTO boleto) {
        if (!verificarUnicidadeUuidAssociado(boleto.getUuid(), boleto.getUuidAssociado())) {
            throw new IllegalArgumentException("A combinação UUID e UUID do associado não é única!");
        }

        if (!associadoService.verificarExistenciaAssociado(boleto.getUuidAssociado())) {
            throw new IllegalArgumentException("O associado especificado não existe!");
        }

        Date dataAtual = new Date();
        if (boleto.getVencimento().before(dataAtual)) {
            throw new IllegalArgumentException("A data de vencimento não pode ser menor que a data atual.");
        }
  
        Boleto novoBoleto = new Boleto();
        novoBoleto.setUuid(boleto.getUuid());
        novoBoleto.setValor(boleto.getValor());
        novoBoleto.setVencimento(boleto.getVencimento());
        novoBoleto.setUuidAssociado(boleto.getUuidAssociado());
        novoBoleto.setDocumentoPagador(boleto.getDocumentoPagador());
        novoBoleto.setNomePagador(boleto.getNomePagador());
        novoBoleto.setNomeFantasiaPagador(boleto.getNomeFantasiaPagador());
        novoBoleto.setSituacao("PENDENTE"); 
        novoBoleto = repository.save(novoBoleto);
        return novoBoleto;
    }


    public ResponseEntity<String> realizarPagamento(BoletoDTO boleto) {
  
    	Optional<Boleto> boletoOptional = repository.findByUuidAndUuidAssociado(boleto.getUuid(), boleto.getUuidAssociado());
        
    	if (boletoOptional.isPresent()) {
            Boleto b = boletoOptional.get();
            if ("PAGO".equals(b.getSituacao())) {
                return ResponseEntity.badRequest().body("O boleto já foi pago.");
            }
            if (boleto.getValor() != null && b.getValor() != null && boleto.getValor().compareTo(b.getValor()) == 0) {
                b.setSituacao("PAGO");
                repository.save(b);

                return ResponseEntity.ok("Pagamento realizado com sucesso.");
            } else {
                return ResponseEntity.badRequest().body("O valor do pagamento é divergente do cadastrado.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    private boolean verificarUnicidadeUuidAssociado(UUID uuid, UUID uuidAssociado) {
        Optional<Boleto> boletoExistente = repository.findByUuidAndUuidAssociado(uuid, uuidAssociado);
        return !boletoExistente.isPresent();
    }
	
   public void gerarBoleto() { 
        List<BoletoArquivo> listaDeBoletos = obterBoletos(); 
        String nomeArquivo = "c:\\sicred\\boletos.txt"; 
        gerarArquivo(listaDeBoletos, nomeArquivo);
    }

    private List<BoletoArquivo> obterBoletos() {
    	List<Boleto> entities = repository.findAll();
        BoletoArquivo bArquivo = new BoletoArquivo();
        List<BoletoArquivo> listaArquivo = new ArrayList<>();
    	 
        for (Boleto bEntity : entities) {
        	bArquivo.setDocumentoAssociado(bEntity.getDocumentoPagador());
        	bArquivo.setIdentificadorBoleto(bEntity.getUuid().toString());
			bArquivo.setValor(bEntity.getValor());
			listaArquivo.add(bArquivo);
		}
        return listaArquivo;
    }
    
    public void gerarArquivo(List<BoletoArquivo> listaDeBoletos, String nomeArquivo) {
     	String linha = "";
        try (FileWriter arquivo = new FileWriter(nomeArquivo)) {
            for (BoletoArquivo boleto : listaDeBoletos) {
                arquivo.write(linha);
                linha = boleto.getDocumentoAssociadoFormatado() +
                        boleto.getIdentificadorBoletoFormatado() +
                        boleto.getValorFormatado() + "\n";
            }
            arquivo.close();
        } catch (IOException e) {
        	System.out.println("Erro gerando boletos");
            e.printStackTrace();
        }
    }
    
}
