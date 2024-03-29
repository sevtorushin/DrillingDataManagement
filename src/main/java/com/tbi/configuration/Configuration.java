package com.tbi.configuration;

import org.springframework.context.annotation.*;
import service.containers.ExtendedClientPool;
import service.containers.ServerPool;

@org.springframework.context.annotation.Configuration
@ComponentScan("com.tbi")
@PropertySource("/application.properties")
public class Configuration {

    @Bean(name = "globalServerPool")
    public ServerPool getServerPool(){
        return new ServerPool();
    }

    @Bean(name = "globalClientPool")
    public ExtendedClientPool getClientPool(){
        return new ExtendedClientPool();
    }
}
