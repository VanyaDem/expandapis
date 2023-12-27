package com.vanyadem.expandapis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanyadem.expandapis.dto.TableRequest;
import com.vanyadem.expandapis.exceptions.SuchTableExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DynamicTableServiceImpl implements DynamicTableService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createTableFromJson(TableRequest tableRequest) {
        String table = tableRequest.getTable();

        tableValidation(table);

        StringBuilder query = buildQueryForTableCreating(tableRequest, table);

        jdbcTemplate.execute(query.toString());
    }


    @Override
    public void insertDataIntoTable(TableRequest tableRequest) {
        String tableName = tableRequest.getTable();

        tableRequest
                .getRecords()
                .forEach(record -> executeInsertQuery(tableName, record));
    }

    public String getAll(String tableName) {
        String query = String.format("SELECT * FROM %s", tableName);
        List<Map<String, Object>> products = jdbcTemplate.queryForList(query);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private StringBuilder buildQueryForTableCreating(TableRequest tableRequest, String table) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ").append(table).append(" (");

        tableRequest
                .getRecords()
                .get(0)
                .keySet()
                .forEach(column -> appendColumnWithType(column, query));

        query.delete(query.length() - 2, query.length());
        query.append(")");
        return query;
    }

    private static StringBuilder buildQueryForTableInserting(String tableName, Map<String, String> record) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ").append(tableName).append(" (");

        record.keySet().forEach(column -> queryBuilder.append(column).append(", "));
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());

        queryBuilder.append(") VALUES (");

        record.keySet().forEach(column -> {
            String value = record.get(column);
            queryBuilder.append("'").append(value).append("', ");
        });

        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());

        queryBuilder.append(")");
        return queryBuilder;
    }

    private void executeInsertQuery(String tableName, Map<String, String> record) {
        StringBuilder queryBuilder = buildQueryForTableInserting(tableName, record);

        jdbcTemplate.execute(queryBuilder.toString());
    }

    private void tableValidation(String tableName) {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, tableName);
        if (count > 0) {
            throw new SuchTableExistException(String.format("Table '%s' already exist", tableName));
        }
    }

    private void appendColumnWithType(String column, StringBuilder builder) {
        builder.append(column).append(" VARCHAR (255), ");
    }

}
