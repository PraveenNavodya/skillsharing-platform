package com.skillsharing.platform.skillsharing.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.skillsharing.platform.skillsharing.model.SkillPost;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/posts")
public class SkillPostController {

    private final Firestore dbFirestore = FirestoreClient.getFirestore();
    private static final String COLLECTION_NAME = "skillPosts";

    @PostMapping("/create")
    public String createSkillPost(@RequestBody SkillPost skillPost) throws ExecutionException, InterruptedException {
        CollectionReference posts = dbFirestore.collection("skillPosts");
        skillPost.setTimestamp(System.currentTimeMillis());
        DocumentReference docRef = posts.document(skillPost.getId());
        ApiFuture<WriteResult> future = docRef.set(skillPost);
        future.get();
        return "✅ Skill post created successfully!";
    }

    @GetMapping("/{id}")
    public SkillPost getSkillPost(@PathVariable String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(SkillPost.class);
        } else {
            return null; // Or throw a custom exception
        }
    }

    @GetMapping("/user/{userId}")
    public List<SkillPost> getSkillPostsByUser(@PathVariable String userId) throws ExecutionException, InterruptedException {
        List<SkillPost> postsList = new ArrayList<>();
        CollectionReference posts = dbFirestore.collection(COLLECTION_NAME);
        Query query = posts.whereEqualTo("userId", userId);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            postsList.add(document.toObject(SkillPost.class));
        }
        return postsList;
    }

    @PutMapping("/update/{id}")
    public String updateSkillPost(@PathVariable String id, @RequestBody SkillPost updatedSkillPost) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            // Ensure the ID remains the same
            updatedSkillPost.setId(id);
            ApiFuture<WriteResult> updateFuture = docRef.set(updatedSkillPost);
            updateFuture.get();
            return "✅ Skill post updated successfully!";
        } else {
            return "⚠️ Skill post with ID " + id + " not found.";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSkillPost(@PathVariable String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<WriteResult> future = docRef.delete();
        future.get();
        return "🗑️ Skill post deleted successfully!";
    }

    // TODO: Implement media upload functionality (will likely involve Firebase Storage)
    @PostMapping("/upload/{postId}")
    public String uploadMedia(@PathVariable String postId, @RequestBody List<String> mediaUrls) {
        // This is a placeholder - actual implementation will involve Firebase Storage
        // and updating the skill post document with the media URLs.
        // Consider handling file uploads and generating URLs.
        return "⚠️ Media upload functionality not yet implemented.";
    }
}