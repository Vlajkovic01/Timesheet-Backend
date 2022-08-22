package com.example.timesheet.controller;

import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.client.response.ClientResponseDTO;
import com.example.timesheet.model.dto.search.SearchRequestDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.ClientService;
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
    public ResponseEntity<List<ClientDTO>> getClients(@RequestBody(required = false) SearchRequestDTO searchRequestDTO,
                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {

        List<Client> clients = clientService.findClients(searchRequestDTO, pageNo, pageSize);

        List<ClientDTO> clientsDTO = modelMapper.mapAll(clients, ClientDTO.class);

        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }
}
