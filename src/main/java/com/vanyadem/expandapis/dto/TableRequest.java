package com.vanyadem.expandapis.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TableRequest {

    private String table;

    private List<Map<String, String>> records;
}
