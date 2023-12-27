package com.vanyadem.expandapis.utils;

import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.entity.User;

/**
 * Utility class for converting UserDto objects to User and vice versa.
 */
public class DtoUtils {

    /**
     * Method that converts a UserDto object to a User object.
     *
     * @param userDto UserDto object to convert
     * @return User object obtained from UserDto
     */
    public static User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
