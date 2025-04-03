package com.hooniegit.StateOne.Config;

import com.hooniegit.StateOne.Config.Handlers.DefineHandler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hooniegit.SourceData.Event.EventWrapper;
import com.hooniegit.SourceData.Source.State;
import com.hooniegit.Xtream.Stream.Handler;
import com.hooniegit.Xtream.Stream.StreamAutoConfiguration;

// Nexus Imports
import com.hooniegit.SourceData.Tag.TagGroup;
import com.hooniegit.SourceData.Tag.TagData;

/**
 * 
 */

@Configuration
public class StreamConfiguration {

    /**
     * 
     * @return
     */
    @Bean
    public List<Handler<EventWrapper<TagGroup<Integer>, ConcurrentHashMap<Integer, Integer>>>> handlers() {
        return List.of(new DefineHandler());
    }

    /**
     * 
     * @return
     */
    @Bean
    public StreamAutoConfiguration<EventWrapper<TagGroup<Integer>, ConcurrentHashMap<Integer, Integer>>> streamAutoConfiguration() {
        return new StreamAutoConfiguration<>();
    }

}
