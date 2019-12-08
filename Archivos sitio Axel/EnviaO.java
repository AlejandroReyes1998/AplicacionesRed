import java.net.*;
import java.io.*;
public class EnviaO{
	public static void main(String args[]){
		try{
			Socket cl=new Socket("localhost",8000);
			dato d=new dato("Juan",20,1.0f,18);
			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
			System.out.println("Conexión establecida...\nSe enviará el objeto con la siguiente información\n v1: "+d.getv1()+" v2: "+d.getv2()+" v3: "+d.getv3()+" v4: "+d.getv4());
			oos.writeObject(d);
			oos.flush();
			System.out.println("Objeto enviado, esperando objeto....");
			dato d2=(dato)ois.readObject();
			System.out.println("Objeto recibido.\nSe recibió el objeto con la siguiente información\n v1: "+d2.getv1()+" v2: "+d2.getv2()+" v3: "+d2.getv3()+" v4: "+d2.getv4());
			ois.close();
			oos.close();
			cl.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}