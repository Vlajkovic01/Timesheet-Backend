package com.example.timesheet.repository;

import com.example.timesheet.model.dto.search.ReportRequestDTO;
import com.example.timesheet.model.entity.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Integer> {

    @Query(value = "SELECT worklog FROM WorkLog worklog " +
            "WHERE (worklog.client.id = :#{#searchParameters.client.id}) " +
            "AND (worklog.project.id = :#{#searchParameters.project.id}) " +
            "AND (worklog.category.id = :#{#searchParameters.category.id}) " +
            "AND (worklog.employee.id = :#{#searchParameters.employee.id}) " +
            "AND (worklog.date > :#{#searchParameters.startDate}) " +
            "AND (worklog.date < :#{#searchParameters.endDate})")
    Page<WorkLog> generateReport(@Param("searchParameters") ReportRequestDTO searchParameters, Pageable pageable);

    @Query(value = "SELECT worklog FROM WorkLog worklog " +
            "WHERE (worklog.client.id = :#{#searchParameters.client.id}) " +
            "AND (worklog.project.id = :#{#searchParameters.project.id}) " +
            "AND (worklog.category.id = :#{#searchParameters.category.id}) " +
            "AND (worklog.employee.id = :#{#searchParameters.employee.id}) " +
            "AND (WEEK(worklog.date) = WEEK(:#{#searchParameters.quickDateWeek}))")
    Page<WorkLog> generateReportWithQuickDateWeek(@Param("searchParameters") ReportRequestDTO searchParameters, Pageable pageable);

    @Query(value = "SELECT worklog FROM WorkLog worklog " +
            "WHERE (worklog.client.id = :#{#searchParameters.client.id}) " +
            "AND (worklog.project.id = :#{#searchParameters.project.id}) " +
            "AND (worklog.category.id = :#{#searchParameters.category.id}) " +
            "AND (worklog.employee.id = :#{#searchParameters.employee.id}) " +
            "AND (MONTH(worklog.date) = MONTH(:#{#searchParameters.quickDateMonth}))")
    Page<WorkLog> generateReportWithQuickDateMonth(@Param("searchParameters") ReportRequestDTO searchParameters, Pageable pageable);
}
