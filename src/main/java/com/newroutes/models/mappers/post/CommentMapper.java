package com.newroutes.models.mappers.post;

import com.newroutes.entities.post.CommentEntity;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.post.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = PostServiceMap.class)
public interface CommentMapper extends BaseMapper<CommentEntity, Comment> {

    @Override
    @Mapping(source = "postId", target = "post")
    CommentEntity convertToEntity(Comment dto);

    @Override
    @Mapping(source = "post", target = "postId")
    Comment convertToDto(CommentEntity entity);

    void mergePostData(Comment source, @MappingTarget Comment target);
}
