package com.tbi.controllers;

import clients.another.Client;
import clients.another.ExtendedClient;
import com.tbi.entities.ClientMetaData;
import com.tbi.servise.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/all")
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "clients";
    }

    @GetMapping("/create")
    public String newClient(@ModelAttribute("metaData") ClientMetaData metaData) {
        return "createClient";
    }

    @PostMapping("/create")
    public String createAndConnect(@ModelAttribute("metaData") ClientMetaData metaData) {
        ExtendedClient client = (ExtendedClient) clientService.createClient(
                ExtendedClient.class, metaData.getId(), metaData.getName(), metaData.getServerHost(), metaData.getServerPort(), metaData.getAutoReconnect());
        clientService.connectClient(client);
        return "redirect:/clients/all";
    }

    @GetMapping("/connect/{id}")
    public String connect(@PathVariable Integer id) {
        Client client = clientService.getClientById(id);
        clientService.connectClient(client);
        return "redirect:/clients/details/" + id;
    }

    @GetMapping("/disconnect/{id}")
    public String disconnect(@PathVariable Integer id) {
        Client client = clientService.getClientById(id);
        clientService.disconnectClient(client);
        return "redirect:/clients/details/" + id;
    }

    @GetMapping("/drop/{id}")
    public String drop(@PathVariable Integer id) {
        Client client = clientService.getClientById(id);
        clientService.removeClient(client);
        return "redirect:/clients/all";
    }

    @GetMapping("/details/{id}")
    public String getByPort(@PathVariable Integer id, Model model) {
        ExtendedClient client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "clientDetails";
    }
}
