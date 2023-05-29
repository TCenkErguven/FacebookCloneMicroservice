package com.cenk.manager;

import com.cenk.dto.request.GetMyProfileRequestDto;
import com.cenk.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(
        name= "user-service",
        url = "http://localhost:9093/api/v1/userprofile"
        )
public interface IUserProfileManager {
    @PostMapping("/getotherprofile")
    public ResponseEntity<UserProfile> getOtherProfile(@RequestBody @Valid GetMyProfileRequestDto dto);
}
