package com.newroutes.models.mappers.post;

import com.newroutes.entities.post.PostEntity;
import com.newroutes.exceptions.post.PostNotFoundException;
import com.newroutes.repositories.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceMap {

    private final PostRepository postRepository;

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

}
