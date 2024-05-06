package com.rsl.springsecexamplev2.service;

import com.rsl.springsecexamplev2.dto.UserReqDto;
import com.rsl.springsecexamplev2.dto.ExceptionDTO;
import com.rsl.springsecexamplev2.dto.LoginReq;
import com.rsl.springsecexamplev2.dto.LoginRes;
import com.rsl.springsecexamplev2.entity.User;
import com.rsl.springsecexamplev2.enums.Role;
import com.rsl.springsecexamplev2.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public ResponseEntity<?> authenticate(LoginReq loginReq){
        log.info("authenticate method started by: {}", loginReq.getLoginDetail());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginReq.getLoginDetail(),
                            loginReq.getPassword()));
            log.info("authentication details: {}", authentication);
            String username = authentication.getName();
            User user = new User(username,"");
            String token = jwtService.createToken(user);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            LoginRes loginRes = new LoginRes(username,token);
            log.info("user: {} logged in",  user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRes);

        }catch (BadCredentialsException e){
            ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(),"Invalid username or password");
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
        }catch (Exception e){
            ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
        }
    }

    public void register(UserReqDto userReqDto){
        log.info("user register method started by: {}", userReqDto.getEmail());
        User user = new User();
        user.setUsername(userReqDto.getUsername());
        user.setEmail(userReqDto.getEmail());
        user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("saved new user");
    }

}
