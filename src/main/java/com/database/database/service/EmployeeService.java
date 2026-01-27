package com.database.database.service;

import com.database.database.Exception.UserNotFoundException;
import com.database.database.JPARepository.EmployeeRepo;
import com.database.database.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * Offset Pagination
     * Here it runs two queries
     * 1.It queries from 0th to current offset then ignore them. then gets the rest employees from that offset based on pageSize.
     * 2. Other query to get count by id.
     * @param offset from which page to start.
     * @param pageSize size of page
     */
    public Page<Employee> findAllEmployees(Integer offset, Integer pageSize) {
        return employeeRepo.findAll(PageRequest.of(offset,pageSize));
    }

    /**
     * Use keySet Pagination, efficient.
     * One query, Gets employees after the cursor/id, get based on pageSize.
     * @param cursor from which employee to start.
     */
    public CursorPageResponse<Employee> findAllEmployeesByKeySet(Long cursor, Integer pagesize){
        Pageable pageable = PageRequest.of(0,pagesize);
        List<Employee> employees=employeeRepo.fetchNextPage(cursor, pageable);
        boolean islast = employees.size()==pagesize;
        Long nextCursor=employees.get(employees.size()-1).getId();
        return CursorPageResponse.<Employee>builder().data(employees).isLast(islast).pageSize(pagesize).nextCursor(nextCursor).build();
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteEmployee(Long id){
        employeeRepo.deleteEmployeeById(id);
    }
}