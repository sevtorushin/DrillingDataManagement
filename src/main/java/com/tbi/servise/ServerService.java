package com.tbi.servise;

import org.springframework.stereotype.Service;
import servers.another.Server;
import service.containers.ServerPool;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class ServerService {
    private final ServerPool serverPool;

    public ServerService(ServerPool serverPool) {
        this.serverPool = serverPool;
    }

    public Server createServer(Class<? extends Server> serverClass, Integer port){
        Server server = null;
        try {
            server = serverClass.getConstructor(Integer.class).newInstance(port);
            server.setCheckClients(true);
            server.start();
            serverPool.addNew(server);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return server;
    }

    public void addServer(Server server){
        server.start();
        serverPool.addNew(server);
    }

    public List<Server> getAllServers(){
        return serverPool.getAll();
    }

    public Server getServerByPort(Integer port){
        return serverPool.getOnLocalPort(port);
    }
}
