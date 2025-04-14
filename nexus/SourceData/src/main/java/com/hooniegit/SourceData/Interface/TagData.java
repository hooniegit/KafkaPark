package com.hooniegit.SourceData.Interface;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TagData<T> {

    private int id;
    private T value;
    private String timestamp;

}
