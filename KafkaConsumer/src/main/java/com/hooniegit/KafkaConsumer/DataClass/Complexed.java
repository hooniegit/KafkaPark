package com.hooniegit.KafkaConsumer.DataClass;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Complexed {

    private HashMap<String, Object> header;
    private Specified body;

}
