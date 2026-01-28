package com.database.database.controller;
import com.database.database.JPARepository.EmployeeRepo;
import com.database.database.model.Employee;
import com.database.database.model.CursorPageResponse;
import com.database.database.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeResource {
    private final EmployeeService employeeService;
    private final EmployeeRepo employeeRepo;

    @GetMapping("/all")
    public ResponseEntity<Page<Employee>> getAllEmployees (@RequestParam(name = "offset") Integer offset, @RequestParam(name = "limit") Integer limit) {
        Page<Employee> employees = employeeService.findAllEmployees(offset, limit);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value="/allByKeySet")
    public CursorPageResponse<Employee> getAllEmployeesByKeySet (@RequestParam(name = "cursor", defaultValue = "0") Long cursor, @RequestParam(name = "limit") Integer limit) {
        CursorPageResponse<Employee> employees = employeeService.findAllEmployeesByKeySet(cursor, limit);
        return employees;
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/find/empid")
    public ResponseEntity<List<Employee>> getEmployeeByEmployeeCode (@RequestParam String empid) {

        return new ResponseEntity<List<Employee>>(employeeRepo.findEmployeeByEmployeeCode(empid), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}