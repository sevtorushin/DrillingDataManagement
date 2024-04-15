package com.tbi;

import clients.another.ExtendedClient;
import com.tbi.servise.templates.TemplateSetHandlers;
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

//        TemplateSetTasks setTasks = new TemplateSetTasks();
//        TemplateSetHandlers setHandlers = new TemplateSetHandlers();
//
//        ExtendedClient client = new ExtendedClient("127.0.0.1", 7000);
//        client.connect();
//
//        setTasks.addReceiveFromClient(client);
//
//        setHandlers.addPrintFromClient(client);
//
//        client.getTaskContainer().get("ReceiveFromClient_1").execute();
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
