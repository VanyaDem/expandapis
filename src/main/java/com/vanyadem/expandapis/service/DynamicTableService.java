package com.vanyadem.expandapis.service;

import com.vanyadem.expandapis.dto.TableRequest;
import org.json.JSONObject;

public interface DynamicTableService {

    void createTableFromJson(TableRequest tableRequest);

    void insertDataIntoTable(TableRequest tableRequest);

    String getAll(String tableName);
}
