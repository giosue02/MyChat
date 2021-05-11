package mychat.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mychat.tcp.messages.Message;

public abstract class ConnectionHandler extends Thread {

    protected Socket socket;
    public ClientInfo info;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;
    protected static final Logger logger = Logger.getLogger(ConnectionHandler.class.getName());

    public ConnectionHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.info = new ClientInfo("nickname");
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    public String getDescription() {
        return this.socket.getRemoteSocketAddress().toString();
    }

    public ClientInfo getClientInfo() {
        return this.info;
    }

    protected void setClientInfo(ClientInfo info) {
        this.info = info;
    }

    public void sendMessage(Message msg) throws IOException {
        ConnectionHandler.logger.log(Level.INFO, "sending {0}", msg);
        this.output.writeObject(msg);
        this.output.flush();
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException {
        Message msg = (Message) input.readObject();
        ConnectionHandler.logger.log(Level.INFO, "received {0}", msg);
        return msg;
    }

    public void closeConnection() throws IOException {
        this.socket.close();
    }

    @Override
    public void run() {
        
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message;
                message = this.receiveMessage();
                this.processMessage(message);
            }
        } catch (IOException ex) {
            notifyException(ex);
            ConnectionHandler.logger.log(Level.INFO, "close connection {0}", this.getDescription());
        } catch (ClassNotFoundException ex) {
            notifyException(ex);
            ConnectionHandler.logger.log(Level.SEVERE, "unknows message {0}", ex);
        } finally {
            this.notifyClosingConnection();
            try {
                this.closeConnection();
            } catch (IOException ex) {
                notifyException(ex);
                ConnectionHandler.logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    protected abstract void processMessage(Message message);

    protected abstract void notifyClosingConnection();

    public abstract void notifyException(Exception ex);
}
