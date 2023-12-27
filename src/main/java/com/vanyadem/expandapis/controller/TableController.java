package com.vanyadem.expandapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vanyadem.expandapis.dto.TableRequest;
import com.vanyadem.expandapis.service.DynamicTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class TableController {

    private final DynamicTableService dynamicTableService;

    @PostMapping("/add")
    public ResponseEntity<?> addTable(@RequestBody TableRequest tableRequest) {
//        JSONObject jsonObject = new JSONObject(jsonData);

        dynamicTableService.createTableFromJson(tableRequest);
        dynamicTableService.insertDataIntoTable(tableRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllRowsByTableName() throws JsonProcessingException {
        String response = dynamicTableService.getAll("products");
        return ResponseEntity.ok(response);
    }
}
