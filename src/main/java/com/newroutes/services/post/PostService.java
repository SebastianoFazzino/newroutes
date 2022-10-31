package com.newroutes.services.post;


import com.newroutes.entities.post.PostEntity;
import com.newroutes.enums.post.ReactionType;
import com.newroutes.exceptions.post.PostNotFoundException;
import com.newroutes.models.mappers.post.PostMapper;
import com.newroutes.models.mappers.post.PostReactionMapper;
import com.newroutes.models.post.Post;
import com.newroutes.models.post.PostReaction;
import com.newroutes.repositories.post.PostReactionRepository;
import com.newroutes.repositories.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
    private final PostReactionMapper reactionMapper;
    private final PostReactionRepository reactionRepository;

    // ********************************************************************
    // Post CRUD region

    public Post getById(UUID id) {

        log.info("Getting post by id {}", id);
        Optional<PostEntity> optExistingPost = repository.findById(id);

        if ( optExistingPost.isPresent() ) {
            return mapper.convertToDto(optExistingPost.get());
        }
        throw new PostNotFoundException(String.format("Post not found by id '%s'", id));
    }

    public List<Post> getAll() {

        log.info("Getting all posts");
        return repository.findAll()
                .stream().map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Post save(Post post) {

        log.info("Saving post {}", post);
        return mapper.convertToDto(repository.save(mapper.convertToEntity(post)));
    }

    @Transactional
    public void delete(UUID postId) {

        Post post = this.getById(postId);

        log.info("Deleting post {}", post);
        repository.delete(mapper.convertToEntity(post));
    }

    public Post update(Post post) {

        log.info("Requested update post {}", post.getId());
        Optional<PostEntity> optExistingPost = repository.findById(post.getId());

        if ( optExistingPost.isPresent() ) {

            Post existingPost = mapper.convertToDto(optExistingPost.get());

            log.info("Updating existing post {}", existingPost);
            mapper.mergePostData(post, existingPost);
            return this.save(existingPost);
        }
        return this.save(post);
    }

    public List<Post> getByUserId(UUID userId) {

        log.info("Getting all posts for User {}", userId);
        return repository.findAllByUserId(userId)
                .stream().map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    // ********************************************************************
    // Post reaction region

    private PostReaction save(PostReaction postReaction) {
        log.info("Saving PostReaction {}", postReaction);
        return reactionMapper.convertToDto(reactionRepository.save(reactionMapper.convertToEntity(postReaction)));
    }

    public Post react(UUID postId, UUID userId, ReactionType reactionType) {

        log.info("Adding new Post Reaction of type {} on Post '{}' for User '{}'", reactionType, postId, userId);
        PostReaction reaction = new PostReaction();
        reaction.setPostId(postId);
        reaction.setUserId(userId);
        reaction.setReaction(reactionType);

        this.save(reaction);
        return this.updateReactions(postId, reactionType, false);
    }

    @Transactional
    public Post deleteReaction(UUID postId, UUID userId) {

        ReactionType reactionType = reactionRepository.getByPost_IdAndUserId(postId, userId).getReaction();

        log.info("Deleting Post Reaction of type {} on Post '{}' for User '{}'", reactionType, postId, userId);
        reactionRepository.deleteByPost_IdAndUserId(postId, userId);
        return this.updateReactions(postId, reactionType, true);
    }

    public Post updateReactions(UUID postId, ReactionType reactionType, boolean subtract) {

        log.info("Updating reaction counter for Post {}", postId);

        Post post = this.getById(postId);
        HashMap<ReactionType,Integer> reactionsCounter = post.getReactionsCounter();

        // update reaction counter and total reactions
        int totalReactions = subtract ? post.getTotalReactions() -1 : post.getTotalReactions() +1;
        int newCounterForReactionType = subtract ? reactionsCounter.get(reactionType) -1 : reactionsCounter.get(reactionType) +1;
        reactionsCounter.put(reactionType, newCounterForReactionType);

        post.setReactionsCounter(reactionsCounter);
        post.setTotalReactions(totalReactions);
        return this.save(post);
    }

}
