package com.berhan.manager;

import com.berhan.dto.request.CreateUserProfileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.berhan.constants.RestApi.ACTIVATESTATUS;
import static com.berhan.constants.RestApi.DELETEBYID;


@FeignClient(url = "http://localhost:9091/api/v1/user",dismiss404 = true,name = "auth-user")
public interface UserProfileManager {

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activationUserProfile(@RequestBody Long authId);

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUserProfile(@RequestBody CreateUserProfileRequestDto dto);
    @PostMapping(DELETEBYID)
    public ResponseEntity<Boolean> deleteUserProfile(@RequestBody Long authId);
}
