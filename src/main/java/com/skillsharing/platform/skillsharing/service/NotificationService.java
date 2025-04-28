package com.skillsharing.platform.skillsharing.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.skillsharing.platform.skillsharing.model.Notification;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    public String createNotification(Notification notification) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference notifications = dbFirestore.collection("notifications");
        DocumentReference notificationDoc = notifications.document(notification.getId());
        ApiFuture<WriteResult> future = notificationDoc.set(notification);
        future.get(); // Wait until completed ✅
        return "✅ Notification created successfully!";
    }

    public Notification getNotification(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("notifications").document(id);
        DocumentSnapshot documentSnapshot = documentReference.get().get();
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Notification.class);
        }
        return null;
    }

    public String deleteNotification(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("notifications").document(id);
        ApiFuture<WriteResult> future = documentReference.delete();
        future.get(); // Wait until completed ✅
        return "🗑️ Notification deleted successfully!";
    }
}
