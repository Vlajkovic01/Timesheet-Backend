package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsClientByName(String name);

    Client findClientByName(String name);

    @Query(value = "SELECT client FROM Client client WHERE client.name LIKE CONCAT('%', :searchQuery, '%') AND client.deleted = false")
    Page<Client> filterAll(String searchQuery, Pageable pageable);

    Page<Client> findAll(Pageable pageable);
    Page<Client> findAllByDeletedFalse(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Client client SET client.deleted = true WHERE client.id = :id")
    void deleteClientById(@Param("id") Integer id);

    Client findClientById(Integer id);
}
