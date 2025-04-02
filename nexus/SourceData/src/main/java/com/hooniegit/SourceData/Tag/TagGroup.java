package com.hooniegit.SourceData.Tag;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 데이터 그룹 클래스입니다. 타임스탬프 정보를 포함합니다.
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TagGroup<T> {

    private String timestamp;
    private TagData<T>[] tagData;

}
