package com.rsl.springsecexamplev2.controller;


import com.rsl.springsecexamplev2.dto.UserReqDto;
import com.rsl.springsecexamplev2.dto.LoginReq;
import com.rsl.springsecexamplev2.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @ResponseBody
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq)  {
        return authService.authenticate(loginReq);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserReqDto userReqDto)  {
        authService.register(userReqDto);
    }
}
