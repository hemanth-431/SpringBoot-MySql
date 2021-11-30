package com.database.database.JPARepository;

import com.database.database.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeById(Long id);
    List<Employee> findEmployeeByEmployeeCode(String employeeCode);
}