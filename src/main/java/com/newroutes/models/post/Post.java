package com.newroutes.models.post;

import com.newroutes.entities.post.PostReaction;
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
public class Post extends BaseModel {

    private UUID userId;

    private String title;

    private String message;

    private String file;

    private Integer likeCount = 0;

    public List<String> tags;

    private List<PostReaction> reactions;
}
