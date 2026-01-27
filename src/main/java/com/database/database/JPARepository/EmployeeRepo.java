package com.database.database.JPARepository;

import com.database.database.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeById(Long id);
    List<Employee> findEmployeeByEmployeeCode(String employeeCode);

    @Query("""
    SELECT p FROM Employee p
    WHERE (:cursor IS NULL OR p.id > :cursor)
    ORDER BY p.id ASC
""")
    List<Employee> fetchNextPage(@Param("cursor") Long cursor, Pageable pageable);
}