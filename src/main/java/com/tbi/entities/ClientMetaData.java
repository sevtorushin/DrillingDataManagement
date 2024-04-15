package com.tbi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ClientMetaData {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Object id;
    @Getter
    @Setter
    private String serverHost;
    @Getter
    @Setter
    private int serverPort;
    @Getter
    @Setter
    private Boolean autoReconnect;
}
