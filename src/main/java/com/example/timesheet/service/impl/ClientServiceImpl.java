package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.search.SearchRequestDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.ClientRepository;
import com.example.timesheet.service.ClientService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final CustomModelMapper modelMapper;

    private final EmployeeService employeeService;

    public ClientServiceImpl(EmployeeService employeeService, CustomModelMapper modelMapper, ClientRepository clientRepository) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addNewClient(ClientDTO clientDTO, Authentication authentication) {

        if (!employeeService.isAdmin(authentication)) {
            return null;
        }

        Client newClient = modelMapper.map(clientDTO, Client.class);

        if (existsClient(newClient)) {
            return null;
        }

        save(newClient);

        return newClient;
    }

    @Override
    public Client findClientByName(String name) {
        return clientRepository.findClientByName(name);
    }

    @Override
    public boolean existsClient(Client client) {
        return clientRepository.existsClientByName(client.getName());
    }

    @Override
    public List<Client> findClients(SearchRequestDTO searchRequestDTO, Pageable pageable) {

        Page<Client> pagedResult = Page.empty();

        if (searchRequestDTO == null) {
            return clientRepository.findAll(pageable).getContent();
        }

        if (searchRequestDTO.getSearchFilter() != null) {
            pagedResult = clientRepository.findClientsByNameStartsWith(searchRequestDTO.getSearchFilter(), pageable);
        }

        if (searchRequestDTO.getSearchQuery() != null) {
            pagedResult = clientRepository.findClientsByName(searchRequestDTO.getSearchQuery(), pageable);
        }

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}
