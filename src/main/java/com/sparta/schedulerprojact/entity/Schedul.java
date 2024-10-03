package com.sparta.schedulerprojact.entity;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Schedul {

    private Long id;
    private String task;
    private String name;
    private String password;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Schedul(SchedulerRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.createdAt = requestDto.getCreatedAt();
        this.updatedAt = requestDto.getUpdatedAt();
    }

    public void update(SchedulerRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.updatedAt = requestDto.getUpdatedAt();
    }

}
