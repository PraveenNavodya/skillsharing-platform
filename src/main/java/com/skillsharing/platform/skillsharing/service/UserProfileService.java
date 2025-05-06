package com.skillsharing.platform.skillsharing.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.skillsharing.platform.skillsharing.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserProfileService {

    private static final String COLLECTION_NAME = "users";

    // Now accepts UID instead of using userProfile.getId()
    public String createUserProfile(String uid, UserProfile userProfile) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        userProfile.setId(uid);  // Ensure ID is set to UID
        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_NAME)
                .document(uid)
                .set(userProfile);
        return "✅ User Profile created at: " + future.get().getUpdateTime();
    }

    public UserProfile getUserProfile(String uid) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(uid);
        DocumentSnapshot snapshot = docRef.get().get();
        return snapshot.exists() ? snapshot.toObject(UserProfile.class) : null;
    }

    public String updateUserProfile(String uid, UserProfile userProfile) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        userProfile.setId(uid);  // Update the ID to match UID
        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_NAME)
                .document(uid)
                .set(userProfile);
        return "✅ User Profile updated at: " + future.get().getUpdateTime();
    }

    public String deleteUserProfile(String uid) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_NAME).document(uid).delete();
        future.get();
        return "✅ User Profile deleted successfully!";
    }
}
