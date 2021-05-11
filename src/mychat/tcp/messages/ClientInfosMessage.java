package mychat.tcp.messages;

import java.util.List;
import mychat.tcp.ClientInfo;

public class ClientInfosMessage extends Message {
    public final List<ClientInfo> infos;
    
    public ClientInfosMessage(List<ClientInfo> infos) {
        this.infos=infos;
    }  
}