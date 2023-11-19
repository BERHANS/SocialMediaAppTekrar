package com.berhan.controller;

import com.berhan.dto.request.LoginAuthRequestDto;
import com.berhan.dto.request.RegisterAuthRequestDto;
import com.berhan.dto.request.UpdateAuthRequestDto;
import com.berhan.dto.response.RegisterAuthResponseDto;
import com.berhan.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.berhan.constants.RestApi.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService service;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterAuthResponseDto> register(@RequestBody RegisterAuthRequestDto dto){
        return ResponseEntity.ok(service.register(dto));
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activationStatus(String activationCode){
        return ResponseEntity.ok(service.activationStatus(activationCode));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginAuthRequestDto dto){
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UpdateAuthRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @PostMapping(DELETEBYID)
    public ResponseEntity<Boolean> delete(String token){
        return ResponseEntity.ok(service.delete(token));
    }


}
