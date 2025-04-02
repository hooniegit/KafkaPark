package com.hooniegit.StateOne.Service.MSSQL;

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
 * 
 */

@Service
public class MssqlService {

    private final JdbcTemplate jdbcTemplate;

    @Getter
    private final ReferenceMap referenceMap;

    @Autowired
    public MssqlService(JdbcTemplate jdbcTemplate, ReferenceMap referenceMap) {
        this.jdbcTemplate = jdbcTemplate;
        this.referenceMap = referenceMap;
    }

    /**
     * 
     */
    @PostConstruct
    public void initialTask() {
        updateReference();
    }

    /**
     * 
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void periodicalTask() {
        try {
            updateReference();
        } catch (Exception ex) {

        }
    }

    /**
     * 
     */
    private void updateReference() {
        String getSql = """
            SELECT
                id, name
            FROM
                ctc_kafka.dbo.kf_tag
            ORDER BY id ASC;
        """;

        referenceMap.updateAll(jdbcTemplate.query(getSql, new RowMapper<ConcurrentHashMap<Integer, Integer>>() {
            @Override
            public ConcurrentHashMap<Integer, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                ConcurrentHashMap<Integer, Integer> resultMap = new ConcurrentHashMap<>();
                while (rs.next()) {
                    resultMap.put(rs.getInt("name"), rs.getInt("id"));
                }
                return resultMap;
            }
        }).stream().findFirst().orElse(new ConcurrentHashMap<>()));
    }
}
