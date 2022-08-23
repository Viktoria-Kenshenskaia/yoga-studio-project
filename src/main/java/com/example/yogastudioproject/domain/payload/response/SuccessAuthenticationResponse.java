package com.example.yogastudioproject.domain.payload.response;

import com.example.yogastudioproject.dto.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessAuthenticationResponse {
    private String jwt;
    private AppUserDto userDto;
}
