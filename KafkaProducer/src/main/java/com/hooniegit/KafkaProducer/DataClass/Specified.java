package com.hooniegit.KafkaProducer.DataClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Body 타입으로 사용할 데이터 클래스입니다.<br>
 * - id 단위 데이터와 category 단위 데이터로 구분됩니다.
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Specified {

    private int id;
    private double value;
    private String status;

    private int category;
    private String status_01;
    private String status_02;
    private String status_03;

}
