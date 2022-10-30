package com.newroutes.models.post;

import com.newroutes.enums.post.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReactionCounterResponse {

    private Post post;

    private HashMap<ReactionType,Integer> reactionsMap;

    private Integer totalReactions;

    public void instantiateMap() {
        this.reactionsMap = new HashMap<>();
        for ( var type : ReactionType.values() ) {
            reactionsMap.put(type,0);
        }
    }
}
