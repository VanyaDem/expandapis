package com.vanyadem.expandapis.controller;

import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
        userService.addUser(userDto);
        return ResponseEntity.ok().build();
    }

}
