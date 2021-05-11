package mychat.tcp.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import mychat.tcp.ConnectionHandler;
import mychat.tcp.messages.ClientInfoMessage;
import mychat.tcp.messages.Message;
import mychat.tcp.messages.MsgMessage;

public class MyChatClientHandler extends ConnectionHandler{
    private final MyChatServer server;

    public MyChatClientHandler(MyChatServer server, Socket client) throws IOException {
        super(client);
        this.server = server;
    }

    @Override
    protected void processMessage(Message message) {
        if(message instanceof MsgMessage){
            this.server.broadcastMessages(message, this);
        } else if(message instanceof ClientInfoMessage){
            ClientInfoMessage m = (ClientInfoMessage)message;
            this.setClientInfo(m.info);
            this.server.broadcastAllClients();
        }
    }

    @Override
    protected void notifyClosingConnection() {
        if(this.server.running) {
            this.server.removeClient(this);
            this.server.broadcastAllClients();
        }
    }

    @Override
    public void notifyException(Exception ex) {
        server.notifyExceptionFromClientHandler(this.info, ex);
    }
}
