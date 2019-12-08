
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class lista extends JFrame implements ActionListener {

    static ArrayList<String> fileList = new ArrayList<>();
    private Container contenedor;/*declaramos el contenedor*/
    private JButton eliminar, descarga;/*declaramos el objeto Boton*/
    private JLabel mensaje;/*declaramos el objeto Label*/
    private JTextField campo;
    private JList listaNombres;/*declaramos La Lista*/
    private DefaultListModel modelo;/*declaramos el Modelo*/
    private JScrollPane scrollLista;

    public lista() {
        /*permite iniciar las propiedades de los componentes*/
        iniciarComponentes();
        /*Asigna un titulo a la barra de titulo*/
 /*tamaño de la ventana*/
        setSize(320, 320);
        /*pone la ventana en el Centro de la pantalla*/
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void iniciarComponentes() {
        contenedor = getContentPane();/*instanciamos el contenedor*/
 /*con esto definmos nosotros mismos los tamaños y posicion
		 * de los componentes*/
        contenedor.setLayout(null);

        campo = new JTextField();
        campo.setBounds(20, 80, 135, 23);

        /*Propiedades del boton, lo instanciamos, posicionamos y
		 * activamos los eventos*/
        eliminar = new JButton();
        eliminar.setText("Eliminar");
        eliminar.setBounds(20, 210, 120, 23);
        eliminar.addActionListener(this);

        descarga = new JButton();
        descarga.setText("Descarga");
        descarga.setBounds(140, 210, 120, 23);
        descarga.addActionListener(this);

        mensaje = new JLabel();
        mensaje.setBounds(20, 250, 280, 23);

        //instanciamos la lista
        listaNombres = new JList();
        listaNombres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //instanciamos el modelo
        modelo = new DefaultListModel();
        File f = new File("/home/alejandro/recibidos/");
        listDirectory(f);
        for (int i = 0; i < fileList.size(); i++) {
            modelo.add(i, fileList.get(i));
        }
        listaNombres.setModel(modelo);
        //instanciamos el Scroll que tendra la lista
        scrollLista = new JScrollPane();
        scrollLista.setBounds(0, 0, 300, 200);
        scrollLista.setViewportView(listaNombres);

        /*Agregamos los componentes al Contenedor*/
        contenedor.add(eliminar);
        contenedor.add(descarga);
        contenedor.add(mensaje);
        contenedor.add(scrollLista);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == eliminar) {
            eliminarNombre(listaNombres.getSelectedIndex());
        }
        if (evento.getSource() == descarga) {
            try {
                descargax(listaNombres.getSelectedIndex());
            } catch (IOException ex) {
                Logger.getLogger(lista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void eliminarNombre(int indice) {
        if (indice != -1) {
            String selectedText = (String) modelo.getElementAt(indice);
            modelo.removeElementAt(indice);
            File e = new File(selectedText);
            e.delete();
            JOptionPane.showMessageDialog(null, "Archivo eliminado");
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un indice",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void descargax(int indice) throws IOException {
        String selectedText = (String) modelo.getElementAt(indice);
        File f = new File(selectedText);
        if (f.isDirectory()) {
            try (FileOutputStream fos = new FileOutputStream("/home/alejandro/comprimir/compress.zip"); ZipOutputStream zipOut = new ZipOutputStream(fos)) {
                try {
                    //Zipeado
                    zipFile(f, f.getName(), zipOut);
                    System.out.println("Directorio comprimido!");
                } catch (IOException ex) {
                    System.out.println("Ha ocurrido un error al comprimir:" + ex);
                }
                //Envio del zip a través del socket
                try {
                    File fd = new File("/home/alejandro/comprimir/compress.zip");
                    String nombre = fd.getName();
                    long tam = fd.length();
                    String ruta = fd.getAbsolutePath();
                    String host = "127.0.0.2";
                    int pto = 1234;
                    Socket cl = new Socket(host, pto);
                    System.out.println("Conexión con servidor establecida. Se enviará el directorio\n\n");
                    DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                    DataInputStream dis = new DataInputStream(new FileInputStream(ruta));
                    dos.writeUTF(nombre);
                    dos.flush();
                    dos.writeLong(tam);
                    dos.flush();
                    long enviados = 0, porcent = 0;
                    int n = 0;
                    byte[] b = new byte[512];
                    while (enviados < tam) {                        
                        n = dis.read(b);
                        dos.write(b);
                        dos.flush();
                        enviados = enviados + n;
                        porcent = (enviados * 100) / tam;
                        System.out.println("\rTransferido el " + porcent + "% del directorio");
                    }
                    System.out.println("Directorio enviado");
                    dis.close();
                    dos.close();
                    cl.close();
                } catch (IOException ex) {
                    System.out.println("Error al crear el socket cliente " + ex);
                }
            }
        } else {
            try {
                //Creamos socket con el archivo seleccionado
                String nombre = f.getName();
                long tam = f.length();
                String ruta = f.getAbsolutePath();
                String host = "127.0.0.2";
                int pto = 1235;
                Socket cl = new Socket(host, pto);
                System.out.println("Conexión con servidor establecida. Se enviará el archivo: " + ruta + "\n\n");
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(ruta));
                dos.writeUTF(nombre);
                dos.flush();
                dos.writeLong(tam);
                dos.flush();
                long enviados = 0, porcent = 0;
                int n = 0;
                byte[] b = new byte[512];
                while (enviados < tam) {                  
                    n = dis.read(b);
                    dos.write(b);
                    dos.flush();
                    enviados = enviados + n;
                    porcent = (enviados * 100) / tam;
                    System.out.println("\rTransferido el " + porcent + "% del archivo");
                }
                System.out.println("Archivo enviado");
                dis.close();
                dos.close();
                cl.close();
            } catch (IOException ex) {
                System.out.println("Error al crear el socket cliente " + ex);
            }
        }

    }

    public static void listDirectory(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File currFile : files) {
                listDirectory(currFile);
            }
        } else {
            fileList.add(file.getPath());
        }
    }
    //Método que zipea archivos y directorios

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        //Directorio detectado
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[2048];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }
}
