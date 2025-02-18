package com.hooniegit.KafkaConsumer.DataClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CategoryClass {

    private int category;
    private String status_01;
    private String status_02;
    private String status_03;

    private String timestamp;

}