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

    public String createUserProfile(UserProfile userProfile) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_NAME)
                .document(userProfile.getId())
                .set(userProfile);
        return "✅ User Profile created at: " + future.get().getUpdateTime();
    }

    public UserProfile getUserProfile(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot document = documentReference.get().get();
        if (document.exists()) {
            return document.toObject(UserProfile.class);
        } else {
            return null;
        }
    }

    public String updateUserProfile(UserProfile userProfile) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_NAME)
                .document(userProfile.getId())
                .set(userProfile); // set() will overwrite
        return "✅ User Profile updated at: " + future.get().getUpdateTime();
    }

    public String deleteUserProfile(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = dbFirestore.collection(COLLECTION_NAME).document(id).delete();
        future.get(); 
        return "✅ User Profile deleted successfully!";
    }
    
}
