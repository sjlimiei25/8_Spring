package com.kh.ems.employee.model.mapper;

import com.kh.ems.employee.model.dto.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<Employee> selectAll();

    int selectCount();
}
