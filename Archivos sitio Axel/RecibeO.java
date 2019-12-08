import java.net.*;
import java.io.*;
public class RecibeO{
	public static void main(String args[]){
		try{
			ServerSocket ss=new ServerSocket(8000);
			System.out.println("Servidor iniciado, esperando objetos....");
			for(;;){
				Socket cl=ss.accept();
				ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
				dato d = (dato)ois.readObject();
				System.out.println("Recibido el objeto con la siguiente información\n v1: "+d.getv1()+" v2: "+d.getv2()+" v3: "+d.getv3()+" v4: "+d.getv4());
				dato d2=new dato("ESCOM",25,25.2f,26);
				oos.writeObject(d2);
				oos.flush();
				oos.close();
				ois.close();
				cl.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}