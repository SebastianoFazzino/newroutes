package com.newroutes.controllers.post;

import com.newroutes.enums.post.ReactionType;
import com.newroutes.models.post.Post;
import com.newroutes.models.post.PostReactionCounterResponse;
import com.newroutes.services.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1/post"})
public class PostController {

    private final PostService service;

    // ********************************************************************
    // Post CRUD region

    @GetMapping("/id/{id}")
    public ResponseEntity<Post> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getById() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/new")
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity.ok(service.save(post));
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<Post>> getByUserId(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<Post> update(@RequestBody Post post) {
        return ResponseEntity.ok(service.update(post));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody Post post) {
        service.delete(post);
        ResponseEntity.ok();
    }

    // ********************************************************************
    // Post reaction region

    @PostMapping("/react/{postId}")
    public ResponseEntity<PostReactionCounterResponse> addReaction(
            @PathVariable("postId") UUID postId,
            @RequestParam UUID userId,
            @RequestParam ReactionType reactionType
    ) {
        return ResponseEntity.ok(service.react(postId, userId, reactionType));
    }

    @DeleteMapping("/delete-reaction/{postId}")
    public ResponseEntity<PostReactionCounterResponse> addReaction(
            @PathVariable("postId") UUID postId,
            @RequestParam UUID userId
    ) {
        return ResponseEntity.ok(service.deleteReaction(postId, userId));
    }

}
