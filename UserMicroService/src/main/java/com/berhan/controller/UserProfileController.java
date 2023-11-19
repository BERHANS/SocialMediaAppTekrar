package com.berhan.controller;

import com.berhan.dto.request.CreateUserProfileRequestDto;
import com.berhan.dto.request.UpdateUserProfileRequestDto;
import com.berhan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.berhan.constants.RestApi.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserProfileController {

    private final UserProfileService service;

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activationUserProfile(@RequestBody Long authId){
        return ResponseEntity.ok(service.activationUserProfile(authId));
    }

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUserProfile(@RequestBody CreateUserProfileRequestDto dto){
        return ResponseEntity.ok(service.createUserProfile(dto));
    }

    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> updateUserProfile(UpdateUserProfileRequestDto dto){
        return ResponseEntity.ok(service.updateUserProfile(dto));
    }

    @PostMapping(DELETEBYID)
    public ResponseEntity<Boolean> deleteUserProfile(@RequestBody Long authId){
        return ResponseEntity.ok(service.deleteUserProfile(authId));
    }

}
