package com.hooniegit.SourceData.Interface;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */

@Getter @Setter @AllArgsConstructor
public class Package {

    // Tag Properties
    private TagGroup<Double> value;
    private TagGroup<Boolean> mode;

    // Group Properties
    private TagGroup<Integer> state;
    private TagGroup<String> statusOne;
    private TagGroup<String> statusTwo;

}