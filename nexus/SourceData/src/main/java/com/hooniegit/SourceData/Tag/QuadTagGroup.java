package com.hooniegit.SourceData.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class QuadTagGroup {

    public TagGroup<Long> tagGroup;
    public TagGroup<Boolean> stateGroup;

    public TagGroup<Integer> state01Group;
    public TagGroup<String> state02Group;
    public TagGroup<String> state03Group;

}
