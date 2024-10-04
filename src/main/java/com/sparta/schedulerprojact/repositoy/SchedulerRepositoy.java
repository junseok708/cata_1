package com.sparta.schedulerprojact.repositoy;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import com.sparta.schedulerprojact.dto.SchedulerResponseDto;
import com.sparta.schedulerprojact.entity.Schedul;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

public class SchedulerRepositoy {
    private final JdbcTemplate jdbctemplate;

    public SchedulerRepositoy(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    public Schedul save(Schedul schedul) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into schedul(task,name,password,email) values(?,?,?,?)";

        jdbctemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedul.getTask());
            ps.setString(2, schedul.getName());
            ps.setString(3, schedul.getPassword());
            ps.setString(4, schedul.getEmail());

            return ps;
        });

        return schedul;
    }


    public List<SchedulerResponseDto> findAll() {

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

    public List<SchedulerResponseDto> findOne(Long id){
        String sql = "select * from schedul where id=?";
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
        },id);

    }


    public void update(Long id, SchedulerRequestDto requestDto) {
        String sql = "update schedul set task = ?,name = ? where id = ? and password = ?";
        jdbctemplate.update(sql, requestDto.getTask(), requestDto.getName(), id, requestDto.getPassword());
    }

    public void delete(Long id,SchedulerRequestDto requestDto ) {
        String sql = "delete from schedul where id = ? and password = ?";
        jdbctemplate.update(sql, id,requestDto.getPassword());
    }

    public Schedul schedulFindById(Long id) {
        String sql = "select * from schedul where id = ?";
        return jdbctemplate.query(sql, ResultSet -> {
            if (ResultSet.next()) {
                Schedul schedul = new Schedul();
                schedul.setTask(ResultSet.getString("task"));
                return schedul;
            } else {
                return null;
            }
        }, id);
    }


}
