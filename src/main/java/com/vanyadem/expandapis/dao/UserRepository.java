package com.vanyadem.expandapis.dao;

import com.vanyadem.expandapis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u where u.username = :username")
    Optional<User> findByName(String username);

}
