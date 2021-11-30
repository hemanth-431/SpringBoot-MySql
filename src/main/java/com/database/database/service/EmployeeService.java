package com.database.database.service;

import com.database.database.Exception.UserNotFoundException;
import com.database.database.JPARepository.EmployeeRepo;
import com.database.database.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

//    public Employee findEmployeeByEmpId(String employeeCode) {
//        return employeeRepo.findEmployeeByEmpId(employeeCode)
//                .orElseThrow(() -> new UserNotFoundException("User by id " + employeeCode + " was not found"));
//    }

    public void deleteEmployee(Long id){
        employeeRepo.deleteEmployeeById(id);
    }
}