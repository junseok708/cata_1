package com.sparta.schedulerprojact.controller;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import com.sparta.schedulerprojact.dto.SchedulerResponseDto;
import com.sparta.schedulerprojact.entity.Schedul;
import com.sparta.schedulerprojact.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class SchedulerController {

    private final JdbcTemplate jdbcTemplate;

    public SchedulerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public SchedulerResponseDto createSchedul(@RequestBody SchedulerRequestDto requestDto) {
        Schedul schedul = new Schedul(requestDto);
        User user = new User(requestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String SchedulSql = "INSERT INTO scheduler (schedulerName, task) VALUES (left(UUID(),8), ?)";
        String userSql = "INSERT INTO user (name, password) VALUES (?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement schedulPreparedStatement =
                    con.prepareStatement(SchedulSql, PreparedStatement.RETURN_GENERATED_KEYS);

            schedulPreparedStatement.setString(1, schedul.getTask());

            return schedulPreparedStatement;
        }, keyHolder);

        jdbcTemplate.update(con -> {
            PreparedStatement userPreparedStatement =
                    con.prepareStatement(userSql, PreparedStatement.RETURN_GENERATED_KEYS);
            userPreparedStatement.setString(1, user.getName());
            userPreparedStatement.setString(2, user.getPassword());
            return userPreparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedul.setId(id);

        SchedulerResponseDto schedulerResponseDto = new SchedulerResponseDto();
        return schedulerResponseDto;
    }

    @GetMapping("/{name}&{updateAt}")
    public List<SchedulerResponseDto> getSchedulers(@PathVariable String name, Timestamp updatedAt) {
        User user = new User();
        Schedul scheduler = new Schedul();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "select s.id,u.name, s.task,s.createdAt,s.updatedAt from schedul s join user u on s.serialNumber = u.serialNumber " +
                "where u.name =? and date_formet(s.updatedAt,'%Y-%m-%d') = date_formet(?,'%Y-%m-%d')" +
                "order by s.updatedAt desc)";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, scheduler.getUpdatedAt().toString());
            return preparedStatement;
        }, keyHolder);

        return jdbcTemplate.query(sql, new RowMapper<SchedulerResponseDto>() {
            @Override
            public SchedulerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String task = rs.getString("task");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                Timestamp updatedAt = rs.getTimestamp("updatedAt");

                return new SchedulerResponseDto(id, name, task, createdAt, updatedAt);
            }
        });
    }

    @GetMapping("/{serialNumber}")
    public List<SchedulerResponseDto> getSchedulerByserialNumber(@RequestParam String serialNumber) {
        Schedul schedul = new Schedul();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "select s.id,u.name, s.task,s.createdAt,s.updatedAt from schedul s join user u on s.serialNumber = u.serialNumber " +
                "where s.serialNumber = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, schedul.getSerialNumber());
            return preparedStatement;
        }, keyHolder);

        return jdbcTemplate.query(sql, new RowMapper<SchedulerResponseDto>() {
            @Override
            public SchedulerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String task = rs.getString("task");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                Timestamp updatedAt = rs.getTimestamp("updatedAt");

                return new SchedulerResponseDto(id, name, task, createdAt, updatedAt);
            }
        });
    }

    @PutMapping("/{serialNumber}")
    public String updateScheduler(@PathVariable String serialNumber, @RequestBody SchedulerRequestDto requestDto) {
        Schedul schedul = findBySchedulSerialNumber(serialNumber);
        User user = findByUserschedul(serialNumber);
        if (schedul != null && user != null) {
            String schedulSql = "update schedul set task = ? where serialNumber = ?";
            String userSql = "update user set name = ? where serialNumber = ?";
            jdbcTemplate.update(schedulSql, schedul.getTask(), serialNumber);
            jdbcTemplate.update(userSql, user.getName(), serialNumber);
            return schedul.getSerialNumber();
        } else {
            throw new IllegalArgumentException("선택한 일정은 존제하지 않습니다");
        }

    }

    @DeleteMapping("/{serialNumber}")
    public String deleteScheduler(@PathVariable String serialNumber) {
        Schedul schedul = findBySchedulSerialNumber(serialNumber);
        User user = findByUserschedul(serialNumber);

        if (schedul != null && user != null) {
            String schedulSql = "delete from schedul where serialNumber = ?";
            String userSql = "delete from user where serialNumber = ?";
            jdbcTemplate.update(schedulSql, serialNumber);
            jdbcTemplate.update(userSql, serialNumber);

            return serialNumber;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존제하지 않습니다");
        }

    }

    private User findByUserschedul(String serialNumber) {
        String sql = "select * from User where serialNumber = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                User user = new User();
                user.setSerialNumber(resultSet.getString("serialNumber"));

                return user;

            } else {
                return null;
            }
        }, serialNumber);
    }

    private Schedul findBySchedulSerialNumber(String serialNumber) {
        String sql = "select * from schedul where serialNumber = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedul schedul = new Schedul();
                schedul.setSerialNumber(resultSet.getString("serialNumber"));

                return schedul;

            } else {
                return null;
            }
        }, serialNumber);
    }
}
