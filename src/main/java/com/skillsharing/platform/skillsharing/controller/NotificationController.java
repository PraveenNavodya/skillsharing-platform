package com.skillsharing.platform.skillsharing.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.skillsharing.platform.skillsharing.model.Notification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final Firestore dbFirestore = FirestoreClient.getFirestore();

    @PostMapping("/create")
    public String createNotification(@RequestBody Notification notification) throws ExecutionException, InterruptedException {
        CollectionReference notifications = dbFirestore.collection("notifications");
        notification.setTimestamp(System.currentTimeMillis()); // set current time
        DocumentReference docRef = notifications.document(notification.getId());
        ApiFuture<WriteResult> future = docRef.set(notification);
        future.get();
        return "✅ Notification created successfully!";
    }

    @GetMapping("/getByUser/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable String userId) throws ExecutionException, InterruptedException {
        List<Notification> notificationsList = new ArrayList<>();
        CollectionReference notifications = dbFirestore.collection("notifications");
        Query query = notifications.whereEqualTo("userId", userId);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            notificationsList.add(document.toObject(Notification.class));
        }
        return notificationsList;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNotification(@PathVariable String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection("notifications").document(id);
        ApiFuture<WriteResult> future = docRef.delete();
        future.get();
        return "🗑️ Notification deleted successfully!";
    }
}
