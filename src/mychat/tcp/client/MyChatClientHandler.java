package mychat.tcp.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import mychat.tcp.ClientInfo;
import mychat.tcp.ConnectionHandler;
import mychat.tcp.messages.ClientInfosMessage;
import mychat.tcp.messages.Message;
import mychat.tcp.messages.MsgMessage;

public class MyChatClientHandler extends ConnectionHandler{
    MyChatClient client;
    private List<ClientInfo> infos;

    public MyChatClientHandler(MyChatClient client, String host, int port) throws IOException {        
        super(new Socket(host, port));
        this.client=client;
        this.infos=new ArrayList<>();
    }
    
    @Override
    protected void processMessage(Message message) {
        if (message instanceof MsgMessage) {
            MsgMessage m=(MsgMessage)message;
            client.notifyReceivedMessage(m.client_info,m.msg);
        } else if (message instanceof ClientInfosMessage) {
            ClientInfosMessage m=(ClientInfosMessage)message;
            infos=m.infos;
            client.notifyUpdateClients(infos);
        }     
    }

    @Override
    protected void notifyClosingConnection() {
        client.notifyClosingConnection();
    }

    @Override
    public void notifyException(Exception ex) {
        client.notifyException(ex);
    }
}
