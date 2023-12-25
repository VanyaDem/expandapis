package com.vanyadem.expandapis.service;

import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User addUser(UserDto userDto);

    org.springframework.security.core.userdetails.User convertToUserDetails(User user);

}
