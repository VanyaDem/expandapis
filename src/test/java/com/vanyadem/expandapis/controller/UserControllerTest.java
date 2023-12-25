package com.vanyadem.expandapis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanyadem.expandapis.dto.UserDto;
import com.vanyadem.expandapis.entity.User;
import com.vanyadem.expandapis.exceptions.SuchUserExistException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @Transactional
    @Rollback
    public void addUserShouldReturn201Status() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("Vanya");
        userDto.setPassword("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());


    }

    @Test
    @Transactional
    @Rollback
    public void addUserShouldThrowValidationExceptionIfUsernameIsEmpty() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("");
        userDto.setPassword("123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

    @Test
    @Transactional
    @Rollback
    public void addUserShouldThrowValidationExceptionIfPasswordIsEmpty() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("Vanya");
        userDto.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

    @Test
    @Transactional
    @Rollback
    public void addUserShouldReturn400StatusIfUserExist() throws Exception {

        String password = "123";
        password = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername("Vanya");
        user.setPassword(password);
        entityManager.persist(user);

        UserDto userDto = new UserDto();
        userDto.setUsername("Vanya");
        userDto.setPassword("123");

        var exception = mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResolvedException().getClass();

        Assertions.assertEquals(exception, SuchUserExistException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void authenticateUserShouldReturnToken() throws Exception {

        String password = "1234";
        password = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername("Vanyaa");
        user.setPassword(password);
        entityManager.persist(user);


        UserDto userDto = new UserDto();
        userDto.setUsername("Vanyaa");
        userDto.setPassword("1234");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertTrue(result.startsWith("{\"token\":"));
    }

    @Test
    @Transactional
    @Rollback
    public void authenticateUserShouldReturn401Status() throws Exception {

        User user = new User();
        user.setUsername("Vanyaa");
        user.setPassword(passwordEncoder.encode("1234"));
        entityManager.persist(user);


        UserDto userDto = new UserDto();
        userDto.setUsername("Vanyaa");
        userDto.setPassword("1111");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}