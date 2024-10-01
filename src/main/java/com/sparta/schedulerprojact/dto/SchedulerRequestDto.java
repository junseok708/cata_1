package com.sparta.schedulerprojact.dto;

import lombok.Getter;

import java.security.Timestamp;

@Getter
public class SchedulerRequestDto {

    private String serialNumber;
    private String task;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String name;
    private String password;


}
