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

    /**
     * Creates a new SkillPost document in Firestore.
     *
     * @param skillPost The skill post data from the request body.
     * @return A success message including the generated document ID.
     */
    @PostMapping("/create")
    public String createSkillPost(@RequestBody SkillPost skillPost) throws ExecutionException, InterruptedException {
        CollectionReference posts = dbFirestore.collection(COLLECTION_NAME);
        skillPost.setTimestamp(System.currentTimeMillis());
        DocumentReference docRef = posts.document(); // Firestore auto-generates the ID
        skillPost.setId(docRef.getId()); // Save the generated ID to the SkillPost object
        ApiFuture<WriteResult> future = docRef.set(skillPost);
        future.get();
        return "✅ Skill post created successfully! ID: " + docRef.getId();
    }

    /**
     * Retrieves a SkillPost document by its ID.
     *
     * @param id The document ID of the SkillPost.
     * @return The SkillPost object or null if not found.
     */
    @GetMapping("/{id}")
    public SkillPost getSkillPost(@PathVariable String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(SkillPost.class);
        } else {
            return null; // You can replace this with a custom exception if needed
        }
    }

    /**
     * Retrieves all SkillPosts created by a specific user.
     *
     * @param userId The user ID.
     * @return A list of SkillPosts belonging to the user.
     */
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

    /**
     * Updates an existing SkillPost by its ID.
     *
     * @param id                The ID of the SkillPost to update.
     * @param updatedSkillPost The updated data for the SkillPost.
     * @return A success or failure message.
     */
    @PutMapping("/update/{id}")
    public String updateSkillPost(@PathVariable String id, @RequestBody SkillPost updatedSkillPost) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            updatedSkillPost.setId(id); // Ensure the ID remains consistent
            ApiFuture<WriteResult> updateFuture = docRef.set(updatedSkillPost);
            updateFuture.get();
            return "✅ Skill post updated successfully!";
        } else {
            return "⚠️ Skill post with ID " + id + " not found.";
        }
    }

    /**
     * Deletes a SkillPost by its ID.
     *
     * @param id The ID of the SkillPost to delete.
     * @return A success or failure message.
     */
    @DeleteMapping("/delete/{id}")
    public String deleteSkillPost(@PathVariable String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<WriteResult> writeResult = docRef.delete();
        writeResult.get();
        return "✅ Skill post with ID " + id + " deleted successfully.";
    }
}
