package com.newroutes.models.mappers.post;

import com.newroutes.entities.post.PostReactionEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.post.PostReaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PostServiceMap.class)
public interface PostReactionMapper extends BaseMapper<PostReactionEntity, PostReaction> {

}
