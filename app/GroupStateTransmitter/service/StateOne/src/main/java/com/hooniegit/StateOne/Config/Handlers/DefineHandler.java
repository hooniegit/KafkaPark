package com.hooniegit.StateOne.Config.Handlers;

// Nexus Imports
import com.hooniegit.Xtream.Stream.Event;
import com.hooniegit.Xtream.Stream.Handler;
import com.hooniegit.SourceData.Tag.TagGroup;
import com.hooniegit.SourceData.Tag.TagData;

public class DefineHandler extends Handler<TagGroup<Integer>> {
    
    @Override
    protected void process(Event<TagGroup<Integer>> event) {
        // Handle the event here
        System.out.println("Processing DefineHandler event: " + event.getData());
    }

}
