package com.kh.ems.employee.model.service;

import com.kh.ems.employee.model.dto.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> selectAll();
}
