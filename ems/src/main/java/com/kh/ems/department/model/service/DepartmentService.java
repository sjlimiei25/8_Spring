package com.kh.ems.department.model.service;

import com.kh.ems.department.model.dto.DepartmentDTO;
import com.kh.ems.department.model.vo.Department;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> selectAll();

    int getCount();
}
