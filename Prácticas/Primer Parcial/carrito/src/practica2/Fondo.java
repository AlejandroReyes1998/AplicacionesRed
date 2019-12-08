/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import javax.swing.JFrame;
/**
 *
 * @author envamapa
 */
public class Fondo extends JFrame {
    
    public static void main(String[] args){
        Fondo f = new Fondo();
        f.setSize(400, 300);
        f.setVisible(true);
        Panel p = new Panel("/Imagenes/samsung.jpg");
        f.add(p);
    }
    
}
