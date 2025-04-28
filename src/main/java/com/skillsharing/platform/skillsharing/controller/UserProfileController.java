package com.skillsharing.platform.skillsharing.controller;

import com.skillsharing.platform.skillsharing.model.UserProfile;
import com.skillsharing.platform.skillsharing.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/create")
    public String createUserProfile(@RequestBody UserProfile userProfile) throws ExecutionException, InterruptedException {
        return userProfileService.createUserProfile(userProfile);
    }

    @GetMapping("/{id}")
    public UserProfile getUserProfile(@PathVariable String id) throws ExecutionException, InterruptedException {
        return userProfileService.getUserProfile(id);
    }

    @PutMapping("/update/{id}")
    public String updateUserProfile(@PathVariable String id, @RequestBody UserProfile userProfile) throws ExecutionException, InterruptedException {
        return userProfileService.updateUserProfile(id, userProfile);
    }
    

    @DeleteMapping("/delete/{id}")
    public String deleteUserProfile(@PathVariable String id) throws ExecutionException, InterruptedException {
        return userProfileService.deleteUserProfile(id);
    }
}
