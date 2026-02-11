package com.kh.ems.employee.model.service;

import com.kh.ems.employee.model.dto.Employee;
import com.kh.ems.employee.model.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public int getCount() {
        return employeeMapper.selectCount();
    }
}
