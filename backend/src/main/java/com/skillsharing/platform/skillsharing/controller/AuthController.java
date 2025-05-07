package com.skillsharing.platform.skillsharing.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) throws FirebaseAuthException {
        // Verify Firebase ID token
        FirebaseToken decodedToken = FirebaseAuth.getInstance()
                .verifyIdToken(loginRequest.getIdToken());
        
        String uid = decodedToken.getUid();
        // You can create your own JWT or return the Firebase token
        return "your-custom-token-or-firebase-token";
    }

    public static class LoginRequest {
        private String idToken;

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}