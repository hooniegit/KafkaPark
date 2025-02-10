package com.hooniegit.KafkaProducer.DataClass;

import lombok.Getter;

@Getter
public enum State {

    UNKNOWN(0, "UNKNOWN"), 
    STOPPED(1, "STOPPED"), 
    RUNNING(2, "RUNNING"), 
    PROBLEM(3, "PROBLEM");

    int id;
    String info;

    State(int id, String info) {
        this.id = id;
        this.info = info;
    }

}
