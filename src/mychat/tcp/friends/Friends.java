/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mychat.tcp.friends;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static mychat.tcp.login.loginUtenti.connessioneDB;

/**
 *
 * @author giosu
 */
public class Friends {
    
    static void insertFriend(String myId, String friendId) throws SQLException {
        Connection connection = null;
        String out = "";
        try {
            connection = connessioneDB();
            PreparedStatement prepared = connection.prepareStatement("insert into amici (myId, friendId) values (?,?)");
            prepared.setString(1, myId);
            prepared.setString(2, friendId);
            prepared.executeUpdate();

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
    }
    
    static void deleteFriend(String myId, String friendId) throws SQLException {
        Connection connection = null;
        String out = "";
        try {
            connection = connessioneDB();
            PreparedStatement prepared = connection.prepareStatement("delete from amici WHERE myId = ? AND friendId = ?");
            prepared.setString(1, myId);
            prepared.setString(2, friendId);
            prepared.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/deleteFriend");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    static boolean checkPresent(String myId, String friendId) throws SQLException {
        Connection connection = null;
        boolean out=false;
        try {
            connection = connessioneDB();
            PreparedStatement prepared = connection.prepareStatement("SELECT friendId FROM amici WHERE myId = ?");
            prepared.setString(1, myId);
            prepared.execute();
            ResultSet rs = prepared.getResultSet();
            while (rs.next()) {
                if(friendId.equals(rs.getString("friendId"))){
                    out = true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/checkPresent");
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
    
    public static String getFriends(String myId){
        Connection connection = null;
        String out="";
        try {
            connection = connessioneDB();
            PreparedStatement prepared = connection.prepareStatement("SELECT friendId FROM amici WHERE myId = ?");
            prepared.setString(1, myId);
            prepared.execute();
            ResultSet rs = prepared.getResultSet();
            while (rs.next()) {
                out += rs.getString("friendId") + "~";
            }

        } catch (SQLException ex) {
            System.out.println("Errore in mychat.tcp.login/loginUtenti/getFriends");
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
}
