/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import sharedClasses.Producto;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static double getTotal(ArrayList<Producto> ticket, ArrayList<Producto> productos) {

        double total = 0;
        for (int i = 0; i < ticket.size(); i++) {

            for (int j = 0; j < productos.size(); j++) {
                if (productos.get(j).getCodigo().equals(ticket.get(i).getCodigo())) {
                    if (productos.get(j).getExistencias() >= ticket.get(i).getCompra()) {
                        total = total + (ticket.get(i).getCompra() * ticket.get(i).getPrecio());
                    } else {
                        return -1;
                    }
                }
            }
        }
        for (int i = 0; i < ticket.size(); i++) {
            for (int j = 0; j < productos.size(); j++) {
                if (productos.get(j).getCodigo().equals(ticket.get(i).getCodigo())) {
                    if (productos.get(j).getExistencias() >= ticket.get(i).getCompra()) {
                        productos.get(j).setExistencias(productos.get(i).getExistencias() - ticket.get(i).getCompra());
                    } else {
                        return -1;
                    }
                }
            }
        }

        return total;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Producto> productos = new ArrayList();
        ArrayList<Producto> ticket = new ArrayList();
        ObjectInputStream ois2 = null;
        ObjectOutputStream oos2 = null;
        //productos.add(new Producto("Samsung", "35 pulgadas, LED", "1000", 5500, 15));
        //productos.add(new Producto("Sony", "40 pulgadas, ultra slim", "1100", 9500, 9));
        //productos.add(new Producto("Panasonic", "45 pulgadas", "100", 10500, 13));
        //productos.add(new Producto("Tashi", "50 pulgadas", "4100", 10700, 20));
        //productos.add(new Producto("Sharp", "70 pulgadas", "1110", 13000, 15));
        //productos.add(new Producto("LG", "Ultra led, 50 pulgadas", "1111", 12000, 9));
        try {
            ServerSocket server = new ServerSocket(9090);
            for (;;) {
                Socket serializado = server.accept();
                System.out.println("Objetos recibidos desde: " + serializado.getInetAddress() + ": " + serializado.getPort());
                oos2 = new ObjectOutputStream(serializado.getOutputStream());
                ois2 = new ObjectInputStream(serializado.getInputStream());
                productos = (ArrayList<Producto>) ois2.readObject();
                String respuesta = "Proceso terminado";
                oos2.writeObject(respuesta);
                oos2.flush();
                serializado.close();
                break;
            }
            for (;;) {
                Socket cliente = server.accept();
                System.out.println("Cliente conectado desde: " + cliente.getInetAddress() + ": " + cliente.getPort());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                int opc = Integer.parseInt((String) ois.readObject());
                System.out.println("Objeto Recibido");
                switch (opc) {
                    case 1:
                        ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                        oos.writeObject(Integer.toString(productos.size()));
                        for (int i = 0; i < productos.size(); i++) {
                            oos.writeObject(productos.get(i));
                            System.out.println("Enviado " + productos.get(i).getNombre());
                        }
                        DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                        dos.writeUTF("/Imagenes/samsung.jpg");
                        dos.flush();
                        dos.writeUTF("/Imagenes/sony.jpg");
                        dos.flush();
                        dos.writeUTF("/Imagenes/panasonic.jpg");
                        dos.flush();
                        dos.writeUTF("/Imagenes/tashi.jpg");
                        dos.flush();
                        dos.writeUTF("/Imagenes/sharp.jpg");
                        dos.flush();
                        dos.writeUTF("/Imagenes/lg.jpg");
                        break;
                    case 2:
                        oos = new ObjectOutputStream(cliente.getOutputStream());

                        int tam = ois.readInt();
                        for (int j = 0; j < tam; j++) {
                            ticket.add((Producto) ois.readObject());
                            System.out.println("Recibi (server): " + ticket.get(j).getNombre() + "con: " + ticket.get(j).getCompra());
                        }

                        double total = Servidor.getTotal(ticket, productos);
                        oos.writeObject(Double.toString(total));
                        ticket.clear();
                        break;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
}
