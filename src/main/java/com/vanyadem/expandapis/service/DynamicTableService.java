package com.vanyadem.expandapis.service;

import org.json.JSONObject;

public interface DynamicTableService {

    void createTableFromJson(JSONObject jsonData);

    void insertDataIntoTable(JSONObject jsonData);

    String getAll();
}
