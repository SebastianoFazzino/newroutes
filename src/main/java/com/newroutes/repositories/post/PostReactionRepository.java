package com.newroutes.repositories.post;

import com.newroutes.entities.post.PostReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, UUID> {

    boolean existsByPostAndUserId(UUID postId, UUID userId);
}
