package com.newroutes.models.post;

import com.newroutes.enums.post.ReactionType;
import com.newroutes.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostReaction extends BaseModel {

    private UUID postId;

    private UUID userId;

    private ReactionType reaction;

}
