package com.example.aspblindajes.repository;

import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.model.Client;
import com.example.aspblindajes.model.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByName(String name);

    @Query(nativeQuery = true,
            value = "SELECT c.* FROM client c " +
                    "WHERE c.hidden = 0")
    List<Client> findAllClientHiddenFalse();
}
