package mychat.tcp.messages;

import mychat.tcp.ClientInfo;

public class ClientInfoMessage extends Message {
    public final ClientInfo info;
    
    public ClientInfoMessage(ClientInfo info) {
        this.info=info;
    }    
}
