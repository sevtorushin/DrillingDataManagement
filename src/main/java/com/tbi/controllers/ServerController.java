package com.tbi.controllers;

import clients.another.Client;
import com.tbi.entities.ServerMetaData;
import com.tbi.servise.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import servers.another.ExtendedServer;
import servers.another.Server;

@Controller
@RequestMapping("/servers")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @GetMapping("/all")
    public String getAllServers(Model model) {
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

    @GetMapping("/details/{port}")
    public String getByPort(@PathVariable int port, Model model) {
        ExtendedServer server = (ExtendedServer) serverService.getServerByPort(port);
        model.addAttribute("server", server);
        return "serverDetails";
    }

    @GetMapping("/stop/{port}")
    public String stopServer(@PathVariable int port) {
        Server server = serverService.getServerByPort(port);
        serverService.stopServer(server);
        return "redirect:/servers/details/" + port;
    }

    @GetMapping("/start/{port}")
    public String startServer(@PathVariable int port) {
        Server server = serverService.getServerByPort(port);
        serverService.startServer(server);
        return "redirect:/servers/details/" + port;
    }

    @GetMapping("/drop/{port}")
    public String dropServer(@PathVariable int port) {
        Server server = serverService.getServerByPort(port);
        serverService.removeServer(server);
        return "redirect:/servers/all";
    }

    @GetMapping("/details/{server_port}/pool")
    public String getByPort(@PathVariable int server_port,
                            @RequestParam(value = "id", required = false) Integer id,
                            Model model) {
        Server server = serverService.getServerByPort(server_port);
        Client client = server.getClientPool().get(id);
        model.addAttribute("client", client);
        model.addAttribute("server", server);
        return "clientDetails";
    }

    @GetMapping("/details/{server_port}/pool/drop/{id}")
    public String dropClient(@PathVariable int server_port,
                             @PathVariable Integer id){
        Server server = serverService.getServerByPort(server_port);
        Client client = server.getClientPool().get(id);
        serverService.dropClient(server, client);
        return "redirect:/servers/details/" + server_port;
    }
}
