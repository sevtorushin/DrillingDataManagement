package com.tbi;

import com.tbi.servise.templates.TemplateSetTasks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import servers.another.ExtendedServer;
import servers.another.Server;

import java.io.IOException;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
