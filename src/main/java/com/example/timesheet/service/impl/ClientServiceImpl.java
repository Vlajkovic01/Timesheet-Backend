package com.example.timesheet.service.impl;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.ClientRepository;
import com.example.timesheet.service.ClientService;
import com.example.timesheet.service.EmployeeService;
import com.example.timesheet.service.ProjectService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ProjectService projectService;

    private final ClientRepository clientRepository;

    private final CustomModelMapper modelMapper;

    private final EmployeeService employeeService;

    public ClientServiceImpl(EmployeeService employeeService, CustomModelMapper modelMapper, ClientRepository clientRepository, @Lazy ProjectService projectService) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.projectService = projectService;
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
        if (name == null) {
            throw new BadRequestException("Please provide a valid name data.");
        }
        return clientRepository.findClientByName(name);
    }

    @Override
    public Client findClientById(Integer id) {

        if (id == null) {
            throw new BadRequestException("Please provide a valid data.");
        }
        return clientRepository.findClientById(id);
    }

    @Override
    public boolean existsClient(Client client) {
        return clientRepository.existsClientByName(client.getName());
    }

    @Override
    public List<Client> findUndeletedClients(String searchQuery, Pageable pageable) {
        if (searchQuery == null) {
            return clientRepository.findAllByDeletedFalse(pageable).getContent();
        }
        return clientRepository.filterAllUndeleted(searchQuery, pageable).getContent();
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
    public void delete(Integer id) {
        Client clientForDelete = findClientById(id);

        if (clientForDelete == null) {
            throw new BadRequestException("Please provide a valid data.");
        }

        clientForDelete.getProjects().forEach(project -> projectService.delete(project.getId()));

        clientRepository.deleteClientById(clientForDelete.getId());
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}
