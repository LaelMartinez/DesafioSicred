package com.sicred.associado.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sicred.associado.entities.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, UUID> {

	@Query( nativeQuery = true, value = "SELECT * FROM tb_associado WHERE uuid = :uuid" )
	Optional<Associado> findByUUID(UUID uuid);

	
	@Query( nativeQuery = true, value = "SELECT * FROM tb_associado WHERE documento = :documento" )
	Optional<Associado> findByDocumento1(String documento);

	@Query("SELECT a FROM Associado a WHERE a.documento = :documento" )
	Optional<Associado> findByDocumento2(String documento);
	
	@Query("SELECT new com.sicred.associado.dto.AssociadoDTO(obj) "
			+ "FROM Associado obj " 
			+ "WHERE obj.documento = :documento")
	Optional<Associado> findByDocumento3(String documento);


}
