package com.hooniegit.KafkaProducer.Data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* Inner Data Class
* - Assume No Data Omission
*/

@Getter @NoArgsConstructor @AllArgsConstructor
public class Inner implements Serializable {

	// Asset Properties
	private int toolId;
	private StateCondition state;
	private String step;
	private String condition;

	// Tag Properties
	private int parameter;
	private double value;
	private boolean pmmode;

}
