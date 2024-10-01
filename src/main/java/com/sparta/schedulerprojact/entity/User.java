package com.sparta.schedulerprojact.entity;

import com.sparta.schedulerprojact.dto.SchedulerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Long id;
    private String serialNumber;
    private String name;
    private String password;

    public User(SchedulerRequestDto requestDto) {
        this.serialNumber = requestDto.getSerialNumber();
        this.name = requestDto.getName();
        this.password = requestDto.getPassword();
    }

}
