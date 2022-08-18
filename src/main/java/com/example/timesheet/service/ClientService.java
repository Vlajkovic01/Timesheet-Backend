package com.example.timesheet.service;

import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.entity.Client;
import org.springframework.security.core.Authentication;

public interface ClientService {

    Client addNewClient(ClientDTO clientDTO, Authentication authentication);
    boolean existsClient(Client client);

    void save(Client client);
}
