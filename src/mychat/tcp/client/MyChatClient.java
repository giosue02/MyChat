package mychat.tcp.client;

import java.io.IOException;
import java.util.List;
import mychat.tcp.ClientInfo;
import mychat.tcp.messages.ClientInfoMessage;
import mychat.tcp.messages.Message;
import mychat.tcp.messages.MsgMessage;

public abstract class MyChatClient {
    MyChatClientHandler handler;
    protected String serverName;
    protected String nickname;
    protected int port;
    
    public MyChatClient(String host, int port, String nickname) {
        this.serverName=host;
        this.port = port;
        this.nickname=nickname;
    }  
    
    public void notifyClosingConnection() {
    }

    public void sendMessage(String msg) throws IOException {
        MsgMessage message=new MsgMessage(this.handler.getClientInfo(),msg);
        this.handler.sendMessage(message);
    }

    public void start() throws IOException {
        handler=new MyChatClientHandler(this,serverName,port);
        handler.info=new ClientInfo(this.nickname);
        this.handler.start();
        Message m=new ClientInfoMessage(this.handler.getClientInfo());
        this.handler.sendMessage(m);
    }

    public void run() throws InterruptedException {
        this.handler.join();        
    }

    public void closeConnection() throws IOException {
        this.handler.closeConnection();
        this.handler=null;
    }
    
    public boolean isConnected() {
        return this.handler!=null;
    }
    
    public abstract void notifyUpdateClients(List<ClientInfo> users);
    public abstract void notifyReceivedMessage(ClientInfo from, String msg);  
    public abstract void notifyException(Exception ex);
}
