package com.hooniegit.KafkaConsumer.MSSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;


/**
 * MSSQL
 */

@Service
public class MssqlService {

    private final JdbcTemplate jdbcTemplate;

    @Getter
    private final Reference referenceMap;

    @Autowired
    public MssqlService(JdbcTemplate jdbcTemplate, Reference referenceMap) {
        this.jdbcTemplate = jdbcTemplate;
        this.referenceMap = referenceMap;
    }

    /**
     * 
     */
    @PostConstruct
    public void initialTask() {
        updateIds();
        updateGroupIds();
    }

    /**
     * 
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void periodicalTask() {
        try {
            updateIds();
            updateGroupIds();
        } catch (Exception ex) {

        }
    }

    /**
     * 
     */
    private void updateIds() {
        String getSql = """
            SELECT DISTINCT
                paramid, id
            FROM
                ctc_kafka.dbo.kf_tag
            ORDER BY id ASC;
        """;

        referenceMap.updateIds(jdbcTemplate.query(getSql, new RowMapper<ConcurrentHashMap<Integer, Integer>>() {
            @Override
            public ConcurrentHashMap<Integer, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                ConcurrentHashMap<Integer, Integer> resultMap = new ConcurrentHashMap<>();
                while (rs.next()) {
                    resultMap.put(rs.getInt("paramid"), rs.getInt("id"));
                }
                return resultMap;
            }
        }).stream().findFirst().orElse(new ConcurrentHashMap<>()));
    }

    /**
     * 
     */
    private void updateGroupIds() {
        String sql = """
            SELECT group_id,
                   STRING_AGG(CAST(id AS VARCHAR), ',') 
                       WITHIN GROUP (ORDER BY 
                           CASE type
                               WHEN 'state' THEN 1
                               WHEN 'statusOne' THEN 2
                               WHEN 'statusTwo' THEN 3
                           END
                       ) AS id_list
            FROM your_table
            GROUP BY group_id
        """;
    
        referenceMap.updateGroups(jdbcTemplate.query(sql, new RowMapper<ConcurrentHashMap<Integer, Integer[]>>() {
            @Override
            public ConcurrentHashMap<Integer, Integer[]> mapRow(ResultSet rs, int rowNum) throws SQLException {
                ConcurrentHashMap<Integer, Integer[]> resultMap = new ConcurrentHashMap<>();
    
                // rs는 이미 RowMapper 내에서 "한 row"만 가리키므로, while문 X
                int groupId = rs.getInt("group_id");
                String idListStr = rs.getString("id_list");
    
                String[] parts = idListStr.split(",");
                Integer[] ids = new Integer[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    ids[i] = Integer.parseInt(parts[i]);
                }
    
                resultMap.put(groupId, ids);
                return resultMap;
            }
        }).stream().reduce((m1, m2) -> {
            m1.putAll(m2);
            return m1;
        }).orElse(new ConcurrentHashMap<>()));
    }
    
    
}
