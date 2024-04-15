package com.tbi.servise;

import clients.another.Client;
import clients.another.ExtendedClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.IdentifiableMessageHandler;
import service.IdentifiableTask;
import service.containers.ClientPool;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientService {
    private final ClientPool clientPool;

    public List<Client> getAllClients() {
        return clientPool.getAll();
    }

    public void addTaskForConnectedClient(ExtendedClient forConnectedClient, IdentifiableTask<Object, ?> task) {
        forConnectedClient.getTaskContainer().addNew(task);
    }

    public void addHandlerForConnectedClient(ExtendedClient forConnectedClient, IdentifiableMessageHandler<Object, ByteBuffer> handler){
        forConnectedClient.getHandlerContainer().addNew(handler);
    }

    public ExtendedClient getClientById(Object id) {
        return (ExtendedClient) clientPool.get(id);
    }

    public Client createClient(Class<? extends Client> clientClass, Object id, String name, String serverHost, int serverPort, boolean autoReconnect) {
        Client client = null;
        try {
            client = clientClass.getConstructor(String.class, int.class).newInstance(serverHost, serverPort);
            if (!name.isBlank())
                client.setName(name);
            if (!id.toString().isBlank())
                client.setId(id);
            client.getClientConnection().setReconnectionMode(autoReconnect);
            clientPool.addNew(client);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return client;
    }

    public void connectClient(Client client){
        client.connect();
    }

    public void disconnectClient(Client client){
        client.disconnect();
    }

    public void removeClient(Client client){
        clientPool.remove(client);
    }
}
