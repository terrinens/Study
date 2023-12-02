package com.example.jwt.entity;

import com.example.jwt.role.UserRole;
import com.example.jwt.role.convert.UserRoleConvert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authority")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor @AllArgsConstructor
public class Authority {
    @Id
    @Column(name = "authority_name", length = 50)
    @Convert(converter = UserRoleConvert.class)
    @Enumerated(EnumType.STRING)
    private UserRole authorityName;
}
