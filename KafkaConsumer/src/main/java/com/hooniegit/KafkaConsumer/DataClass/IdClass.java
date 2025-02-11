package com.hooniegit.KafkaConsumer.DataClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class IdClass {

    private int id;
    private double value;
    private String status;

    private String timestamp;

}
