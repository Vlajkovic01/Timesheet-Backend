package com.example.timesheet.service;

import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.search.SearchRequestDTO;
import com.example.timesheet.model.entity.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    Client addNewClient(ClientDTO clientDTO, Authentication authentication);
    Client findClientByName(String name);
    boolean existsClient(Client client);
    List<Client> findClients(SearchRequestDTO searchRequestDTO, Integer pageNo, Integer pageSize);
    void save(Client client);
}
