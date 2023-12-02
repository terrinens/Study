package com.entity.test.entity;

import com.entity.test.entity.entity_abstract.BoardAbstract;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter @SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Test_Board_1")
public class TestBoard_1 extends BoardAbstract {
}


