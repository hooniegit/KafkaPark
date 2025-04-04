package com.hooniegit.SourceData.Source;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Data<T> {

    private HashMap<String, Object> header;
    private T body;

}
