package com.rsl.springsecexamplev2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRes {
    private String username;
    private String token;
}
