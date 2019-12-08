import java.net.*;
import java.io.*;

public class S_Eco{
	public static void main(String []args){
		try{
			int pto=1234;
			ServerSocket s=new ServerSocket(pto);
			System.out.println("Servicio iniciado, esperando clientes: ");
			for(;;){
				Socket cl=s.accept();
				System.out.println("Cliente conectado desde: "+cl.getInetAddress()+":"+cl.getPort());
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
				System.out.println("esperando mensajes....");
				for(;;){
					String msj=br.readLine();
					System.out.println("Mensaje recibido:..."+msj);
					if(msj.compareToIgnoreCase("salir")==0){
						break;
					}else{
						String eco=msj+" eco";
						pw.println(eco);
						pw.flush();
					}
				}
				br.close();
				pw.close();
				cl.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}