package com.hooniegit.SourceData.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 태그 데이터 클래스입니다.
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TagDataDouble<F, S> {

    private int id;
    private F valueFirst;
    private S valueSecond;

}
