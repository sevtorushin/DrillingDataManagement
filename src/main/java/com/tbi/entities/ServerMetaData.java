package com.tbi.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ServerMetaData {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Object id;
    @Getter
    @Setter
    private Integer port;
    @Getter
    @Setter
    private Integer maxNumberClients;

    public ServerMetaData() {
    }
}
