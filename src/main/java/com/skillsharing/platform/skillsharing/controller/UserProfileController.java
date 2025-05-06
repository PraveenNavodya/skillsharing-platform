package com.skillsharing.platform.skillsharing.controller;

import com.skillsharing.platform.skillsharing.model.UserProfile;
import com.skillsharing.platform.skillsharing.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/me")
public ResponseEntity<?> getCurrentUser(HttpServletRequest request) throws ExecutionException, InterruptedException {
    String uid = (String) request.getAttribute("uid");
    if (uid == null) {
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
    }
    UserProfile profile = userProfileService.getUserProfile(uid);
    if (profile != null) {
        return ResponseEntity.ok(profile);
    } else {
        // 🔥 FIX: return empty JSON object so frontend .json() won't fail
        return ResponseEntity.ok().body("{}");
    }
}


@PostMapping("/create")
public ResponseEntity<String> createUserProfile(@RequestBody UserProfile userProfile, HttpServletRequest request) {
    try {
        String uid = (String) request.getAttribute("uid");

        if (uid == null) {
            System.out.println("❌ UID missing in request");
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        System.out.println("✅ UID: " + uid);
        System.out.println("📦 Received profile: " + userProfile.getFullName() + ", " + userProfile.getEmail());

        userProfile.setId(uid);
        String result = userProfileService.createUserProfile(uid, userProfile);
        return ResponseEntity.ok(result);

    } catch (Exception e) {
        e.printStackTrace(); // 🔥 Print full error to terminal
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
    }
}



    @PutMapping("/update")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfile userProfile, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        String uid = (String) request.getAttribute("uid");
        userProfile.setId(uid);
        return ResponseEntity.ok(userProfileService.updateUserProfile(uid, userProfile));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserProfile(HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        String uid = (String) request.getAttribute("uid");
        return ResponseEntity.ok(userProfileService.deleteUserProfile(uid));
    }
}
