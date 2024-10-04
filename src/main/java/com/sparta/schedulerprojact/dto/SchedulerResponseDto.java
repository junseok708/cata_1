package com.sparta.schedulerprojact.dto;

import com.sparta.schedulerprojact.entity.Schedul;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class SchedulerResponseDto {
    private Long id;
    private String task;
    private String createdAt;
    private String updatedAt;
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
        this.createdAt = String.valueOf(createdAt);
        this.updatedAt = String.valueOf(updatedAt);
    }
}
