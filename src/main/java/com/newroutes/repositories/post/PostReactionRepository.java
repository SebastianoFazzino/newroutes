package com.newroutes.repositories.post;

import com.newroutes.entities.post.PostReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReactionEntity, UUID> {

    List<PostReactionEntity> getAllByPost_Id(UUID postId);

    void deleteByPost_IdAndUserId(UUID postId, UUID UserId);

}
