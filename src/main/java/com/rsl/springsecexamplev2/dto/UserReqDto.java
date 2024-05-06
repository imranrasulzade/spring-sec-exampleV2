package com.rsl.springsecexamplev2.dto;

import lombok.Data;

@Data
public class UserReqDto {
    private String username;
    private String email;
    private String password;
}
