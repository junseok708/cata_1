package com.sparta.schedulerprojact.dto;

import com.sparta.schedulerprojact.entity.Schedul;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchedulerRequestDto {

    private Long id;
    private String task;
    private String createdAt;
    private String updatedAt;
    private String name;
    private String password;
    private String email;


    public SchedulerRequestDto(Schedul schedul) {
        this.id = schedul.getId();
        this.task = schedul.getTask();
        this.name = schedul.getName();
        this.password = schedul.getPassword();
        this.email = schedul.getEmail();
        this.createdAt = String.valueOf(schedul.getCreatedAt());
        this.updatedAt = String.valueOf(schedul.getUpdatedAt());

    }
}
