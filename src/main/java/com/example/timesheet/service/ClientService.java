package com.example.timesheet.service;

import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.entity.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    Client addNewClient(ClientDTO clientDTO, Authentication authentication);

    Client findClientByName(String name);

    Client findClientById(Integer id);

    boolean existsClient(Client client);

    List<Client> findUndeletedClients(String searchQuery, Pageable pageable);

    Client updateClient(ClientDTO clientDTO, Authentication authentication);

    void delete(Integer id);

    void save(Client client);
}
