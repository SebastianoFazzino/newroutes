package com.newroutes.models.post;

import com.newroutes.entities.post.CommentEntity;
import com.newroutes.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseModel {

    private UUID postId;

    private UUID userId;

    private String message;

}
