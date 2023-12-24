package com.vanyadem.expandapis.service;

import com.vanyadem.expandapis.dao.UserRepository;
import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow();
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
                new ArrayList<>()
        );
    }

    @Override
    public User addUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return userRepository.save(user);
    }
}
