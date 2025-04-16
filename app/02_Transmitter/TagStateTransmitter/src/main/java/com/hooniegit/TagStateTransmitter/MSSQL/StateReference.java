package com.hooniegit.TagStateTransmitter.MSSQL;

import com.hooniegit.SourceData.Interface.TagData;

import lombok.Getter;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */

@Component
public class StateReference {

    @Getter
    private final List<Integer> idList = new ArrayList<>();

    @Getter
    private ConcurrentHashMap<Integer, Boolean> idMap = new ConcurrentHashMap<>();

    public void updateMap(List<TagData<Boolean>> dataList) {
        for (TagData<Boolean> data : dataList) {
            this.idMap.replace(data.getId(), data.getValue());
        }
    }

    /**
     * update ids map
     * @param newList
     */
    public void update(List<Integer> newList) {
        // Change List to Set
        // Faster Lookup at .contains() (O(n) to O(1))
        var oldIdSet = new HashSet<>(idList);
        var newIdSet = new HashSet<>(newList);

        // Add new ID (new - old)
        for (Integer id : newIdSet) {
            if (!oldIdSet.contains(id)) {
                idList.add(id);
                idMap.put(id, false);
            }
        }

        // Remove old ID (old - new)
        List<Integer> toRemove = new ArrayList<>();
        for (Integer id : oldIdSet) {
            if (!newIdSet.contains(id)) {
                toRemove.add(id);
                idMap.remove(id);
            }
        }
        idList.removeAll(toRemove);
        System.out.println("List & Map Data - UPDATE COMPLETE");
    }

}
