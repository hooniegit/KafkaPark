package com.hooniegit.SourceData.Tag.Package;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.hooniegit.SourceData.Tag.TagData;
import com.hooniegit.SourceData.Tag.TagGroup;

/**
 * 패키지 파일들을 보관하는 패키지 클래스입니다.
 */

@Getter @Setter @AllArgsConstructor
public class SinglePackage {

    private TagGroup<TagData<Double>> value;
    private TagGroup<TagData<Boolean>> state;
    private TagGroup<TagData<Integer>> stateOne;
    private TagGroup<TagData<String>> stateTwo;
    private TagGroup<TagData<String>> stateThree;

}
