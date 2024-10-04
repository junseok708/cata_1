package com.sparta.schedulerprojact.controller;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import com.sparta.schedulerprojact.dto.SchedulerResponseDto;
import com.sparta.schedulerprojact.entity.Schedul;
import com.sparta.schedulerprojact.service.SchedulerService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SchedulerController {

    private final JdbcTemplate jdbctemplate;

    public SchedulerController(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }


    @PostMapping("/task")
    public SchedulerResponseDto addTask(@RequestBody SchedulerRequestDto requestDto) {
        SchedulerService schedulerService = new SchedulerService(jdbctemplate);
        return schedulerService.createSchedul(requestDto);

    }

    @GetMapping("/task")
    public List<SchedulerResponseDto> getTasks() {
        SchedulerService schedulerService = new SchedulerService(jdbctemplate);
        return schedulerService.getScheduls();

    }

    @GetMapping("/task/{id}")
    public List<SchedulerResponseDto> getTask(@PathVariable("id") Long id) {
        SchedulerService schedulerService = new SchedulerService(jdbctemplate);
        return schedulerService.getSchedulOne(id);
    }

    @PutMapping("/task/{id}")
    public Long updateTask(@PathVariable("id") Long id, @RequestBody SchedulerRequestDto requestDto) {
        SchedulerService schedulerService = new SchedulerService(jdbctemplate);
        return schedulerService.updateTask(id, requestDto);

    }

    @DeleteMapping("/task/{id}")
    public Long deleteTask(@PathVariable("id") Long id, @RequestBody SchedulerRequestDto requestDto) {
        SchedulerService schedulerService = new SchedulerService(jdbctemplate);
        return schedulerService.deleteTsak(id,requestDto);

    }


}
