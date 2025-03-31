package com.hooniegit.SourceData.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 데이터 클래스입니다.
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TagData<T> {

    private int id;
    private T value;

}
