package com.tbi.controllers;

import com.tbi.entities.ServerMetaData;
import com.tbi.servise.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import servers.another.ExtendedServer;
import servers.another.Server;

@Controller
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
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
    public String createAndRun(@ModelAttribute("metaData") ServerMetaData metaData) {
        ExtendedServer server = (ExtendedServer) serverService.createServer(
                ExtendedServer.class, metaData.getPort(), metaData.getName(), metaData.getId(), true, metaData.getMaxNumberClients());
        serverService.startServer(server);
        return "redirect:/servers/all";
    }

    @GetMapping("/getByPort/{port}")
    public String getByPort(@PathVariable int port, Model model) {
        ExtendedServer server = (ExtendedServer) serverService.getServerByPort(port);
        model.addAttribute("server", server);
        return "serverDetails";
    }

    @GetMapping("/stop/{port}")
    public String stopServer(@PathVariable int port) {
        Server server = serverService.getServerByPort(port);
        serverService.stopServer(server);
        return "redirect:/servers/getByPort/" + port;
    }

    @GetMapping("/start/{port}")
    public String startServer(@PathVariable int port) {
        Server server = serverService.getServerByPort(port);
        serverService.startServer(server);
        return "redirect:/servers/getByPort/" + port;
    }

    @GetMapping("/drop/{port}")
    public String dropServer(@PathVariable int port) {
        Server server = serverService.getServerByPort(port);
        serverService.removeServer(server);
        return "redirect:/servers/all";
    }
}
