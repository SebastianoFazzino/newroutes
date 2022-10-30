package com.newroutes.models.mappers.post;

import com.newroutes.entities.post.PostReactionEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.post.PostReaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PostServiceMap.class)
public interface PostReactionMapper extends BaseMapper<PostReactionEntity, PostReaction> {

    @Override
    @Mapping(source = "postId", target = "post")
    PostReactionEntity convertToEntity(PostReaction dto);

    @Override
    @Mapping(source = "post", target = "postId")
    PostReaction convertToDto(PostReactionEntity entity);

}
