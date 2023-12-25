package com.vanyadem.expandapis.service;

import com.vanyadem.expandapis.exceptions.SuchTableExistException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLSyntaxErrorException;

@Service
@RequiredArgsConstructor
public class DynamicTableService {

    private final JdbcTemplate jdbcTemplate;

    public void createTableFromJson(JSONObject jsonData){



        String tableName = jsonData.getString("table");
        JSONArray recordsArray = jsonData.getJSONArray("records");

        tableValidation(tableName);

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ").append(tableName).append(" (");

        JSONObject record = recordsArray.getJSONObject(0);
        record.keySet().forEach(column -> {
            query.append(column).append(" VARCHAR (255), ");
        });

        query.delete(query.length() - 2, query.length());
        query.append(")");

        jdbcTemplate.execute(query.toString());
    }

    public void insertDataIntoTable(JSONObject jsonData) {
        String tableName = jsonData.getString("table");
        JSONArray recordsArray = jsonData.getJSONArray("records");

        for (int i = 0; i < recordsArray.length(); i++) {
            JSONObject record = recordsArray.getJSONObject(i);
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO ").append(tableName).append(" (");

            record.keySet().forEach(column -> queryBuilder.append(column).append(", "));
            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Видалення останньої коми

            queryBuilder.append(") VALUES (");

            record.keySet().forEach(column -> {
                String value = record.getString(column);
                queryBuilder.append("'").append(value).append("', ");
            });
            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Видалення останньої коми

            queryBuilder.append(")");

            jdbcTemplate.execute(queryBuilder.toString());        }
    }

    private void tableValidation(String tableName) {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, tableName);
        if(count > 0){
            throw new SuchTableExistException(String.format("Table '%s' already exist", tableName));
        }
    }

}
