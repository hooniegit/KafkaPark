package com.hooniegit.SourceData.Source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Body 타입으로 사용할 데이터 클래스입니다.
 *  * 해당 클래스는 테스트용으로 현장에서 사용하지 않습니다.
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Specified {

    private int id;
    private double value;
    private boolean state;

    private int group;
    private State group_state_01;
    private String group_state_02;
    private String group_state_03;

}
