package com.entity.test.entity.entity_abstract;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;



@Getter @SuperBuilder(toBuilder = true)
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BoardAbstract {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id = 1L;
    private String title;
    private String con;
    private long view;
}