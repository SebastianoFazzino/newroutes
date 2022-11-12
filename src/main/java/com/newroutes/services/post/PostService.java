package com.newroutes.services.post;


import com.newroutes.entities.post.CommentEntity;
import com.newroutes.entities.post.PostEntity;
import com.newroutes.exceptions.post.CommentNotFoundException;
import com.newroutes.exceptions.post.PostNotFoundException;
import com.newroutes.models.mappers.post.CommentMapper;
import com.newroutes.models.mappers.post.PostMapper;
import com.newroutes.models.post.Comment;
import com.newroutes.models.post.Post;
import com.newroutes.repositories.post.CommentRepository;
import com.newroutes.repositories.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper mapper;
    private final PostRepository repository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    // ********************************************************************
    // Post CRUD region

    public Post getById(UUID id) {

        log.info("Getting post by id {}", id);
        PostEntity post = repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(
                        String.format("Post not found by id '%s'", id)));

        return mapper.convertToDto(post);
    }

    public List<Post> getAll() {

        log.info("Getting all posts");
        return repository.findAll()
                .stream().map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Post save(Post post) {

        log.info("Saving post {}", post);
        post.setUserId(UUID.randomUUID());
        return mapper.convertToDto(repository.save(mapper.convertToEntity(post)));
    }

    @Transactional
    public void delete(UUID postId) {

        Post post = this.getById(postId);

        log.info("Deleting post {}", post);
        repository.delete(mapper.convertToEntity(post));
    }

    public Post update(UUID id, Post post) {

        log.info("Requested update post {}", id);
        Optional<PostEntity> optExistingPost = repository.findById(id);

        if ( optExistingPost.isPresent() ) {

            Post existingPost = mapper.convertToDto(optExistingPost.get());

            log.info("Updating existing post {}", existingPost);
            mapper.mergePostData(post, existingPost);
            return this.save(existingPost);
        }
        return this.save(post);
    }

    public Post addReaction(UUID postId) {

        log.info("Adding a reaction to post {}", postId);

        Post post = this.getById(postId);
        post.setTotalReactions(post.getTotalReactions() != null ? post.getTotalReactions() +1 : 1);
        return this.save(post);
    }

    public List<Post> getByUserId(UUID userId) {

        log.info("Getting all posts for User {}", userId);
        return repository.findAllByUserId(userId)
                .stream().map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    // ********************************************************************
    // Comments region

    public Comment save(Comment comment) {

        log.info("Saving Comment {}", comment);
        return commentMapper.convertToDto(
                commentRepository.save(commentMapper.convertToEntity(comment)));
    }

    public Comment getCommentById(UUID commentId) {

        log.info("Getting Comment by id {}", commentId);

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(
                        String.format("Comment not found by id '%s'", commentId)));

        return commentMapper.convertToDto(comment);
    }

    public List<Comment> getAllForPost(UUID postId) {

        log.info("Getting all Comments for Post {}", postId);
        return commentRepository.getAllByPost_Id(postId)
                .stream().map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Comment addToPost(Comment comment) {

        log.info("Adding new Comment '{}' to Post {}", comment.getMessage(), comment.getPostId());
        return this.save(comment);
    }

    public Comment updateComment(Comment comment) {

        log.info("Requested update Comment {}", comment.getId());
        Optional<CommentEntity> optComment = commentRepository.findById(comment.getId());

        if (optComment.isPresent()) {

            Comment existingComment = commentMapper.convertToDto(optComment.get());

            log.info("Updating existing Comment {}", existingComment);
            commentMapper.mergePostData(comment, existingComment);
            return this.save(existingComment);
        }
        return this.save(comment);
    }

    @Transactional
    public void deleteComment(UUID commentId) {

        Comment comment = this.getCommentById(commentId);

        log.info("Deleting comment '{}'", comment);
        commentRepository.delete(commentMapper.convertToEntity(comment));
    }

}
