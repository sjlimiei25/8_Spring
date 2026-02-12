package com.kh.ems.chat.tools;

import com.kh.ems.department.model.dto.DepartmentDTO;
import com.kh.ems.department.model.service.DepartmentService;
import com.kh.ems.employee.model.dto.EmployeeDTO;
import com.kh.ems.employee.model.service.EmployeeService;
import com.kh.ems.job.model.service.JobService;
import com.kh.ems.job.model.vo.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatTools {

    private static final Logger log = LoggerFactory.getLogger(ChatTools.class);

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final JobService jobService;

    public ChatTools(EmployeeService employeeService, DepartmentService departmentService, JobService jobService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.jobService = jobService;
    }

    @Tool(description = "전체 직원 목록 또는 특정 직원의 정보를 조회합니다.")
    public List<EmployeeDTO> employeeInfo(
            @ToolParam(description = "검색할 직원의 이름 (필수 아님)") String name) {
        log.info("직원 정보 조회 요청 - 이름: {}", name);
        List<EmployeeDTO> list = employeeService.selectAll();
        if (name != null && !name.isEmpty()) {
            return list.stream()
                    .filter(e -> e.getEmpName().contains(name))
                    .toList();
        }
        return list;
    }

    @Tool(description = "전체 부서 목록 정보를 조회합니다.")
    public List<DepartmentDTO> departmentInfo(
            @ToolParam(description = "검색할 부서의 명칭 (필수 아님)") String title) {
        log.info("부서 정보 조회 요청 - 명칭: {}", title);
        List<DepartmentDTO> list = departmentService.selectAll();
        if (title != null && !title.isEmpty()) {
            return list.stream()
                    .filter(d -> d.getDeptTitle().contains(title))
                    .toList();
        }
        return list;
    }

    @Tool(description = "전체 직급 목록 정보를 조회합니다.")
    public List<Job> jobInfo(
            @ToolParam(description = "검색할 직급의 명칭 (필수 아님)") String name) {
        log.info("직급 정보 조회 요청 - 명칭: {}", name);
        List<Job> list = jobService.selectAll();
        if (name != null && !name.isEmpty()) {
            return list.stream()
                    .filter(j -> j.getJobName().contains(name))
                    .toList();
        }
        return list;
    }
}
