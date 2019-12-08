
import java.io.*;
import java.net.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

public class Servidor2 {

    public static void main(String args[]) {
        //Creamos el socket servidor
        try {
            ServerSocket s = new ServerSocket(1235);
            System.out.println("Servidor iniciado, esperando archivos o directorios....");
            for (;;) {
                Socket cl = s.accept();
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                String arch = dis.readUTF();
                long tam = dis.readLong();
                long r = 0;
                int n = 0;
                long p = 0;
                // DataOutputStream dos = new DataOutputStream(new FileOutputStream("ruta carpeta destino/"+arch));
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("/home/alejandro/Descargas/" + arch));
                System.out.println("Preparando para recibir: " + arch + "\n\n");
                while (r < tam) {
                    byte[] b;
                    if (tam - r >= 512) {
                        b = new byte[512];
                    } else {
                        Integer x = (int) (long) (tam - r);
                        b = new byte[x];
                    }
                    n = dis.read(b);
                    dos.write(b);
                    dos.flush();
                    r = r + n;
                    p = (r * 100) / tam;
                    System.out.println("\rRecibido el " + p + "%");
                }
                dos.close();
                dis.close();
                cl.close();
                File tmpDir = new File("/home/alejandro/comprimir/compress.zip");
                File tmpDir2 = new File("/home/alejandro/Descargas/compress.zip");
                /*
                Borramos los zip generados
                File tmpDir = new File("ruta carpeta temporal/compress.zip");
                File tmpDir2 = new File("ruta carpeta destino/compress.zip");
                 */
                //Checamos si enviamos un directorio o un archivo
                boolean exists = tmpDir.exists();
                if (exists) {
                    //Borramos los zip generados y descomprime
                    unZip();
                    tmpDir.delete();
                    tmpDir2.delete();
                    JOptionPane.showMessageDialog(null, "Directorio descargado");
                } else {
                    JOptionPane.showMessageDialog(null, "Archivo descargado");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método que descomprime
    public static void unZip() throws IOException {
        //try (ZipFile file = new ZipFile("ruta carpeta temporal/compress.zip"))
        try (ZipFile file = new ZipFile("/home/alejandro/comprimir/compress.zip")) {
            FileSystem fileSystem = FileSystems.getDefault();
            Enumeration<? extends ZipEntry> entries = file.entries();
            // String uncompressedDirectory = "ruta carpeta destino/";
            String uncompressedDirectory = "/home/alejandro/Descargas/";
            //Hasta que el zip termine
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                //Subcarpetas
                if (entry.isDirectory()) {
                    System.out.println("Creando subcarpeta:" + uncompressedDirectory + entry.getName());
                    Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
                } //Descomprimimos
                else {
                    InputStream is = file.getInputStream(entry);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String uncompressedFileName = uncompressedDirectory + entry.getName();
                    Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                    Files.createFile(uncompressedFilePath);
                    FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
                    while (bis.available() > 0) {
                        fileOutput.write(bis.read());
                    }
                    fileOutput.close();
                    System.out.println("Archivo listo :" + entry.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
