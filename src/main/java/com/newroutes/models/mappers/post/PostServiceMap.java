package com.newroutes.models.mappers.post;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newroutes.entities.post.PostReactionEntity;
import com.newroutes.enums.post.ReactionType;
import com.newroutes.exceptions.post.PostReactionNotFoundException;
import com.newroutes.repositories.post.PostReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceMap {

    private final PostReactionRepository repository;
    private final Gson gson = new Gson();

    public PostReactionEntity map(UUID id) {
        if (id == null) {
            return null;
        }
        return repository.findById(id)
                .orElseThrow(() ->
                        new PostReactionNotFoundException(
                                String.format("Post Reaction not found by id '%s'", id)) );
    }

    public UUID map(PostReactionEntity reaction) {
        if (reaction == null) {
            return null;
        }
        return reaction.getId();
    }

    public String map(HashMap<ReactionType,Integer> reactionsCounter) {
        return gson.toJson(reactionsCounter);
    }

    public HashMap<ReactionType,Integer> map(String reactionsCounter) {
        if ( reactionsCounter == null || reactionsCounter.equals("") ) {
            return new HashMap<>();
        }
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        return gson.fromJson( reactionsCounter, type );
    }
}
