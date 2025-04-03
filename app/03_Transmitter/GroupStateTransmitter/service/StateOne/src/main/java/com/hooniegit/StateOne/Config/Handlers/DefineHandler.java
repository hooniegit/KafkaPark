package com.hooniegit.StateOne.Config.Handlers;

// Nexus Imports
import com.hooniegit.Xtream.Stream.Event;
import com.hooniegit.Xtream.Stream.Handler;
import com.hooniegit.SourceData.Tag.TagData;
import com.hooniegit.SourceData.Tag.TagGroup;

import java.util.concurrent.ConcurrentHashMap;

import com.hooniegit.SourceData.Event.EventWrapper;

public class DefineHandler extends Handler<EventWrapper<TagGroup<Integer>, ConcurrentHashMap<Integer, Integer>>> {
    
    @Override
    protected void process(Event<EventWrapper<TagGroup<Integer>, ConcurrentHashMap<Integer, Integer>>> event) {
        
        TagData<Integer>[] tagData = event.getData().getData().getTagData();

    }

}
