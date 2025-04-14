package com.hooniegit.KafkaConsumer.MSSQL;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 *
 */

@Component
public class StateReference {

    // <id, index>
    @Getter
    private ConcurrentHashMap<Integer, Integer> ids = new ConcurrentHashMap<>();

    // <group, [state, statusOne, statusTwo]>
    @Getter
    private ConcurrentHashMap<Integer, Integer[]> groups = new ConcurrentHashMap<>();

    /**
     * update ids map
     * @param ids
     */
    public void updateIds(ConcurrentHashMap<Integer, Integer> ids) {
        this.ids = ids;
    }

    /**
     * update groups map
     * @param groups
     */
    public void updateGroups(ConcurrentHashMap<Integer, Integer[]> groups) {
        this.groups = groups;
    }

}
