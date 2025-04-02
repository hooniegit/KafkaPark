package com.hooniegit.SourceData.Source;

import lombok.Getter;

/**
 * State 정보를 표기하기 위한 Enum 규격입니다. 상태 변화 추척을 위해 사용합니다. <br>
 * - 해당 이넘은 테스트용으로 현장에서 사용하지 않습니다.
 */

@Getter
public enum State {

    UNKNOWN(-1, "UNKNOWN"), 
    STOPPED(0, "STOPPED"), 
    RUNNING(1, "RUNNING"), 
    PROBLEM(2, "PROBLEM");

    int id;
    String info;

    State(int id, String info) {
        this.id = id;
        this.info = info;
    }

}
