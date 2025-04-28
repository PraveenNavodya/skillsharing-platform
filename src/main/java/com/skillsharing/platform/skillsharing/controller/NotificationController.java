package com.skillsharing.platform.skillsharing.controller;

import com.skillsharing.platform.skillsharing.model.Notification;
import com.skillsharing.platform.skillsharing.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public String createNotification(@RequestBody Notification notification) throws ExecutionException, InterruptedException {
        return notificationService.createNotification(notification);
    }

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable String id) throws ExecutionException, InterruptedException {
        return notificationService.getNotification(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNotification(@PathVariable String id) throws ExecutionException, InterruptedException {
        return notificationService.deleteNotification(id);
    }
}
