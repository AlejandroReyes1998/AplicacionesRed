package chatp3;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class Chat extends javax.swing.JFrame {
private String user;
MulticastClient cliente;
String[] conectados;
String textoChat="";
List<String> listaConectados;
String privateTo="";
DefaultListModel modelo;
    public Chat() {
        initComponents();
        txtChat.setContentType("text/html");
        txtChat.setEditable(false);
        txtChat.setText("");
        listaConectados=new ArrayList<>();
       
        cliente = new MulticastClient(this);
        user = JOptionPane.showInputDialog(this, "Nombre de usuario");
        if(user==null)
            System.exit(0);
        cliente.start();
        cliente.envia("<inicio>"+user);
        cliente.envia("<msg><b>"+user+"</b> se ha conectado.");
        txtEstado.setText("Usuario: "+user);
        
        listConectados.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent evt) {
               JList list =(JList)evt.getSource();
               if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    privateTo=(String)modelo.getElementAt(index);
                    String texto = JOptionPane.showInputDialog("Mensaje privado a "+privateTo);
                    if(texto!=null)
                        cliente.envia("<privado><"+user+"><"+privateTo+">"+texto);
                } else if (evt.getClickCount() == 3) {   // Triple-click
                    int index = list.locationToIndex(evt.getPoint());
                    privateTo=(String)modelo.getElementAt(index);
                    cliente.envia("<privado><"+user+"><"+privateTo+"><zumbido>");
                }
           }
        });
    }

    public void updateUsersList(String usersCSV){
    
        String[] users = usersCSV.split(",");
        modelo = new DefaultListModel();
        listConectados.removeAll();
        for(String user:users)
        {
            user.replace(" ","");
            user.trim();
            if(!user.contains("conectados") || user.isEmpty())
                modelo.addElement(user);
        }
        listConectados.setModel(modelo);
    }
    
    public void abajo() throws InterruptedException{
        putScrollBarAtEnd(jScrollPane1);
    }
    
    private void putScrollBarAtEnd(JScrollPane c){
        // Updates UI
        c.updateUI();
        c.validate();
    
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JScrollBar bar;
                bar = c.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
                c.setVerticalScrollBar(bar);
    
                // Updates UI
                c.updateUI();
                c.validate();
            }
        });
    }
    
    public void Agregar_conversacion(String msg) throws InterruptedException
    {
        String senderUser="";
        String toUser="";
        System.out.println(msg);
        msg = msg.replaceAll("hola", "<img width=\"80\" height=\"50\" src=\"file:./hola.gif\"></img>");
        msg = msg.replaceAll(":D", "<img width=\"80\" height=\"50\" src=\"file:./sonrisa.gif\"></img>");
        msg = msg.replaceAll("<3", "<img width=\"80\" height=\"50\" src=\"file:./corazon.gif\"></img>");
        msg = msg.replaceAll("beso", "<img width=\"80\" height=\"50\" src=\"file:./beso.jpg\"></img>");
        msg = msg.replaceAll("jajaja", "<img width=\"80\" height=\"50\" src=\"file:./ja.gif\"></img>");
        msg = msg.replaceAll("bye", "<img width=\"80\" height=\"50\" src=\"file:./bye.gif\"></img>");
        if(msg.contains("<msg>"))
        {
            senderUser=msg.split("<")[2].split(">")[0];
            if(senderUser.equals("b"))
                textoChat=textoChat+"<br/><br/>"+msg;
            else
                textoChat=textoChat+"<br/><br/><b>"+  senderUser  +"</b>:<br/>"+msg;
            txtChat.setText(textoChat);
        }
        else if(msg.contains("<inicio>"))
        {
            msg.replace("<inicio>", "");
            listaConectados.add(msg);
        }
        else if(msg.contains("<fin>"))
        {
            textoChat=textoChat+"<br/><br/><b>''"+msg+"'' se ha desconectado</b><br/>";
            txtChat.setText(textoChat);
        }
        else if(msg.contains("<conectados"))
        {
            updateUsersList(msg);
        }else if(msg.contains("privado"))
        {
            senderUser=msg.split("<")[2].replace(">", "");
            toUser=msg.split("<")[3].split(">")[0];
            if(toUser.equals(user))
            {
                if(msg.contains("<zumbido>")){
                    textoChat=textoChat+"<br/><br/><b>Recibio un zumbido de "+senderUser+"</b>:<br/>"+msg;
                    txtChat.setText(textoChat);
                    Point pont = this.getLocation();
                    for(int i=0; i<40; i++){
                        if(i%4 == 0){
                            int x = pont.x + 5;
                            int y = pont.y;
                            this.setLocation(x, y); 
                        }   
                        if(i%4 == 1){
                            int x = pont.x - 5;
                            int y = pont.y;
                            this.setLocation(x, y);
                        }
                        if(i%4 == 2){
                            int x = pont.x;
                            int y = pont.y + 5;
                            this.setLocation(x, y);
                        }
                        if(i%4 == 3){
                            int x = pont.x;
                            int y = pont.y - 5;
                            this.setLocation(x, y);
                        }
                        
                        Thread.sleep(10);
                    }
                }else{
                    if(msg.contains("no me ignores")){
                    textoChat=textoChat+"<br/><br/><b>Recibio un zumbido de "+senderUser+"</b>:<br/>"+msg;
                    txtChat.setText(textoChat);
                    Point pont = this.getLocation();
                    for(int i=0; i<40; i++){
                        if(i%4 == 0){
                            int x = pont.x + 5;
                            int y = pont.y;
                            this.setLocation(x, y); 
                        }   
                        if(i%4 == 1){
                            int x = pont.x - 5;
                            int y = pont.y;
                            this.setLocation(x, y);
                        }
                        if(i%4 == 2){
                            int x = pont.x;
                            int y = pont.y + 5;
                            this.setLocation(x, y);
                        }
                        if(i%4 == 3){
                            int x = pont.x;
                            int y = pont.y - 5;
                            this.setLocation(x, y);
                        }
                        
                        Thread.sleep(10);
                    }
                }
                    else{
                    textoChat=textoChat+"<br/><br/><b>Mensaje privado de "+senderUser+"</b>:<br/>"+msg;
                    txtChat.setText(textoChat);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listConectados = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtEnvia = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        txtEstado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtChat = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ESCOMChat");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Contactos");

        listConectados.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(listConectados);

        txtEnvia.setColumns(20);
        txtEnvia.setRows(5);
        txtEnvia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEnviaKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txtEnvia);

        btnEnviar.setBackground(new java.awt.Color(204, 204, 204));
        btnEnviar.setForeground(new java.awt.Color(153, 153, 153));
        btnEnviar.setText("Enviar");
        btnEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviar.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        txtEstado.setFont(new java.awt.Font("Yu Gothic Light", 1, 24)); // NOI18N
        txtEstado.setForeground(new java.awt.Color(0, 102, 153));
        txtEstado.setText("Desconectado");

        jScrollPane1.setAutoscrolls(true);

        txtChat.setEditable(false);
        jScrollPane1.setViewportView(txtChat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:

        String texto=txtEnvia.getText();
        cliente.envia("<msg><"+user+">"+texto);
        txtEnvia.setText("");
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        cliente.envia("<fin>"+user);
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void txtEnviaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEnviaKeyTyped
        // TODO add your handling code here:
        char tecla = evt.getKeyChar();
        if(tecla == KeyEvent.VK_ENTER){
            btnEnviar.doClick();
        }
    }//GEN-LAST:event_txtEnviaKeyTyped


    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList listConectados;
    private javax.swing.JEditorPane txtChat;
    private javax.swing.JTextArea txtEnvia;
    private javax.swing.JLabel txtEstado;
    // End of variables declaration//GEN-END:variables
}

