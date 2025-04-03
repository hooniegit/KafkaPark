package com.hooniegit.SourceData.Tag.Package;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.hooniegit.SourceData.Tag.TagDataDouble;
import com.hooniegit.SourceData.Tag.TagDataTripple;
import com.hooniegit.SourceData.Tag.TagGroup;

/**
 * 패키지 파일들을 보관하는 패키지 클래스입니다.
 */

@Getter @Setter @AllArgsConstructor
public class GroupPackage {

    private TagGroup<TagDataDouble<Double, Boolean>> tagPackage;
    private TagGroup<TagDataTripple<Integer, String, String>> groupPackage;

}
