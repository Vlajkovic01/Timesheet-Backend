package com.example.timesheet.service.impl;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.ClientRepository;
import com.example.timesheet.service.ClientService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    public List<Client> findClients(String searchQuery, Pageable pageable) {
        if (searchQuery == null) {
            return clientRepository.findAll(pageable).getContent();
        }
        return clientRepository.filterAll(searchQuery, pageable).getContent();
    }

    @Override
    public Client updateClient(ClientDTO clientDTO, Authentication authentication) {
        Client clientForUpdate = findClientByName(clientDTO.getName());

        if (clientForUpdate == null) {
            throw new BadRequestException("Please provide a valid client.");
        }

        clientForUpdate.setAddress(clientDTO.getAddress());
        clientForUpdate.setCity(clientDTO.getCity());
        clientForUpdate.setZip(clientDTO.getZip());
        clientForUpdate.setCountry(clientDTO.getCountry());

        save(clientForUpdate);

        return clientForUpdate;
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}
