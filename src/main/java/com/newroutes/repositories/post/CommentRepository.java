package com.newroutes.repositories.post;

import com.newroutes.entities.post.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

    List<CommentEntity> getAllByPost_Id(UUID postId);
}
