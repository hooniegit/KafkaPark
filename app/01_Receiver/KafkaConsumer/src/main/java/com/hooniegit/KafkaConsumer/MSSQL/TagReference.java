package com.hooniegit.KafkaConsumer.MSSQL;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 */

@Component
public class TagReference {

    // <id, index> 
    @Getter
    private ConcurrentHashMap<Integer, Integer> ids = new ConcurrentHashMap<>();

    /**
     * update ids map
     * @param ids
     */
    public void updateIds(ConcurrentHashMap<Integer, Integer> ids) {
        this.ids = ids;
    }

}
