package com.hooniegit.KafkaProducer.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import org.ini4j.Ini;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class IniConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    
    @Value("${configuration.path}")
    private String configFilePath;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            Properties props = new Properties();
            File configFile = new File(configFilePath);

            if (!configFile.exists() || !configFile.canRead()) {
                throw new IOException("Config file not found or not readable: " + configFilePath);
            }

            Ini ini = new Ini(new FileReader(configFile));
            ini.forEach((sectionName, section) -> {
                section.forEach((optionName, optionValue) -> {
                    props.put(sectionName + "." + optionName, optionValue);
                });
            });

            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            PropertiesPropertySource iniPropertySource = new PropertiesPropertySource("iniPropertySource", props);
            environment.getPropertySources().addFirst(iniPropertySource);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from config.ini", e);
        }
    }
}