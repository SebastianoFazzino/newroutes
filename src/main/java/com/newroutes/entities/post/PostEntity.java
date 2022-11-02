package com.newroutes.entities.post;

import com.newroutes.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "post", indexes= {
        @Index(name = "userIndex", columnList="userId")
})
public class PostEntity extends BaseEntity {

    @Type(type="uuid-char")
    private UUID userId;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String message;

    @Lob
    private String file;

    @ElementCollection
    @CollectionTable(name = "tags")
    @Column(name = "tag")
    private List<String> tags;

    @Column(columnDefinition = "TEXT")
    private String reactionsCounter;

    private Integer totalReactions;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "post",
            cascade = CascadeType.ALL
    )
    private List<PostReactionEntity> reactions;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "post",
            cascade = CascadeType.ALL
    )
    private List<CommentEntity> comments;

}
