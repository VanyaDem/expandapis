package com.vanyadem.expandapis.utils;

import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.entity.User;

public class DtoUtils {

    public static User dtoToUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
