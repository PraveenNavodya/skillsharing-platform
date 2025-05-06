package com.skillsharing.platform.skillsharing.controller;

import com.skillsharing.platform.skillsharing.model.Notification;
import com.skillsharing.platform.skillsharing.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody Notification notification, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        String uid = (String) request.getAttribute("uid");
        notification.setUserId(uid);
        return ResponseEntity.ok(notificationService.createNotification(uid, notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable String id, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        String uid = (String) request.getAttribute("uid");
        Notification result = notificationService.getNotification(uid, id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable String id, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        String uid = (String) request.getAttribute("uid");
        return ResponseEntity.ok(notificationService.deleteNotification(uid, id));
    }
}
