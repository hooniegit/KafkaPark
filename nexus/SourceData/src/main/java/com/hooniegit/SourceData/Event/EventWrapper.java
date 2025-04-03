package com.hooniegit.SourceData.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Xtream Event Wrapper 클래스입니다. 이벤트 객체에 추가적인 항목을 포함할 때 사용합니다.
 */

@Getter @AllArgsConstructor
public class EventWrapper<D, E> {

    private final D data;
    private final E etc;

}
