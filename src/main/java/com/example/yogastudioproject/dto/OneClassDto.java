package com.example.yogastudioproject.dto;

import com.example.yogastudioproject.domain.model.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OneClassDto implements Serializable {
    private long oneClassId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String dateOfClass;
    private AppUserDto teacher;
}
