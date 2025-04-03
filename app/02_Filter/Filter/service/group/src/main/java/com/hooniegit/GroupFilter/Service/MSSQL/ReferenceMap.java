package com.hooniegit.GroupFilter.Service.MSSQL;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class ReferenceMap {
    
    @Getter
    private ConcurrentHashMap<Integer, Integer> reference = new ConcurrentHashMap<>();

    public void updateAll(ConcurrentHashMap<Integer, Integer> newData) {
        this.reference = newData;
    }

}
