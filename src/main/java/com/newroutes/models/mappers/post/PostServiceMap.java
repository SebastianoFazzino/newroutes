package com.newroutes.models.mappers.post;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newroutes.entities.post.PostEntity;
import com.newroutes.entities.post.PostReactionEntity;
import com.newroutes.enums.post.ReactionType;
import com.newroutes.exceptions.post.PostNotFoundException;
import com.newroutes.exceptions.post.PostReactionNotFoundException;
import com.newroutes.repositories.post.PostReactionRepository;
import com.newroutes.repositories.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceMap {

    private final PostRepository postRepository;
    private final PostReactionRepository postReactionRepository;
    private final Gson gson = new Gson();

    public PostReactionEntity mapPostReaction(UUID id) {
        if (id == null) {
            return null;
        }
        return postReactionRepository.findById(id)
                .orElseThrow(() ->
                        new PostReactionNotFoundException(
                                String.format("Post Reaction not found by id '%s'", id)) );
    }

    public UUID mapPostReaction(PostReactionEntity reaction) {
        if (reaction == null) {
            return null;
        }
        return reaction.getId();
    }

    public PostEntity mapPost(UUID id) {
        if (id == null) {
            return null;
        }
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new PostNotFoundException(
                                String.format("Post not found by id '%s'", id)) );
    }

    public UUID mapPost(PostEntity post) {
        if (post == null) {
            return null;
        }
        return post.getId();
    }

    public String map(HashMap<ReactionType,Integer> reactionsCounter) {
        return gson.toJson(reactionsCounter);
    }

    public HashMap<ReactionType,Integer> map(String reactionsCounter) {
        if ( reactionsCounter == null || reactionsCounter.equals("") ) {
            return new HashMap<>();
        }
        Type type = new TypeToken<HashMap<ReactionType,Integer>>(){}.getType();
        return gson.fromJson( reactionsCounter, type );
    }
}
