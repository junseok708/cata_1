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
    private String serialNumber;
    private String task;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Schedul(SchedulerRequestDto requestDto) {
        this.serialNumber = requestDto.getSerialNumber();
        this.task = requestDto.getTask();
        this.createdAt = requestDto.getCreatedAt();
        this.updatedAt = requestDto.getUpdatedAt();
    }

}
