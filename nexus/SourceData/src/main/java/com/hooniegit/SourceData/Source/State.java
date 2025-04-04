package com.hooniegit.SourceData.Source;

import lombok.Getter;

/**
 * 
 */

@Getter
public enum State {

    UNKNOWN(-1, "UNKNOWN"), 
    STOPPED(0, "STOPPED"), 
    RUNNING(1, "RUNNING"), 
    PROBLEM(2, "PROBLEM");

    int value;
    String expression;

    State(int value, String expression) {
        this.value = value;
        this.expression = expression;
    }

}
