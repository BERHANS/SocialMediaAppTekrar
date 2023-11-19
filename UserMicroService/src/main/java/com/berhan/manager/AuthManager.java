package com.berhan.manager;

import com.berhan.dto.request.UpdateAuthRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:9090/api/v1/auth",name = "userProfile-auth",dismiss404 = true)
public interface AuthManager {

    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UpdateAuthRequestDto dto);
}
