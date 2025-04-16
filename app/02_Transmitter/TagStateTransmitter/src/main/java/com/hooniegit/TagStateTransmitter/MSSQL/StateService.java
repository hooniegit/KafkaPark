package com.hooniegit.TagStateTransmitter.MSSQL;

import com.hooniegit.SourceData.Interface.TagData;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * MSSQL
 */
@Service
public class StateService {

    private final JdbcTemplate stateJdbcTemplate;
    private final StateReference referenceMap;

    @Autowired
    public StateService(JdbcTemplate jdbcTemplate,
                        StateReference referenceMap) {
        this.stateJdbcTemplate = jdbcTemplate;
        this.referenceMap = referenceMap;
    }

    @PostConstruct
    public void initialTask() {
        update();
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void periodicalTask() throws Exception {
        update();
    }

    private void update() {
        // SELECT TagID & Create List
        String getSql = """
        SELECT TagID 
        FROM ToolState.dbo.TagList 
        WHERE ToolState = 'State'
        ORDER BY TagID ASC;
        """;
        List<Integer> tagIdList = stateJdbcTemplate.query(getSql, (rs, rowNum) -> rs.getInt("TagID"));

        System.out.println(">> TagID List Size :: " + tagIdList.size());

        // Update List if Database is Updated
        this.referenceMap.update(tagIdList);
    }

    /**
     *
     * @param dataList
     */
    public void check(List<TagData<Boolean>> dataList) {
        List<TagData<Boolean>> entryList = new ArrayList<>();

        // Check if Database is Updated
        for (TagData<Boolean> data : dataList) {
            if (!this.referenceMap.getIdMap().get(data.getId()).equals(data.getValue())) {
                System.out.println("[BF]" + this.referenceMap.getIdMap().get(data.getId()) + " -> [AF]" + data.getValue());
                entryList.add(data);
            }
        }

        // Insert Data & Update Map if Database is Updated
        if (!entryList.isEmpty()) {
            System.out.println(">> CHANGED DATA :: " + entryList.size());
            this.referenceMap.updateMap(entryList);
            insertMultipleRows(entryList);
        }
    }

    /**
     *
     * @param dataList
     */
    protected void insertMultipleRows(List<TagData<Boolean>> dataList) {
        String sql = """
        INSERT INTO TagValue (TagID, Timestamp, Value) 
        VALUES (?, ?, ?)
        """;

        stateJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TagData<Boolean> row = dataList.get(i);
                ps.setInt(1, row.getId());
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.parse(row.getTimestamp())));
                ps.setString(3, row.getValue().toString());
            }

            @Override
            public int getBatchSize() {
                return dataList.size();
            }
        });
    }

}
