package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findFirstByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Employee employee SET employee.deleted = true, employee.status = 'INACTIVE' WHERE employee.email = :email")
    void deleteEmployeeByEmail(@Param("email") String email);

    Page<Employee> findAllByDeletedFalse(Pageable pageable);
}
