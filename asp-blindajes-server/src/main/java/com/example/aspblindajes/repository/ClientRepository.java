package com.example.aspblindajes.repository;

import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByName(String name);
}
