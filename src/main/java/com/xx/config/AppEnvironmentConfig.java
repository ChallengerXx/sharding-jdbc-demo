package com.xx.config;

import com.xx.config.data.property.DbPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

@Configuration
public class AppEnvironmentConfig implements EnvironmentAware {

    @Autowired
    DbPropertyService dbPropertyService;

    @Override
    public void setEnvironment(Environment environment) {
        ConfigurableEnvironment evn = (ConfigurableEnvironment) environment;
        evn.getPropertySources().addLast(new PropertySource<DbPropertyService>("dbPropertySource", dbPropertyService) {
            @Override
            public Object getProperty(String name) {
                return dbPropertyService.getProperty(name);
            }
        });
    }
}
