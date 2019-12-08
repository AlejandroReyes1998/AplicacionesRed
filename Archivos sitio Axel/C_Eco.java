import java.net.*;
import java.io.*;

public class C_Eco{
	public static void main(String []args){
		try{
			String host="127.0.0.1";
			int pto=1234;
			Socket cl=new Socket(host,pto);
			System.out.println("Conexion al servidor exitosa!, escribe un mensaje, <ENTER> para enviar, 'salir' para salir");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
			for(;;){
				String msj=br2.readLine();
				pw.println(msj);
				pw.flush();
				if(msj.compareToIgnoreCase("salir")==0){
					System.out.println("Termino apliacion");
					br2.close();
					br.close();
					pw.close();
					cl.close();
					System.exit(0);
				}else{
					String eco=br2.readLine();
					System.out.println("Eco recibido "+eco);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}