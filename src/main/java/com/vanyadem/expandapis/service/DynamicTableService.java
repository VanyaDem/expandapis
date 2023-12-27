package com.vanyadem.expandapis.service;

import com.vanyadem.expandapis.dto.TableRequest;

public interface DynamicTableService {

    void createTableFromJson(TableRequest tableRequest);

    void insertDataIntoTable(TableRequest tableRequest);

    String getAll(String tableName);
}
