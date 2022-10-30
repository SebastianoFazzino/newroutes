package com.newroutes.entities.post;

import com.newroutes.entities.BaseEntity;
import com.newroutes.enums.post.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "post_reaction", uniqueConstraints = {
        @UniqueConstraint(name = "uniqueUser", columnNames = {"userId","post_id"})
})
public class PostReaction extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Type(type="uuid-char")
    private PostEntity post;

    @Type(type="uuid-char")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private ReactionType reaction;

}
