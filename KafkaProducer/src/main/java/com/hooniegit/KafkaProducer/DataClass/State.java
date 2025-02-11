package com.hooniegit.KafkaProducer.DataClass;

import lombok.Getter;

/**
 * State 정보를 표기하기 위한 Enum 규격입니다. 상태 변화 추척을 위해 사용합니다.
 */

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
