package com.example.timesheet.controller;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.client.response.ClientResponseDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.ClientService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/client")
public class ClientController {

    private final CustomModelMapper modelMapper;
    private final ClientService clientService;

    public ClientController(ClientService clientService, CustomModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClientResponseDTO> addNewClient(@Validated @RequestBody ClientDTO clientDTO,
                                                          Authentication authentication) {

        Client createdClient = clientService.addNewClient(clientDTO, authentication);

        if (createdClient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientResponseDTO clientResponseDTO = modelMapper.map(createdClient, ClientResponseDTO.class);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients(@RequestParam(required = false, name = "name") String searchQuery,
                                                      Pageable pageable) {

        List<Client> clients = clientService.findClients(searchQuery, pageable);

        List<ClientDTO> clientsDTO = modelMapper.mapAll(clients, ClientDTO.class);
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClientResponseDTO> updateClient(@Validated @RequestBody ClientDTO clientDTO,
                                                          Authentication authentication) {

        try {
            Client updatedClient = clientService.updateClient(clientDTO, authentication);
            ClientResponseDTO clientResponseDTO = modelMapper.map(updatedClient, ClientResponseDTO.class);

            return new ResponseEntity<>(clientResponseDTO, HttpStatus.OK);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteClient(@RequestBody Integer id) {
        try {
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
