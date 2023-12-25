package com.vanyadem.expandapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vanyadem.expandapis.service.DynamicTableService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class TableController {

    private final DynamicTableService dynamicTableService;

    @PostMapping("/add")
    public ResponseEntity<?> addTable(@RequestBody Map<String, Object> jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);

        dynamicTableService.createTableFromJson(jsonObject);
        dynamicTableService.insertDataIntoTable(jsonObject);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllRowsByTableName() throws JsonProcessingException {
        String response = dynamicTableService.getAll();
        return ResponseEntity.ok(response);
    }
}
