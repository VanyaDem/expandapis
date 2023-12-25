package com.vanyadem.expandapis.controller;

import com.vanyadem.expandapis.service.DynamicTableService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class TableController {

    private final DynamicTableService dynamicTableService;

    @PostMapping("/add")
    public ResponseEntity<?> addTable(@RequestBody Map<String, Object> jsonData){
        JSONObject jsonObject = new JSONObject(jsonData);

        dynamicTableService.createTableFromJson(jsonObject);
        dynamicTableService.insertDataIntoTable(jsonObject);
        return ResponseEntity.ok().build();
    }
}
