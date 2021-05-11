package mychat.tcp;

public class ClientInfo implements java.io.Serializable{
    public String nickname;
    public ClientInfo(String nickname){
        this.nickname = nickname;
    }
}
