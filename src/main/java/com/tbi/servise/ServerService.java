package com.tbi.servise;

import org.springframework.stereotype.Service;
import servers.another.ExtendedServer;
import servers.another.Server;
import service.IdentifiableTask;
import service.containers.ServerPool;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class ServerService {
    private final ServerPool serverPool;

    public ServerService(ServerPool serverPool) {
        this.serverPool = serverPool;
    }

    public Server createServer(Class<? extends Server> serverClass, Integer port, String name, Object id, boolean checkAliveClients, Integer maxNumberClients) {
        Server server = null;
        try {
            if (maxNumberClients != null)
                server = serverClass.getConstructor(Integer.class, int.class).newInstance(port, maxNumberClients);
            else
                server = serverClass.getConstructor(Integer.class).newInstance(port);
            if (!name.isBlank())
                server.setName(name);
            if (!id.toString().isBlank())
                server.setId(id);
            server.setCheckClients(checkAliveClients);
            serverPool.addNew(server);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return server;
    }

    public void startServer(Server server) {
        server.start();
    }

    public void stopServer(Server server) {
        try {
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addServer(Server server) {
        server.start();
        serverPool.addNew(server);
    }

    public void removeServer(Server server) {
        serverPool.remove(server);
    }

    public List<Server> getAllServers() {
        return serverPool.getAll();
    }

    public Server getServerByPort(Integer port) {
        return serverPool.getOnLocalPort(port);
    }

    public void addTask(ExtendedServer server, IdentifiableTask<Object, ?> task) {

        server.getTaskContainer().addNew(task);
    }
}
