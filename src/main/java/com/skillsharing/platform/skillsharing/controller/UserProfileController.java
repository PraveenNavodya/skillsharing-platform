package com.skillsharing.platform.skillsharing.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.skillsharing.platform.skillsharing.model.UserProfile;
import org.springframework.web.bind.annotation.*;
import com.google.cloud.firestore.WriteResult;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @PostMapping("/create")
    public String createUserProfile(@RequestBody UserProfile userProfile) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference users = dbFirestore.collection("users");
        DocumentReference userDoc = users.document(userProfile.getId());
        ApiFuture<WriteResult> future = userDoc.set(userProfile);
        future.get(); // wait until complete
        return "✅ User Profile created successfully!";
    }
}
