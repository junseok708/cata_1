package com.sparta.schedulerprojact.service;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import com.sparta.schedulerprojact.dto.SchedulerResponseDto;
import com.sparta.schedulerprojact.entity.Schedul;
import com.sparta.schedulerprojact.repositoy.SchedulerRepositoy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

public class SchedulerService {

    private final JdbcTemplate jdbctemplate;

    public SchedulerService(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    public SchedulerResponseDto createSchedul(SchedulerRequestDto requestDto) {

        Schedul schedul = new Schedul(requestDto);
        SchedulerRepositoy schedulerRepositoy = new SchedulerRepositoy(jdbctemplate);

        Schedul saveSchedul = schedulerRepositoy.save(schedul);

        SchedulerResponseDto schedulerResonseDto = new SchedulerResponseDto(schedul);

        return schedulerResonseDto;

    }

    public List<SchedulerResponseDto> getScheduls() {
        SchedulerRepositoy schedulerRepositoy = new SchedulerRepositoy(jdbctemplate);
        return schedulerRepositoy.findAll();

    }
    public List<SchedulerResponseDto> getSchedulOne(Long id) {
        SchedulerRepositoy schedulerRepositoy = new SchedulerRepositoy(jdbctemplate);
        return schedulerRepositoy.findOne(id);

    }

    public Long updateTask(Long id, String password, SchedulerRequestDto requestDto) {
        SchedulerRepositoy schedulerRepositoy = new SchedulerRepositoy(jdbctemplate);
        Schedul schedul = schedulerRepositoy.schedulFindById(id);

        if (schedul != null) {
            schedulerRepositoy.update(id, password, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 없습니다");
        }
    }

    public Long deleteTsak(Long id, String password) {
        SchedulerRepositoy schedulerRepositoy = new SchedulerRepositoy(jdbctemplate);
        Schedul schedul = schedulerRepositoy.schedulFindById(id);

        if (schedul != null) {
            schedulerRepositoy.delete(id, password);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 없습니다");
        }
    }


}
