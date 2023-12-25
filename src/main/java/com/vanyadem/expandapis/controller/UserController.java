package com.vanyadem.expandapis.controller;

import com.vanyadem.expandapis.dto.JwtResponse;
import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.service.UserService;
import com.vanyadem.expandapis.utils.DtoUtils;
import com.vanyadem.expandapis.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
        userService.addUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/authenticate")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody UserDto userDto){
        authenticate(userDto);

        UserDetails userDetails = userService
                .convertToUserDetails(DtoUtils
                        .dtoToUser(userDto));

        return ResponseEntity
                .ok( new JwtResponse(jwtTokenUtils.generateToken(userDetails)));
    }

    private void authenticate(UserDto userDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword())
                );
    }

}
