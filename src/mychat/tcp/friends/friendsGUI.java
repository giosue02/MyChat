package mychat.tcp.friends;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mychat.tcp.friends.Friends.checkPresent;
import static mychat.tcp.gui.MyChatServerGUI.registeredUserList;
import mychat.tcp.login.loginUtenti;
import mychat.tcp.gui.MyChatClientGUI;
import static mychat.tcp.gui.MyChatClientGUI.friendsList;

/**
 *
 * @author giosu
 */
public class friendsGUI extends javax.swing.JFrame {
    static String username="";
    
    public friendsGUI(String username) {
        initComponents();
        this.username = username;
        String lastCharacter= username.substring(username.length()-1);
        if(lastCharacter.equals("s")){
            lblTitle.setText(username + "' friends");
        }else{
            lblTitle.setText(username + "'s friends");
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        txtFriend = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        lblMessage = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();

        txtFriend.setMinimumSize(new java.awt.Dimension(6, 28));
        txtFriend.setPreferredSize(new java.awt.Dimension(42, 28));
        txtFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFriendActionPerformed(evt);
            }
        });

        jLabel2.setText("Nickname");

        btnAdd.setText("Add friend");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove friend");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Friends");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(135, 135, 135))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitle)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(btnAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemove))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMessage)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(103, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFriend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnRemove))
                .addGap(53, 53, 53)
                .addComponent(lblMessage)
                .addGap(27, 27, 27))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFriendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFriendActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String new_friend = txtFriend.getText();
        String utenti[] = loginUtenti.RegisteredUsers().split("~");
        boolean trovato = false;
        boolean himself = false;
        String myId = "", friendId="";
        for(int i=0; i<utenti.length; i++){
            String infos[] = utenti[i].split("#");
            if(new_friend.equals(infos[1])){
                trovato = true;
                friendId = infos[0];
            }
            if(new_friend.equals(username)){
                himself=true;
            }
            if(username.equals(infos[1])){
                myId = infos[0];
            }
        }
        if(himself){
            lblMessage.setText("You can't add yourself!");
        }else if(!trovato){
            lblMessage.setText("Friend not found!");
        }else{
            try {
                if(checkPresent(myId, friendId)){
                    lblMessage.setText(new_friend + " is already your friend!");
                }else{
                    try {
                        Friends.insertFriend(myId, friendId);
                        lblMessage.setText("Friend succesfully added!");
                        txtFriend.setText("");
                        
                        //AGGIORNO LISTA AMICI
                        String friend_list="Friends: \n";
                        String name="";
                        String friends[] = Friends.getFriends(myId).split("~");
                        for(int i=0; i<friends.length;i++){
                            name = loginUtenti.getUsername(friends[i]);
                            friend_list+="@"+name+"\n";
                        }
                        if(!name.equals("")){
                            friendsList.setText(friend_list);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(friendsGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(friendsGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        String del_friend = txtFriend.getText();
        String utenti[] = loginUtenti.RegisteredUsers().split("~");
        boolean trovato = false;
        String myId = "", friendId="";
        for(int i=0; i<utenti.length; i++){
            String infos[] = utenti[i].split("#");
            if(del_friend.equals(infos[1])){
                trovato = true;
                friendId = infos[0];
            }
            if(username.equals(infos[1])){
                myId = infos[0];
            }
        }
        if(!trovato){
            lblMessage.setText("Friend not found!");
        }else{
            String amici[] = Friends.getFriends(myId).split("~");
            boolean isAmico = false;
            for(int i=0; i<amici.length; i++){
                if(friendId.equals(amici[i])){
                    isAmico = true;
                }
            }
            if(!isAmico){
                lblMessage.setText(del_friend + " is not your friend!");
            }else{
                try {
                    Friends.deleteFriend(myId, friendId);
                    lblMessage.setText("Friend successfully removed");
                    txtFriend.setText("");
                    //AGGIORNO LISTA AMICI
                    String friend_list="Friends: \n";
                    String name="";
                    String friends[] = Friends.getFriends(myId).split("~");
                    for(int i=0; i<friends.length;i++){
                        name = loginUtenti.getUsername(friends[i]);
                        friend_list+="@"+name+"\n";
                    }
                    if(!name.equals("")){
                        friendsList.setText(friend_list);
                    }else{
                        friendsList.setText("Friends");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(friendsGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InstantiationException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(friendsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(friendsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(friendsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new friendsGUI(username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtFriend;
    // End of variables declaration//GEN-END:variables
}
