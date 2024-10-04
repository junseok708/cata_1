package com.sparta.schedulerprojact.entity;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Schedul {


    private Long id;
    private String task;
    private String name;
    private String password;
    private String email;
    private String createdAt;
    private String updatedAt;

    public Schedul(SchedulerRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.name = requestDto.getName();
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
    }


}
