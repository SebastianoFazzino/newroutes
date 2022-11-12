package com.newroutes.models.post;

import com.newroutes.entities.post.CommentEntity;
import com.newroutes.models.BaseModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseModel {

    private String author;

    private UUID userId;

    private String title;

    private String message;

    @ToString.Exclude
    private String file;

    private List<String> tags;

    private Integer totalReactions = 0;

    private List<CommentEntity> comments = new ArrayList<>();

}
