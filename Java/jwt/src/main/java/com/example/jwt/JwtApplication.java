package com.example.jwt;

import com.example.jwt.entity.Authority;
import com.example.jwt.role.UserRole;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class JwtApplication {

    @Autowired
    private AuthorityService authorityService;

    public static void main(String[] args) throws NoSuchMethodException {
        SpringApplication.run(JwtApplication.class, args);
    }

    @PostConstruct
    public void init() {
        UserRole[] userRoles = UserRole.values();
        for (UserRole userRole : userRoles) {
            authorityService.save(Authority.builder()
                    .authorityName(userRole)
                    .build());
        }
    }

    @Service
    protected static class AuthorityService {
        SimpleJpaRepository<Authority, UserRole> repository;

        public AuthorityService(EntityManager entityManager) {
            this.repository = new SimpleJpaRepository<>(Authority.class, entityManager);
        }

        @Transactional
        public void save(Authority authority) {
            repository.save(authority);
        }
    }
}
