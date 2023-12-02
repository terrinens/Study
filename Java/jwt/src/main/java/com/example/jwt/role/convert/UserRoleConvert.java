package com.example.jwt.role.convert;

import com.example.jwt.role.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

import java.util.NoSuchElementException;

@Convert
public class UserRoleConvert implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        return attribute.toString();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.toString().toUpperCase().equals(dbData)) {
                return userRole;
            }
        }
        throw new NoSuchElementException("설정된 유저 권한 값이 아닙니다.");
    }
}
