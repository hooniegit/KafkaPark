package com.hooniegit.SourceData.Interface;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TagGroup<T> {

    private String timestamp;
    private List<TagData<T>> group;

}
