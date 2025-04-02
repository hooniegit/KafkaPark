package com.hooniegit.StateOne.Config;

import com.hooniegit.StateOne.Config.Handlers.DefineHandler;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public List<Handler<TagGroup<TagData<State>>>> handlers() {
        return List.of(new DefineHandler());
    }

    /**
     * 
     * @return
     */
    @Bean
    public StreamAutoConfiguration<TagGroup<TagData<State>>> streamAutoConfiguration() {
        return new StreamAutoConfiguration<>();
    }

}
