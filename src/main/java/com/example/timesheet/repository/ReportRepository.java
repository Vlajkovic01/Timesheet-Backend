package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query(nativeQuery = true, value = "SELECT IF(SUM(hours) >= ?, true, false) as sumOfHours " +
            "FROM report " +
            "WHERE date = ? AND employee_id = ?")
    Integer isSumOfHoursGreaterThanHoursPerDay(Double hoursPerDay, LocalDate date, Integer employeeId);
}
