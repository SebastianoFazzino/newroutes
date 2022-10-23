package com.newroutes.entities.user;

import com.newroutes.entities.BaseEntity;
import com.newroutes.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role", uniqueConstraints = {
        @UniqueConstraint(name = "uniqueRole", columnNames = {"user_id","role"})}
)
public class UserRoleEntity extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
