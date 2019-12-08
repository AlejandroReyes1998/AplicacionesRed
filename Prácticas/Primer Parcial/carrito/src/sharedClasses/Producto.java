/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedClasses;
import java.io.File;
import java.io.Serializable;

public class Producto implements Serializable{
    private File imagen;
    private String nombre,ruta,descripcion,codigo;
    private float precio;
    private int existencias,compra = 0;

    public Producto() {
    }

    public int getCompra() {
        return compra;
    }

    public void setCompra(int compra) {
        this.compra = compra;
    }

    public String getCodigo() {
        return codigo;
    }

    public Producto(String nombre, String descripcion, String codigo, float precio, int existencias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.precio = precio;
        this.existencias = existencias;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }
    
    public String [] getDatos()
    {
        String [] datos  = {this.getNombre(),this.getDescripcion(),Float.toString(this.getPrecio()),Integer.toString(this.getExistencias()),"0"};
        return datos;
    }
    
    
    
}
