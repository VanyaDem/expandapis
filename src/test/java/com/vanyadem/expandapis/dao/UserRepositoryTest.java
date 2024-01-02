package com.vanyadem.expandapis.dao;

import com.vanyadem.expandapis.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "/application-test.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByNameShouldReturnUserWithSpecificName() {
        User user = new User();
        user.setUsername("Vanya");
        user.setPassword("123");
        entityManager.persist(user);

        User targetUser = userRepository
                .findByName("Vanya")
                .orElse(new User());

        Assertions.assertEquals("Vanya", targetUser.getUsername());
    }

    @Test
    public void findByNameShouldReturnEmptyOptionalIfNoUser() {
        Optional<User> optionalUser = userRepository.findByName("Vanya");

        Assertions.assertTrue(optionalUser.isEmpty());
    }
}