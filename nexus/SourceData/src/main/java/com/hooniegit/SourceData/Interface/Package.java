package com.hooniegit.SourceData.Interface;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 
 */
@Getter @Setter @AllArgsConstructor
public class Package {

    // Tag Properties
    private List<TagData<Double>> value;
    private List<TagData<Boolean>> mode;

    // Group Properties
    private List<TagData<Integer>> state;
    private List<TagData<String>> statusOne;
    private List<TagData<String>> statusTwo;

}