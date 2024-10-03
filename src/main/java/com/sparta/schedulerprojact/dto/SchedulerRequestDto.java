package com.sparta.schedulerprojact.dto;

import com.sparta.schedulerprojact.entity.Schedul;
import lombok.Getter;

import java.security.Timestamp;

@Getter
public class SchedulerRequestDto {

    private Long id;
    private String task;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String name;
    private String password;
    private String email;


    public SchedulerRequestDto(Schedul schedul) {
        this.id = schedul.getId();
        this.task = schedul.getTask();
        this.createdAt = schedul.getCreatedAt();
        this.updatedAt = schedul.getUpdatedAt();
        this.name = schedul.getName();
        this.password = schedul.getPassword();
        this.email = schedul.getEmail();

    }
}
