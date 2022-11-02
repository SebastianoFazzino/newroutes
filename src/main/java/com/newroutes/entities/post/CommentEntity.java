package com.newroutes.entities.post;


import com.newroutes.entities.BaseEntity;
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
@Table(name = "comment", indexes= {
        @Index(name = "userIndex", columnList="userId"),
        @Index(name = "postIndex", columnList="post_id")
})
public class CommentEntity extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Type(type="uuid-char")
    private PostEntity post;

    @NotNull
    @Type(type="uuid-char")
    private UUID userId;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String message;

}
