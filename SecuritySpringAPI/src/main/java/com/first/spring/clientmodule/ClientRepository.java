package com.first.spring.clientmodule;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
  Client findByEmail(String email);
  Optional<List<Client>> findByAuthorities_AuthNameOrderByFirstNameAscLastNameAsc(String authName);
  
  @Query("SELECT c FROM Client c WHERE NOT EXISTS (SELECT a FROM c.authorities a WHERE a.authName = :roleName)")
  Optional<List<Client>> findClientsWithoutRole(@Param("roleName") String roleName);
	
	
}
	