package com.entity.test.entity;

import com.entity.test.entity.entity_abstract.BoardAbstract;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity(name = "Test_Board_2")
public class TestBoard_2 extends BoardAbstract {
}
