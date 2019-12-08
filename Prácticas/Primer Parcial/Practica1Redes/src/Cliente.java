/*
Para el desarrollo de esta práctica se requiere de dos carpetas, una temporal y otra donde se 
recibirán los archivos seleccionados, se especifica en ambos archivos donde se reemplazarán para su
aplicación en otros equipos
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Cliente extends JPanel
        implements ActionListener {

    JButton go, go2;
    JLabel l;
    JFileChooser chooser, chooser2;
    String choosertitle;

    public Cliente() {
        setBounds(300, 300, 780, 350);
        l = new JLabel("Seleccione el tipo de elemento a enviar");
        add(l);
        go = new JButton("Folder");
        go.addActionListener(this);
        add(go);
        go2 = new JButton("Archivo");
        go2.addActionListener(this);
        add(go2);
        go2 = new JButton("Ver carpeta");
        go2.addActionListener(this);
        add(go2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = ((JButton) e.getSource()).getActionCommand();
        System.out.println("Tipo de archivo a enviar: " + actionCommand);
        //Enviaremos un folder
        if (actionCommand.equals("Folder")) {
            chooser = new JFileChooser();
            //Donde se ubicará el filechooser
            chooser.setCurrentDirectory(new java.io.File("/home/alejandro/"));
            chooser.setDialogTitle(choosertitle);
            //Restringimos a directorios
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = chooser.getSelectedFile();
                    String sourceFile = selectedFile.getName();
                    //Comprimiremos el directorio y posteriormente el zipeado lo enviamos con el socket
                    //try (FileOutputStream fos = new FileOutputStream("rutacarpetatemporal/compress.zip"); ZipOutputStream zipOut = new ZipOutputStream(fos)) 
                    try (FileOutputStream fos = new FileOutputStream("/home/alejandro/comprimir/compress.zip"); ZipOutputStream zipOut = new ZipOutputStream(fos)) {
                        try {
                            //Zipeado
                            zipFile(selectedFile, sourceFile, zipOut);
                            System.out.println("Directorio comprimido!");
                        } catch (IOException ex) {
                            System.out.println("Ha ocurrido un error al comprimir:" + ex);
                        }
                    }
                    //Envio del zip a través del socket
                    try {
                        File f = new File("/home/alejandro/comprimir/compress.zip");
                        String nombre = f.getName();
                        long tam = f.length();
                        String ruta = f.getAbsolutePath();
                        String host = "127.0.0.1";
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

                        while (enviados < tam) {
                            byte[] b;
                            if (tam - enviados >= 512) {
                                b = new byte[512];
                            } else {
                                Integer x = (int) (long) (tam - enviados);
                                b = new byte[x];
                            }
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
                } catch (IOException ex) {
                    System.out.println("Ha ocurrido un error al encontrar el directorio:" + ex);
                }

            } else {
                System.out.println("No se ha seleccionado nada");
            }
        } else if (actionCommand.equals("Archivo")) {
            //Enviaremos un archivo
            chooser2 = new JFileChooser();
            //Donde se ubicará el filechooser
            chooser2.setCurrentDirectory(new java.io.File("/home/alejandro/"));
            chooser2.setDialogTitle(choosertitle);
            if (chooser2.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    //Creamos socket con el archivo seleccionado
                    File f = chooser2.getSelectedFile();
                    String nombre = f.getName();
                    long tam = f.length();
                    String ruta = f.getAbsolutePath();
                    String host = "127.0.0.1";
                    int pto = 1234;
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
                    while (enviados < tam) {
                        byte[] b;
                        if (tam - enviados >= 512) {
                            b = new byte[512];
                        } else {
                            Integer x = (int) (long) (tam - enviados);
                            b = new byte[x];
                        }
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
            } else {
                System.out.println("No se ha seleccionado nada");
            }
        } else {
            lista listax = new lista();
            listax.setVisible(true);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 140);
    }

    //Main y dimensiones de ventana
    public static void main(String s[]) {
        JFrame frame = new JFrame("Practica 1");
        Cliente panel = new Cliente();
        frame.addWindowListener(
                new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
        );
        frame.getContentPane().add(panel, "Center");
        frame.setSize(panel.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
