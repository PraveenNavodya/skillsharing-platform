package com.skillsharing.platform.skillsharing.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.skillsharing.platform.skillsharing.model.Notification;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    public String createNotification(String uid, Notification notification) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String notifId = UUID.randomUUID().toString();  // Generate random ID
        notification.setId(notifId);
        notification.setUserId(uid);

        ApiFuture<WriteResult> future = dbFirestore
            .collection("users")
            .document(uid)
            .collection("notifications")
            .document(notifId)
            .set(notification);

        future.get();
        return "🔔 Notification created for UID: " + uid;
    }

    public Notification getNotification(String uid, String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore
            .collection("users")
            .document(uid)
            .collection("notifications")
            .document(id);

        DocumentSnapshot snapshot = docRef.get().get();
        return snapshot.exists() ? snapshot.toObject(Notification.class) : null;
    }

    public String deleteNotification(String uid, String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = dbFirestore
            .collection("users")
            .document(uid)
            .collection("notifications")
            .document(id)
            .delete();
        future.get();
        return "🗑️ Notification deleted for UID: " + uid;
    }
}
