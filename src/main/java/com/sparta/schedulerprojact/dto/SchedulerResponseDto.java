package com.sparta.schedulerprojact.dto;

import java.sql.Timestamp;

public class SchedulerResponseDto {
    private Long id;
    private String name;
    private String task;
    Timestamp createdAt;
    Timestamp updatedAt;


    public SchedulerResponseDto() {

    }

    public SchedulerResponseDto(Long id, String name, String task, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.task = task;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
