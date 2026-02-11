package com.kh.ems.employee.model.service;

import com.kh.ems.employee.model.vo.Employee;
import com.kh.ems.employee.model.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> selectAll();

    int getCount();
}
