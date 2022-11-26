package com.newroutes.entities.user;

import com.newroutes.entities.BaseEntity;
import com.newroutes.enums.user.LogOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log")
public class LogEntity extends BaseEntity {

    @ManyToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private UserEntity user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogOperationType type;

    @Column(columnDefinition = "TEXT")
    private String logMessage;

}
