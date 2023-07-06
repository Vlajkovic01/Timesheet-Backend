package com.example.timesheet.controller;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.model.dto.employee.EmployeeDTO;
import com.example.timesheet.model.dto.employee.request.EmployeeAddRequestDTO;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CustomModelMapper modelMapper;

    public EmployeeController(CustomModelMapper modelMapper, EmployeeService employeeService) {
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeAddRequestDTO> updateEmployee(@Validated @RequestBody EmployeeAddRequestDTO employeeAddRequestDTO) {

        try {
            Employee updatedEmployee = employeeService.updateEmployee(employeeAddRequestDTO);
            EmployeeAddRequestDTO employeeDTO = modelMapper.map(updatedEmployee, EmployeeAddRequestDTO.class);

            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEmployee(@RequestBody String email) {

        try {
            employeeService.delete(email);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<EmployeeDTO>> getEmployees(Pageable pageable) {

        Page<Employee> employees = employeeService.findAll(pageable);

        Page<EmployeeDTO> employeesDTO = modelMapper.mapAll(employees, EmployeeDTO.class);
        return new ResponseEntity<>(employeesDTO, HttpStatus.OK);
    }
}
