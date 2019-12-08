import java.net.*;
import java.io.*;
public class sHolaMundoD{
	public static void main(String args[]){
		try{
			DatagramSocket s=new DatagramSocket(9999);
			System.out.println("Servidor iniciado en el puerto: "+s.getLocalPort()+"\nEsperando conexión.");
			for(;;){
				DatagramPacket p=new DatagramPacket(new byte[65535],65535);
				s.receive(p);
				System.out.println("Datagrama recibido de : "+p.getAddress()+": "+p.getPort()+"\nCon los datos: ");
				String datos=new String(p.getData(),0,p.getLength());
				System.out.println(datos);
				String msj="Hola mundo!";
				byte[]b=msj.getBytes();
				DatagramPacket p1=new DatagramPacket(b,b.length,p.getAddress,p.getPort);
				s.send(p1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
