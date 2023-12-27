package com.vanyadem.expandapis.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TableRequest {

    @NotBlank
    private String table;

    @NotBlank
    private List<Map<String, String>> records;
}
