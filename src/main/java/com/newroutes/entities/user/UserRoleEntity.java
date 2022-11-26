package com.newroutes.entities.user;

import com.newroutes.entities.BaseEntity;
import com.newroutes.enums.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_role", uniqueConstraints = {
        @UniqueConstraint(name = "uniqueRole", columnNames = {"user_id","role"})}
)
public class UserRoleEntity extends BaseEntity {

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
    private Role role;
}
