package com.kh.ems.department.model.service;

import com.kh.ems.department.model.dto.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> selectAll();
}
