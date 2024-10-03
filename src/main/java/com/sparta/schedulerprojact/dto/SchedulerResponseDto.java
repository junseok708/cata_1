package com.sparta.schedulerprojact.dto;

import com.sparta.schedulerprojact.entity.Schedul;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class SchedulerResponseDto {
    private Long id;
    private String task;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String name;
    private String password;
    private String email;


    public SchedulerResponseDto(Schedul schedul){
        this.id = schedul.getId();
        this.task = schedul.getTask();
        this.name = schedul.getName();
        this.password = schedul.getPassword();
        this.email = schedul.getEmail();
    }

    public SchedulerResponseDto(Long id, String task, String name, String email, String password, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.task = task;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
