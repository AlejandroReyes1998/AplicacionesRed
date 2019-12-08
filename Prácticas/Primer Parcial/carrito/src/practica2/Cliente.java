/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.DataInputStream;
import sharedClasses.Producto;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cliente extends javax.swing.JFrame {

    private ArrayList<Producto> catalogo;
    private DefaultTableModel modelo;
    private ArrayList<Producto> compra;
    private int unisamsung;
    private int unisony;
    private int unipanasonic;
    private int unitashi;
    private int unisharp;
    private int unilg;
    private String samsung;
    private String sony;
    private String panasonic;
    private String tashi;
    private String sharp;
    private String lg;

    /**
     * Creates new form Cliente
     */
    public Cliente() {
        unisamsung = 0;
        unisony = 0;
        unipanasonic = 0;
        unitashi = 0;
        unisharp = 0;
        unilg = 0;
        initComponents();
        this.actualizarButton.setVisible(false);
        this.compra = new ArrayList();
        this.catalogo = new ArrayList();
        this.pideCatalogo();
        modelo = new MyModel();
        jTable1.setDefaultRenderer(Object.class, new ImagenTabla());
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio");
        modelo.addColumn("Existencias");
        modelo.addColumn("Compras");
        modelo.addColumn("Foto");
        for (int i = 0; i < catalogo.size(); i++) {
            modelo.addRow(catalogo.get(i).getDatos());

        }
        
        ImageIcon imagen = new ImageIcon(getClass().getResource(samsung));
        modelo.setValueAt(new JLabel(imagen), 0, 5);
        ImageIcon imagen2 = new ImageIcon(getClass().getResource(sony));
        modelo.setValueAt(new JLabel(imagen2), 1, 5);
        imagen = new ImageIcon(getClass().getResource(panasonic));
        modelo.setValueAt(new JLabel(imagen), 2, 5);
        imagen = new ImageIcon(getClass().getResource(tashi));
        modelo.setValueAt(new JLabel(imagen), 3, 5);
        imagen = new ImageIcon(getClass().getResource(sharp));
        modelo.setValueAt(new JLabel(imagen), 4, 5);
        imagen = new ImageIcon(getClass().getResource(lg));
        modelo.setValueAt(new JLabel(imagen), 5, 5);
        jTable1.setRowHeight(180);
        jTable1.setModel(modelo);
    }
    /**
     *
     * @variables
     * <Variables predeterminadas en el sistema>
     * final int PORT <Puerto al que se ha de comunicar el cliente >
     * final int SERVER <Servidor al que se comunica el cliente>
     * final int SEND <Codigo de envio hacia el servidor>
     *
     */
    public final String SERVER = new String("127.0.0.1");
    public final int PORT = 9090, SEND = 1;

    /**
     * Metodo Compra
     *
     */
    String total = "";
    double suma = 0;

    public void comprarCosas() {
        try {

            Socket cliente = new Socket(InetAddress.getByName(SERVER), PORT);
            cliente.setSoLinger(true, 1);
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            //JOptionPane.showMessageDialog(null,""+cliente.getOutputStream().t);
            oos.writeObject("2");
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            oos.writeInt(this.catalogo.size());
            for (int i = 0; i < this.modelo.getRowCount(); i++) {
                
                //JOptionPane.showMessageDialog(null,""+this.modelo.getValueAt(i, 4));

                if (i == 0) {
                    if (Integer.parseInt((String) this.modelo.getValueAt(i, 4)) <= Integer.parseInt((String) this.modelo.getValueAt(i, 3)) && Integer.parseInt((String) this.modelo.getValueAt(i, 4)) != 0) {
                        unisamsung += Integer.parseInt((String) this.modelo.getValueAt(i, 4));
                        JOptionPane.showMessageDialog(null, "El producto SAMSUNG fue exitosamente agregado al carrito");
                    } else if(Integer.parseInt((String) this.modelo.getValueAt(i, 4)) > Integer.parseInt((String) this.modelo.getValueAt(i, 3))){
                        JOptionPane.showMessageDialog(null, "La cantidad de elementos que requieres del producto SAMSUNG no se encuentra en almacen", "ERROR", JOptionPane.ERROR_MESSAGE);
                        this.modelo.setValueAt("0", i, 4);
                    }
                }
                if (i == 1) {
                    if (Integer.parseInt((String) this.modelo.getValueAt(i, 4)) <= Integer.parseInt((String) this.modelo.getValueAt(i, 3))&& Integer.parseInt((String) this.modelo.getValueAt(i, 4)) != 0) {
                        unisony += Integer.parseInt((String) this.modelo.getValueAt(i, 4));
                        JOptionPane.showMessageDialog(null, "El producto SONY fue exitosamente agregado al carrito");
                    } else if(Integer.parseInt((String) this.modelo.getValueAt(i, 4)) > Integer.parseInt((String) this.modelo.getValueAt(i, 3))){
                        JOptionPane.showMessageDialog(null, "La cantidad de elementos que requieres del producto SONY no se encuentra en almacen", "ERROR", JOptionPane.ERROR_MESSAGE);
                        this.modelo.setValueAt("0", i, 4);
                    }
                }

                if (i == 2) {
                    if (Integer.parseInt((String) this.modelo.getValueAt(i, 4)) <= Integer.parseInt((String) this.modelo.getValueAt(i, 3))&& Integer.parseInt((String) this.modelo.getValueAt(i, 4)) != 0) {
                        unipanasonic += Integer.parseInt((String) this.modelo.getValueAt(i, 4));
                        JOptionPane.showMessageDialog(null, "El producto PANASONIC fue exitosamente agregado al carrito");
                    } else if(Integer.parseInt((String) this.modelo.getValueAt(i, 4)) > Integer.parseInt((String) this.modelo.getValueAt(i, 3))){
                        JOptionPane.showMessageDialog(null, "La cantidad de elementos que requieres del producto PANASONIC no se encuentra en almacen", "ERROR", JOptionPane.ERROR_MESSAGE);
                        this.modelo.setValueAt("0", i, 4);
                    }
                }

                if (i == 3) {
                    if (Integer.parseInt((String) this.modelo.getValueAt(i, 4)) <= Integer.parseInt((String) this.modelo.getValueAt(i, 3))&& Integer.parseInt((String) this.modelo.getValueAt(i, 4)) != 0) {
                        unitashi += Integer.parseInt((String) this.modelo.getValueAt(i, 4));
                        JOptionPane.showMessageDialog(null, "El producto ITASHI fue exitosamente agregado al carrito");
                    } else if(Integer.parseInt((String) this.modelo.getValueAt(i, 4)) > Integer.parseInt((String) this.modelo.getValueAt(i, 3))){
                        JOptionPane.showMessageDialog(null, "La cantidad de elementos que requieres del producto ITASHI no se encuentra en almacen", "ERROR", JOptionPane.ERROR_MESSAGE);
                        this.modelo.setValueAt("0", i, 4);
                    }
                }

                if (i == 4) {
                    if (Integer.parseInt((String) this.modelo.getValueAt(i, 4)) <= Integer.parseInt((String) this.modelo.getValueAt(i, 3))&& Integer.parseInt((String) this.modelo.getValueAt(i, 4)) != 0) {
                        unisharp += Integer.parseInt((String) this.modelo.getValueAt(i, 4));
                        JOptionPane.showMessageDialog(null, "El producto SHARP fue exitosamente agregado al carrito");
                    } else if(Integer.parseInt((String) this.modelo.getValueAt(i, 4)) > Integer.parseInt((String) this.modelo.getValueAt(i, 3))){
                        JOptionPane.showMessageDialog(null, "La cantidad de elementos que requieres del producto SHARP no se encuentra en almacen", "ERROR", JOptionPane.ERROR_MESSAGE);
                        this.modelo.setValueAt("0", i, 4);
                    }
                }

                if (i == 5) {
                    if (Integer.parseInt((String) this.modelo.getValueAt(i, 4)) <= Integer.parseInt((String) this.modelo.getValueAt(i, 3))&& Integer.parseInt((String) this.modelo.getValueAt(i, 4)) != 0) {
                        unilg += Integer.parseInt((String) this.modelo.getValueAt(i, 4));
                        JOptionPane.showMessageDialog(null, "El producto LG fue exitosamente agregado al carrito");
                    } else if(Integer.parseInt((String) this.modelo.getValueAt(i, 4)) > Integer.parseInt((String) this.modelo.getValueAt(i, 3))){
                        JOptionPane.showMessageDialog(null, "La cantidad de elementos que requieres del producto LG no se encuentra en almacen", "ERROR", JOptionPane.ERROR_MESSAGE);
                        this.modelo.setValueAt("0", i, 4);
                    }
                }
                catalogo.get(i).setCompra(Integer.parseInt((String) this.modelo.getValueAt(i, 4)));
                oos.writeObject(catalogo.get(i));
            }

            total = (String) ois.readObject();
            suma += Double.parseDouble(total);

            if (suma >= 0) {
                totalCompra.setText("" + suma);
                JOptionPane.showMessageDialog(null, "Compra exitosa");
                this.actualizaCatalogo();
                this.actualizaCatalogo();
            }
            //this.actualizaCatalogo();
            else{
            //  JOptionPane.showMessageDialog(null, "La cantidad de elementos que requieres no se encuentra en almacen", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo ActualizaCatalogo <Invoca a pidecatalogo y actualiza las filas de
     * la tabla>
     *
     * @return ArrayList
     *
     */
    public ArrayList<Producto> actualizaCatalogo() {
        System.out.println(this.modelo.getRowCount());
        if (this.modelo.getRowCount() > 0) {
            for (int i = this.modelo.getRowCount() - 1; i > -1; i--) {
                this.modelo.removeRow(i);
            }

        }
        for (int i = 0; i < catalogo.size(); i++) {
            modelo.addRow(catalogo.get(i).getDatos());
        }
        ImageIcon imagen = new ImageIcon(getClass().getResource(samsung));
        modelo.setValueAt(new JLabel(imagen), 0, 5);
        ImageIcon imagen2 = new ImageIcon(getClass().getResource(sony));
        modelo.setValueAt(new JLabel(imagen2), 1, 5);
        imagen = new ImageIcon(getClass().getResource(panasonic));
        modelo.setValueAt(new JLabel(imagen), 2, 5);
        imagen = new ImageIcon(getClass().getResource(tashi));
        modelo.setValueAt(new JLabel(imagen), 3, 5);
        imagen = new ImageIcon(getClass().getResource(sharp));
        modelo.setValueAt(new JLabel(imagen), 4, 5);
        imagen = new ImageIcon(getClass().getResource(lg));
        modelo.setValueAt(new JLabel(imagen), 5, 5);
        jTable1.setRowHeight(180);
        jTable1.setModel(modelo);
        return this.pideCatalogo();
    }

    /**
     * Metodo PideCatalogo <Entrega el catalogo de productos>
     *
     * @param <ArrayList>
     * @return
     */
    public ArrayList<Producto> pideCatalogo() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {

            /*
             * Creamos el socket y pedimos el catalogo
             */
            Socket cliente = new Socket(InetAddress.getByName(SERVER), PORT);
            System.out.println("Conectado;");
            cliente.setSoLinger(true, 1);
            oos = new ObjectOutputStream(cliente.getOutputStream());
            //Código de petición de catalogo
            oos.writeObject("1");
            ois = new ObjectInputStream(cliente.getInputStream());
            int numero = Integer.parseInt((String) ois.readObject());
            System.out.println("Recibi: " + numero);

            /*
             * Borramos el arrayList y la tabla
             */
            this.catalogo.clear();

            for (int i = 0; i < numero; i++) {
                Producto prod = (Producto) ois.readObject();
                this.catalogo.add(prod);
                System.out.println("Recibi producto: " + catalogo.get(i).getNombre());
            }
            DataInputStream dis = new DataInputStream(cliente.getInputStream());
            samsung = dis.readUTF();
            sony = dis.readUTF();
            panasonic = dis.readUTF();
            tashi = dis.readUTF();
            sharp = dis.readUTF();
            lg = dis.readUTF();
            cliente.close();
            return this.catalogo;

        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        actualizarButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        totalCompra = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(102, 204, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        actualizarButton.setText("Actualizar");
        actualizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarButtonActionPerformed(evt);
            }
        });
        jPanel4.add(actualizarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 730, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTable1InputMethodTextChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1400, 580));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel1.setText("Total de compra:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 660, -1, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("$");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 660, -1, -1));

        totalCompra.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        totalCompra.setText("0.0");
        jPanel4.add(totalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 660, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("AGREGAR AL CARRITO");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 740, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("FINALIZAR COMPRA");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 740, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 3));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/dinero (1).png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 630, 130, 100));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 3));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2MouseExited(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/carro-de-la-compra (3).png"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 630, 130, 100));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 700, 130, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1430, 820));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actualizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarButtonActionPerformed
        this.actualizaCatalogo();
    }//GEN-LAST:event_actualizarButtonActionPerformed

    private void jTable1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable1InputMethodTextChanged
        totalCompra.setText("ñsldkfjañsdlk" + suma);
    }//GEN-LAST:event_jTable1InputMethodTextChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            switch (jTable1.getSelectedRow()) {
                case 0:
                    Fondo f1 = new Fondo();
                    f1.setSize(400, 300);
                    f1.setVisible(true);
                    Panel p1 = new Panel("/Imagenes/samsung.jpg");
                    f1.add(p1);
                    System.out.print("Samdufh");
                    break;
                case 1:
                    Fondo f2 = new Fondo();
                    f2.setSize(400, 300);
                    f2.setVisible(true);
                    Panel p2 = new Panel("/Imagenes/sony.jpg");
                    f2.add(p2);
                    break;
                case 2:
                    Fondo f3 = new Fondo();
                    f3.setSize(400, 300);
                    f3.setVisible(true);
                    Panel p3 = new Panel("/Imagenes/panasonic.jpg");
                    f3.add(p3);
                    break;
                case 3:
                    Fondo f4 = new Fondo();
                    f4.setSize(400, 300);
                    f4.setVisible(true);
                    Panel p4 = new Panel("/Imagenes/tashi.jpg");
                    f4.add(p4);
                    break;
                case 4:
                    Fondo f5 = new Fondo();
                    f5.setSize(400, 300);
                    f5.setVisible(true);
                    Panel p5 = new Panel("/Imagenes/sharp.jpg");
                    f5.add(p5);
                    break;
                case 5:
                    Fondo f6 = new Fondo();
                    f6.setSize(400, 300);
                    f6.setVisible(true);
                    Panel p6 = new Panel("/Imagenes/lg.jpg");
                    f6.add(p6);
                    break;

            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        jPanel1.setBackground(new Color(255, 153, 102));
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
        jPanel1.setBackground(Color.white);
    }//GEN-LAST:event_jPanel1MouseExited

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        int res = JOptionPane.showConfirmDialog(null, "¿DESEA TERMINAR SU COMPRA?");
        Cliente cl = new Cliente();
        if (res == 0) {
            JOptionPane.showMessageDialog(null, "GRACIAS POR SU COMPRA, HASTA PRONTO!!!");
            try {
                totalCompraTicket();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "CONTINUE CON SU COMPRA");
        }
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        this.comprarCosas();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        jPanel2.setBackground(new Color(255, 153, 102));
    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
        jPanel2.setBackground(Color.white);
    }//GEN-LAST:event_jPanel2MouseExited

    public void totalCompraTicket() throws IOException {
        //GenerarPDF g = new GenerarPDF();
        String ticket = "*************************************************"
                + "\nEL TOTAL DE LA COMPRA FUE DE: $" + this.totalCompra.getText()
                + "\nLOS ARTICULOS COMPRADOD FUERON:"
                + "\nMARCA         PRECIO     UNIDADES      SUBTOTAL"
                + "\nSAMSUNG    -----  5500 ---- " + this.unisamsung + "  -----  $" + (5500 * unisamsung)
                + "\nSONY     -----  9500 ---- " + this.unisony + "  -----  $" + (9500 * unisony)
                + "\nPANASONIC  --- 10500 ---- " + this.unipanasonic + "  -----  $" + (10500 * unipanasonic)
                + "\nTASHI     ----- 10700 ---- " + this.unitashi + "  -----  $" + (10700 * unitashi)
                + "\nSHARP     ----- 13000 ---- " + this.unisharp + "  -----  $" + (13000 * unisharp)
                + "\nLG       ----- 12000 ---- " + this.unilg + "  -----  $" + (12000 * unilg)
                + "\n*****************************************************\n"
                + "EL TOTAL DE LA COMPRA ES: $" + this.totalCompra.getText()
                + "\n*****************************************************\n";

        JOptionPane.showMessageDialog(null, "***************************************************"
                + "\nEL TOTAL DE LA COMPRA FUE DE:" + this.totalCompra.getText()
                + "\nLOS ARTICULOS COMPRADOD FUERON:\n"
                + "\nMARCA         PRECIO     UNIDADES      SUBTOTAL\n"
                + "\nSAMSUNG        5500              " + this.unisamsung + "                      " + (5500 * unisamsung)
                + "\nSONY               9500                " + this.unisony + "                      " + (9500 * unisony)
                + "\nPANASONIC   10500              " + this.unipanasonic + "                     " + (10500 * unipanasonic)
                + "\nTASHI               10700             " + this.unitashi + "                    " + (10700 * unitashi)
                + "\nSHARP             13000              " + this.unisharp + "                      " + (13000 * unisharp)
                + "\nLG                     12000              " + this.unilg + "                      " + (12000 * unilg)
                + "\n*******************\n"
                + "EL TOTAL DE LA COMPRA ES: " + this.totalCompra.getText());
        //g.generarPDF("TICKET DE COMPRA", ticket, "¡GRACIAS POR SU COMPRA!", "F:\\1200px-EAN13.svg.png", "F:\\ticket.pdf");
        //ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", "F:\\ticket.pdf");
        //processBuilder.start();
    }

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
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel totalCompra;
    // End of variables declaration//GEN-END:variables
}
