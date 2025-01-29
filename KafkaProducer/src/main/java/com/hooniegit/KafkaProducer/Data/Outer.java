package com.hooniegit.KafkaProducer.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
* Outer Data Class
* - Assume No Data Omission
*/

public class Outer<T> implements Serializable {

    // Header Property
    private Map<String, Object> header = new HashMap<>();

    // Body Property
    @Getter
    private T body;

    public Outer() {
        this.header.put("timestamp", (Object) LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    
    public Outer(T body) {
        this.header.put("timestamp", (Object) LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.body = body;
    }
    
    /**
     * Set Header
     * @param k
     * @param v
     */
    public void set(String k, Object v) {
        this.header.put(k, v);
    }
    
    /**
     * Get Header Value
     * @param k
     * @return
     */
    public Object get(String k) {
        try {
            return header.get(k);
        } catch (Exception ex) {
            return null;
        }
    }


}
