package com.hooniegit.SourceData.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 */

@Getter @AllArgsConstructor
public class EventWrapper<D, E> {

    private final D data;
    private final E etc;

}
