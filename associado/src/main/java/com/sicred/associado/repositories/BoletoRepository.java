package com.sicred.associado.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sicred.associado.entities.Boleto;

public interface BoletoRepository extends JpaRepository<Boleto, UUID> {
    Optional<Boleto> findByUuidAndUuidAssociado(UUID uuid, UUID uuidAssociado);
    List<Boleto> findByUuidAssociado(UUID uuidAssociado);
    List<Boleto> findByDocumentoPagador(String documentoPagador);
}
