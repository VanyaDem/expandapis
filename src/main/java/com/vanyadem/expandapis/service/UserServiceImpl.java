package com.vanyadem.expandapis.service;

import com.vanyadem.expandapis.dao.UserRepository;
import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.entity.User;
import com.vanyadem.expandapis.exceptions.SuchUserExistException;
import com.vanyadem.expandapis.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ValidationUtils validationUtils;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of()
        );
    }

    @Override
    public User addUser(UserDto userDto) {
        validationUtils.validationRequest(userDto);
        User user = new User();

        try {

            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user = userRepository.save(user);

        } catch (DataIntegrityViolationException exception) {
            throw new SuchUserExistException(String
                    .format("User with name '%s' already exist!", userDto.getUsername()));
        }
        return user;
    }

    public org.springframework.security.core.userdetails.User convertToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))

        );
    }
}
