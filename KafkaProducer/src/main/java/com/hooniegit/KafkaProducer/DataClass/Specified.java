package com.hooniegit.KafkaProducer.DataClass;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Specified {

    private int id;
    private int category;

    private double value;

    private String status;
    private LocalDateTime time_status;

    private String status_01;
    private LocalDateTime time_status_01;
    private String status_02;
    private LocalDateTime time_status_02;
    private String status_03;
    private LocalDateTime time_status_03;

}
