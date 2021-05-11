package mychat.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mychat.tcp.ClientInfo;
import mychat.tcp.messages.ClientInfosMessage;
import mychat.tcp.messages.Message;

public abstract class MyChatServer {
    private final int port;
    private ServerSocket server;
    private final List<MyChatClientHandler> clients;
    protected static final Logger logger=Logger.getLogger(MyChatServer.class.getName());
    protected boolean running;
    
    public MyChatServer(int port) {
        this.port = port;
        this.server = null;
        this.clients = new ArrayList<>();
        this.running = false;
    }
    
    public boolean isStarted(){
        return this.server!=null;
    }
    
    public void start(){
        try {
            server = new ServerSocket(port);
            logger.log(Level.INFO, "Port " + port + " is now open.");
            this.running = true;
        } catch (IOException ex) {
            if(this.running){
                notifyException(ex);
                logger.log(Level.SEVERE, "Error {0}.", ex);
            }
        }
    }
    
    public void run(){
        try{
            while(true){
                Socket client = server.accept();
                logger.log(Level.INFO, "Accept {0}.", client.getRemoteSocketAddress());
                MyChatClientHandler newClient = new MyChatClientHandler(this, client);
                this.clients.add(newClient);
                newClient.start();
            }
        } catch (IOException ex) {
            if(this.running){
                notifyException(ex);
                logger.log(Level.SEVERE, "Error {0}.", ex);
                stop();
            }
        }
    }
    
    public void stop() {
        try{
            this.running = false;
            this.server.close();
            this.clients.forEach(client -> {
                try{
                    client.closeConnection();
                } catch (IOException ex) {
                    notifyException(ex);
                    logger.log(Level.SEVERE, "Error {0}.", ex);
                }
            });
        } catch (IOException ex) {
            notifyException(ex);
            logger.log(Level.SEVERE, "Error {0}.", ex);
        }
    }
    
    void removeClient(MyChatClientHandler client){
        this.clients.remove(client);
    }
    
    void broadcastMessages(Message msg, MyChatClientHandler clientSender) {
        if(clientSender!=null){
            notifyReceiveMsg(clientSender.getClientInfo(),msg);
        }
        this.clients.forEach(client -> {
            try{
                client.sendMessage(msg);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "error sending message {0}", ex);
            }
        });
    }
    
    void broadcastAllClients(){
        List<ClientInfo> cli = new ArrayList<>();
        this.clients.forEach(client ->{
            cli.add(client.getClientInfo());
        });
        notifyUpdateClients(cli);
        ClientInfosMessage message = new ClientInfosMessage(cli);
        broadcastMessages(message, null);
    }

    public abstract void notifyUpdateClients(List<ClientInfo> users);
    public abstract void notifyReceiveMsg(ClientInfo c, Message msg);
    public abstract void notifyException(Exception ex);
    public abstract void notifyExceptionFromClientHandler(ClientInfo c,Exception ex);
}
