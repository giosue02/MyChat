package mychat.tcp.messages;

import mychat.tcp.ClientInfo;

public class MsgMessage extends Message{
    public final ClientInfo client_info;
    public final String msg;

    public MsgMessage(ClientInfo client_info, String msg) {
        this.client_info = client_info;
        this.msg = msg;
    }
}
