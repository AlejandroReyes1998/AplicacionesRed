import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;

public class envia{
	public static void main(String[]args){
		try{
			JFileChooser jf=new JFileChooser();
			int r=jf.showOpenDialog(null);
			if(r==JFileChooser.APPROVE_OPTION){//jf.requestFocusInWindow
				File f=jf.getSelectedFile();
				String nombre=f.getName();
				long tam=f.length();
				String ruta=f.getAbsolutePath();
				String host="127.0.0.1";
				int pto=1234;
				Socket cl=new Socket(host,pto);
				System.out.println("conexion con servidor establecida. Se enviará el archivo: "+ruta+"\n\n");
				DataOutputStream dos=new DataOutputStream(cl.getOutputStream());
				DataInputStream dis=new DataInputStream(new FileInputStream(ruta));
				dos.writeUTF(nombre);
				dos.flush();
				dos.writeLong(tam);
				dos.flush();
				long enviados=0,porcent=0;
				int n=0;
				while(enviados<tam){
					byte[] b=new byte[1500];
					n=dis.read(b);
					dos.write(b);
					dos.flush();
					enviados=enviados+n;
					porcent=(enviados*100)/tam;
					System.out.println("\rTransferido el "+porcent+"% del archivo");
				}
				System.out.println("Archivo enviado");
				dis.close();
				dos.close();
				cl.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}