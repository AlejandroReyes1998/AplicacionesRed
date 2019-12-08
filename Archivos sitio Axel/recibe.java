import java.io.*;
import java.net.*;
public class recibe{
	public static void main(String args[]){
		try{
			ServerSocket s=new ServerSocket(1234);
			System.out.println("Servidor iniciado, esperando archivos....");
			for(;;){
				Socket cl=s.accept();
				DataInputStream dis = new DataInputStream(cl.getInputStream());
				String arch=dis.readUTF();
				long tam=dis.readLong();
				long r=0;
				int n=0;
				long p=0;
				DataOutputStream dos=new DataOutputStream(new FileOutputStream(arch));
				System.out.println("Preparando para recibir: "+arch+"\n\n");
				byte[] b=new byte[1500];
				while(r < tam){
					n=dis.read(b);
					dos.write(b);
					dos.flush();
					r=r+n;
					p=(r*100)/tam;
					System.out.println("\rRecibido el "+p+"% del archivo");
				}
				System.out.println("Archivo recibido correctamente!");
				dos.close();
				dis.close();
				cl.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
