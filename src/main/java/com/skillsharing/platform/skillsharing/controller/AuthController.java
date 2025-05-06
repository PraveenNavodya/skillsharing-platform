package com.skillsharing.platform.skillsharing.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/auth")
    public ResponseEntity<String> authenticateUser(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid"); // set by FirebaseTokenFilter

        if (uid != null) {
            return ResponseEntity.ok("Authenticated as " + uid);
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
    }
}
