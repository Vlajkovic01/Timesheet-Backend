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

    @Query(nativeQuery = true, value = "SELECT * FROM work_log " +
            "WHERE (:#{#searchParameters.client.id} IS NULL OR work_log.client_id = :#{#searchParameters.client.id}) " +
            "AND (:#{#searchParameters.project.id} IS NULL OR work_log.project_id = :#{#searchParameters.project.id}) " +
            "AND (:#{#searchParameters.category.id} IS NULL OR work_log.category_id = :#{#searchParameters.category.id}) " +
            "AND (:#{#searchParameters.employee.id} IS NULL OR work_log.employee_id = :#{#searchParameters.employee.id}) " +
            "AND (:#{#searchParameters.quickDateWeek} IS NULL OR WEEK(work_log.date) = WEEK(:#{#searchParameters.quickDateWeek})) " +
            "AND (:#{#searchParameters.quickDateMonth} IS NULL OR MONTH(work_log.date) =  MONTH(:#{#searchParameters.quickDateMonth})) " +
            "AND (:#{#searchParameters.startDate} IS NULL OR work_log.date > :#{#searchParameters.startDate}) " +
            "AND (:#{#searchParameters.endDate} IS NULL OR work_log.date < :#{#searchParameters.endDate}) ")
    Page<WorkLog> filterAll(@Param("searchParameters") ReportRequestDTO searchParameters, Pageable pageable);
}
