package com.sparta.schedulerprojact.controller;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import com.sparta.schedulerprojact.dto.SchedulerResponseDto;
import com.sparta.schedulerprojact.entity.Schedul;
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
        Schedul schedul = new Schedul(requestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String schedulSql = "insert into schedul(task,name,password,email) values(?,?,?,?)";

        jdbctemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(schedulSql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedul.getTask());
            ps.setString(2, schedul.getName());
            ps.setString(3, schedul.getPassword());
            ps.setString(4, schedul.getEmail());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedul.setId(id);

        SchedulerResponseDto schedulerResonseDto = new SchedulerResponseDto(schedul);

        return schedulerResonseDto;
    }

    @GetMapping("/task")
    public List<SchedulerResponseDto> getTasks() {

        String sql = "select * from schedul";
        return jdbctemplate.query(sql, new RowMapper<SchedulerResponseDto>() {
            @Override
            public SchedulerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                Long id = rs.getLong("id");
                String task = rs.getString("task");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                Timestamp updatedAt = rs.getTimestamp("updatedAt");

                return new SchedulerResponseDto(id, task, name, email, password, createdAt, updatedAt);
            }
        });
    }

    @PutMapping("/task/{id}")
    public Long updateTask(@PathVariable Long id, @PathVariable String password, @RequestBody SchedulerRequestDto requestDto) {
        Schedul schedul = schedulFindById(id);

        if (schedul != null) {
            String sql = "update schedul set task = ?,name = ? where id = ? and password = ?";
            jdbctemplate.update(sql, requestDto.getTask(), requestDto.getName(), password, id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 없습니다");
        }
    }

    @DeleteMapping("/task/{id}")
    public Long deleteTask(@PathVariable Long id, @PathVariable String password) {
        Schedul schedul = schedulFindById(id);

        if (schedul != null) {
            String sql = "delete from schedul where id = ? and password = ?";
            jdbctemplate.update(sql, id, password);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 없습니다");
        }
    }

    private Schedul schedulFindById(Long id) {
        String sql = "select * from schedul where id = ?";
        return jdbctemplate.query(sql, ResultSet -> {
            if (ResultSet.next()) {
                Schedul schedul = new Schedul();
                schedul.setTask(ResultSet.getString("task"));
                //schedul.setCreatedAt(ResultSet.getTimestamp("createdAt"));
                //schedul.setUpdatedAt(ResultSet.getTimestamp("updatedAt"));
                return schedul;
            } else {
                return null;
            }
        }, id);
    }

}
