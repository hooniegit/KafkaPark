package com.hooniegit.SourceData.Source;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Body {

    // Tag Properties
    private int id;
    private double value;
    private boolean mode;

    // Group Properties
    private int group;
    private State State;
    private String StatusOne;
    private String StatusTwo;

}
