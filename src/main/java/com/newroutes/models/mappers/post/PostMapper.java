package com.newroutes.models.mappers.post;

import com.newroutes.entities.post.PostEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = PostServiceMap.class)
public interface PostMapper extends BaseMapper<PostEntity, Post> {

    void mergePostData(Post source, @MappingTarget Post target);
}
