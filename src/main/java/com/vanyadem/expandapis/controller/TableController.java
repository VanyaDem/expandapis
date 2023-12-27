package com.vanyadem.expandapis.controller;

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
        dynamicTableService.createTableFromJson(tableRequest);
        dynamicTableService.insertDataIntoTable(tableRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all/{tableName}")
    public ResponseEntity<String> getAllRowsByTableName(
            @PathVariable(name = "tableName") String tableName) {

        String response = dynamicTableService.getAll(tableName);
        return ResponseEntity.ok(response);
    }
}
