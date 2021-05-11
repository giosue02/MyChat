package mychat.tcp.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author giacomo.pizzini
 */
public class loginUtenti {

    public static Connection connessioneDB() {

        String connectionString = "jdbc:mysql://localhost:3306/mychatdbutenti";
        Connection connection = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    static boolean login(String userName, String userPassword){
        boolean trovato = false;
        Connection connection = null;
        try {
            connection = connessioneDB();
            java.sql.Statement stm = connection.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM utente");

            while (rs.next()) {
                if (userName.equals(rs.getString("username"))) {
                    if (userPassword.equals(rs.getString("password"))) {
                        trovato = true;
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/login");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }

        return trovato;
    }

    static String register(String userName, String userPassword) {
        Connection connection = null;
        String out = "";
        try {
            connection = connessioneDB();

            //verifico che il nome utente non sia già stato usato
            java.sql.Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM utente");
            boolean trovato = false;
            while (rs.next()) {
                if (userName.equals(rs.getString("username"))) {
                        trovato = true;
                }
            }
            //agisco in base al risultato
            if (trovato == false) {
                PreparedStatement prepared = connection.prepareStatement("insert into utente (username, password) values (?,?)");
                prepared.setString(1, userName);
                prepared.setString(2, userPassword);
                prepared.executeUpdate();
                out = "Registration successful. Now you need to login.";
            } else {
                out = "Registration failed: there is already a user with this username.";
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/register");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return out;
    }
    
    public static boolean utenteIsEmpty(){
        Connection connection = null;
        boolean out=false;
        try {
            connection = connessioneDB();

            //verifico che il nome utente non sia già stato usato
            java.sql.Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) AS NumRow FROM utente");
            while (rs.next()) {
                if(rs.getString("NumRow").equals("0")){
                    out=true;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/RegisteredUsers");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return out;
    }
    
    public static String RegisteredUsers(){
        Connection connection = null;
        String out="";
        try {
            connection = connessioneDB();

            //verifico che il nome utente non sia già stato usato
            java.sql.Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM utente");
            while (rs.next()) {
                String res = rs.getString("id")+"#"+rs.getString("username");
                out+=res+"~";
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/RegisteredUsers");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        System.out.println("out prima: " + out);
        if(out.equals("")){
            out="vuoto";
        }
        System.out.println("out dopo: " + out);
        return out;
    } 
    
    public static void InsertMsg(String id_sender, String id_recipient, String msg){
        Connection connection = null;
        try {
            connection = connessioneDB();

            PreparedStatement prepared = connection.prepareStatement("insert into chat (sender, recipient, message) values (?,?,?)");
            prepared.setString(1, id_sender);
            prepared.setString(2, id_recipient);
            prepared.setString(3, (msg));
            prepared.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/InsertMsg");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public static String getMsgs(String id){
        Connection connection = null;
        String out="";
        try {
            connection = connessioneDB();
            if(id.equals("server")){
                PreparedStatement prepared = connection.prepareStatement("SELECT message FROM chat");
                prepared.execute();
                ResultSet rs = prepared.getResultSet();
                while (rs.next()) {
                    String res = rs.getString("message");
                    out+=res+"~";
                }
            }else{
                PreparedStatement prepared = connection.prepareStatement("SELECT message FROM chat WHERE sender = ? OR recipient = ? OR (sender is NULL AND recipient is NULL)");
                prepared.setString(1, id);
                prepared.setString(2, id);
                prepared.execute();
                ResultSet rs = prepared.getResultSet();
                while (rs.next()) {
                    String res = rs.getString("message");
                    out+=res+"~";
                }
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/RegisteredUsers");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return out;
    }
    
    public static String getUsername(String id){
        Connection connection = null;
        String out="";
        try {
            connection = connessioneDB();
            PreparedStatement prepared = connection.prepareStatement("SELECT username FROM utente WHERE id = ?");
            prepared.setString(1, id);
            prepared.execute();
            ResultSet rs = prepared.getResultSet();
            while (rs.next()) {
                out = rs.getString("username");
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/RegisteredUsers");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return out;
    }
    
    public static String getId(String username){
        Connection connection = null;
        String out="";
        try {
            connection = connessioneDB();
            PreparedStatement prepared = connection.prepareStatement("SELECT id FROM utente WHERE username = ?");
            prepared.setString(1, username);
            prepared.execute();
            ResultSet rs = prepared.getResultSet();
            while (rs.next()) {
                out = rs.getString("id");
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/RegisteredUsers");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        
        System.out.println(out);
        return out;
    }
}
