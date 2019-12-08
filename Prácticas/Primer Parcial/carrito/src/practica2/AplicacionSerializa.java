/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import sharedClasses.Producto;

public class AplicacionSerializa {
    public static void main(String[] args){
     String host= "127.0.0.1";
     int pto = 9090;
     ObjectOutputStream oos = null;
     ObjectInputStream ois = null;
     String respuesta;
     ArrayList<Producto> productos = new ArrayList<Producto>();
    try{
         Socket cl = new Socket(host,pto);
         System.out.println("Conexión establecida...");
         oos= new ObjectOutputStream(cl.getOutputStream());
         ois = new ObjectInputStream(cl.getInputStream());
         Producto u = new Producto("Samsung", "35 pulgadas, LED", "1000", 5500, 15);
         productos.add(u);
         u = new Producto("Sony", "40 pulgadas, ultra slim", "1100", 9500, 9);
         productos.add(u);
         u = new Producto("Panasonic", "45 pulgadas", "100", 10500, 13);
         productos.add(u);
         u = new Producto("Tashi", "50 pulgadas", "4100", 10700, 20);
         productos.add(u);
         u = new Producto("Sharp", "70 pulgadas", "1110", 13000, 15);
         productos.add(u);
         u = new Producto("LG", "Ultra led, 50 pulgadas", "1111", 12000, 9);
         productos.add(u);
         System.out.println("Enviando objeto");
         oos.writeObject(productos);
         oos.flush();
         System.out.println("Objeto enviado");   
         respuesta = (String)ois.readObject();
         System.out.println("Respuesta del servidor: " + respuesta);
           
     }catch(Exception e){
         System.err.println(e);
     }//catch
    }//main
}//class

