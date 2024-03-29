package com.tbi.controllers;

import com.tbi.entities.ServerMetaData;
import com.tbi.servise.ServerService;
import exceptions.HandleException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import servers.another.ExtendedServer;
import servers.another.Server;
import service.IdentifiableMessageHandler;
import service.RunnableTask;

import java.nio.ByteBuffer;

@Controller
@RequestMapping("/servers")
public class MainController {

    private final ServerService serverService;

    public MainController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/all")
    public String server(Model model) {
        model.addAttribute("servers", serverService.getAllServers());
        return "servers";
    }

    @GetMapping("/create")
    public String newServer(@ModelAttribute("metaData") ServerMetaData metaData) {
        return "createServer";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("metaData") ServerMetaData metaData) {
        ExtendedServer server = (ExtendedServer) serverService.createServer(ExtendedServer.class, metaData.getPort());
        server.getHandlerContainer().addNew(new IdentifiableMessageHandler<>("print") {
            @Override
            public void handleMessage(ByteBuffer byteBuffer) throws HandleException {

            }
        });
        RunnableTask receive = new RunnableTask("receive") {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("Done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        server.getTaskContainer().addNew(receive);
        receive.execute();
        return "redirect:/servers/all";
    }

    @GetMapping("/getByPort/{port}")
    public String getByPort(@PathVariable int port, Model model){
        ExtendedServer server = (ExtendedServer) serverService.getServerByPort(port);
        model.addAttribute("server", server);
        return "serverDetails";
    }
}
