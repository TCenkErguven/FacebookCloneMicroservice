package com.cenk.controller;

import com.cenk.dto.request.UserProfileDto;
import com.cenk.repository.entity.UserProfile;
import com.cenk.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.cenk.constants.EndPointList.*;

@RestController
@RequestMapping("/elastic/userprofile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping(SAVE)
    public ResponseEntity<Void> save(@RequestBody UserProfileDto dto){
        userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping(FIND_ALL)
    public  ResponseEntity<Iterable<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
    @GetMapping()
    public ResponseEntity<Page<UserProfile>> findAll(int currentPage, int size, String sortParameter, String sortDirection){
        return ResponseEntity.ok(userProfileService.findAll(currentPage,size,sortParameter,sortDirection));
    }


}

