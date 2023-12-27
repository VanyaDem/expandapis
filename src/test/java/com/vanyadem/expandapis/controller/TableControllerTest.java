package com.vanyadem.expandapis.controller;

import com.vanyadem.expandapis.entity.User;
import com.vanyadem.expandapis.service.UserService;
import com.vanyadem.expandapis.utils.JwtTokenUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    private User user = new User();

    private String token;

    private final String json = """
                        
            {
            "table" : "products",
            "records" : [
            {
            "entryDate": "03-01-2023",
            "itemCode": "11111",
            "itemName": "Test Inventory 1",
            "itemQuantity": "20",
            "status": "Paid"
            },
            {
            "entryDate": "03-01-2023",
            "itemCode": "11111",
            "itemName": "Test Inventory 2",
            "itemQuantity": "20",
            "status": "Paid"
            }]
            }
            """;

    @BeforeEach
    void createToken() {
        dropTable();
        user.setUsername("Vanya");
        user.setPassword(passwordEncoder.encode("1234"));

        token = jwtTokenUtils.generateToken(userService.convertToUserDetails(user));
    }

    @Test
    @Transactional
    @Rollback
    public void addTableShouldReturn401StatusIfUserUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Transactional
    @Rollback
    public void addTableShouldReturn201Status() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    @Rollback
    public void addTableShouldReturn400StatusIfTokenIsCorrupted() throws Exception {
        token = token + "fjh";
        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @Transactional
    @Rollback
    public void addTableShouldReturn401StatusIfTokenIsExpired() throws Exception {
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VydHkiLCJpYXQiOjE3MDM2ODg2MjcsImV4cCI6MTcwMzY5MjIyN30.kUkXTixQ3f5g9xkGtFTy5xMyT3zNlULe2YwzX3vYzzE";
        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Transactional
    @Rollback
    public void addTableShouldInsertDataIntoSpecificTable() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        long rowCount = (long) entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM products").getSingleResult();

        Assertions.assertEquals(2, rowCount);

    }

    @Test
    @Transactional
    @Rollback
    public void addTableShouldCreateTableInTheDBBySpecificNameWithSpecificColumns() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        List<String> entryDate = createQueryByColumnName("entryDate");
        List<String> itemCode = createQueryByColumnName("itemCode");
        List<String> itemName = createQueryByColumnName("itemName");
        List<String> itemQuantity = createQueryByColumnName("itemQuantity");
        List<String> status = createQueryByColumnName("status");


        Assertions.assertEquals("03-01-2023", entryDate.get(0));
        Assertions.assertEquals("03-01-2023", entryDate.get(1));
        Assertions.assertEquals("11111", itemCode.get(0));
        Assertions.assertEquals("11111", itemCode.get(1));
        Assertions.assertEquals("Test Inventory 1", itemName.get(0));
        Assertions.assertEquals("Test Inventory 2", itemName.get(1));
        Assertions.assertEquals("20", itemQuantity.get(0));
        Assertions.assertEquals("20", itemQuantity.get(1));
        Assertions.assertEquals("Paid", status.get(0));
        Assertions.assertEquals("Paid", status.get(1));

    }

    @Test
    @Transactional
    @Rollback
    public void getAllRowsByTableNameShouldReturn401StatusIfUserUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/all/products"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Transactional
    @Rollback
    public void getAllRowsByTableNameShouldReturn201Status() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/all/products")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private List<String> createQueryByColumnName(String columnName) {
        return entityManager
                .createNativeQuery(
                        String.format("SELECT %s FROM products ;", columnName))
                .getResultList();
    }

    private void dropTable() {
        try {
            entityManager.createNativeQuery("DROP TABLE products").executeUpdate();
        } catch (Exception e) {
        }
    }

}