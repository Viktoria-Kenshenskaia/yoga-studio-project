package com.example.yogastudioproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OneClassDto implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final String dateOfClass;
    private final Long teacherId;
}
