package com.hooniegit.KafkaConsumer.DataClass;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 데이터 클래스입니다. Header 부분과 Body 부분으로 구성되어 있습니다.
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Complexed<T> {

    private HashMap<String, Object> header;
    private T body;

}
