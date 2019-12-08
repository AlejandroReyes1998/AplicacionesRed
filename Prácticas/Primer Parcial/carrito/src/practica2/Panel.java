/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author envamapa
 */
public class Panel extends JPanel {
    
    ImageIcon imagen;
    String nombre;
    
        public Panel(String nombre) {
            this.nombre = nombre;
        }

        //Se crea un método cuyo parámetro debe ser un objeto Graphics

        public void paint(Graphics grafico) {
            Dimension height = getSize();

            imagen = new ImageIcon(getClass().getResource(nombre)); 

            //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel

            grafico.drawImage(imagen.getImage(), 0, 0, height.width, height.height, null);

            setOpaque(false);
            super.paintComponent(grafico);
        }
    }