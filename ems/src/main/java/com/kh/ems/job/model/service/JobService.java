package com.kh.ems.job.model.service;

import com.kh.ems.job.model.dto.Job;

import java.util.List;

public interface JobService {
    List<Job> selectAll();

    int getCount();
}
