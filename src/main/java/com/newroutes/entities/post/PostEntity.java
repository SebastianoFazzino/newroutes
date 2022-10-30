package com.newroutes.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newroutes.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "post")
public class PostEntity extends BaseEntity {

    @Type(type="uuid-char")
    private UUID userId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Integer likeCount;

    @Lob
    private String file;

    @ElementCollection
    @CollectionTable(name = "tags")
    @Column(name = "tag")
    private List<String> tags;

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "post"
    )
    private List<PostReaction> reactions;
}
