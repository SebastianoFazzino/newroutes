package com.newroutes.models.post;

import com.newroutes.entities.post.CommentEntity;
import com.newroutes.enums.post.ReactionType;
import com.newroutes.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
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

    private String file;

    private List<String> tags;

    private HashMap<ReactionType,Integer> reactionsCounter = this.instantiateMap();

    private Integer totalReactions = 0;

    private List<PostReaction> reactions = new ArrayList<>();

    private List<CommentEntity> comments = new ArrayList<>();


    private HashMap<ReactionType,Integer> instantiateMap() {
        this.reactionsCounter = new HashMap<>();
        for ( var type : ReactionType.values() ) {
            reactionsCounter.put(type,0);
        }
        return reactionsCounter;
    }

}
