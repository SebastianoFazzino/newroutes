package com.newroutes.models.post;

import com.newroutes.enums.post.ReactionType;
import com.newroutes.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
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

    private List<String> tags;

    private HashMap<ReactionType,Integer> reactionsCounter;

    private Integer totalReactions = 0;

    private List<PostReaction> reactions;

}
