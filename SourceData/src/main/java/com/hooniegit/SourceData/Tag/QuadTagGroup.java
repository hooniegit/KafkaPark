package com.hooniegit.SourceData.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.hooniegit.SourceData.Source.State;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class QuadTagGroup {

    public TagGroup<TagData<Long>> tagGroup;
    public TagGroup<TagData<Boolean>> stateGroup;

    public TagGroup<TagData<State>> state01Group;
    public TagGroup<TagData<String>> state02Group;
    public TagGroup<TagData<String>> state03Group;

}
