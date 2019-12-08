package chatp3;

import java.io.*;
 import java.net.*;
 import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class MulticastClient extends Thread{
 	public static final String MCAST_ADDR  = "230.0.0.1"; //dir clase D valida, grupo al que nos vamos a unir
 	public static final int MCAST_PORT = 9013;//puerto multicast
 	public static final int DGRAM_BUF_LEN=512; //tamaño del buffer
        InetAddress group =null;
        Chat elChat;
        
        
        public MulticastClient(Chat chat)
        {
            elChat=chat;
            System.out.println("Cliente iniciado.");
            try{
    		group = InetAddress.getByName(MCAST_ADDR);//intenta resolver la direccion
            }catch(UnknownHostException e){
    		e.printStackTrace();
    		System.exit(1);
	}
        }
        
        public Boolean envia(String msg)
        {
        try{
    		MulticastSocket socketEnvio = new MulticastSocket(MCAST_PORT);
    		socketEnvio.joinGroup(group); // se configura para escuchar el paquete
    		DatagramPacket packet = new DatagramPacket(msg.getBytes(),msg.length(),group,MCAST_PORT);
    		socketEnvio.send(packet);
    		socketEnvio.close();
                return true;
    	}catch(IOException e){
    		e.printStackTrace();
                return false;
    	}
        }

	public void run(){
        try{
    		group = InetAddress.getByName(MCAST_ADDR);//intenta resolver la direccion
            }catch(UnknownHostException e){
    		e.printStackTrace();
    		System.exit(1);
	}
    	Vector d = new Vector();
	boolean salta=true;	
    	try{
    		MulticastSocket socket = new MulticastSocket(MCAST_PORT); //socket tipo multicast
    		socket.joinGroup(group);//se une al grupo
                String mensaje="";
    		while(salta){
    			byte[] buf = new byte[DGRAM_BUF_LEN];//crea arreglo de bytes 
    			DatagramPacket recv = new DatagramPacket(buf,buf.length);//crea el datagram packet a recibir
    			socket.receive(recv);// ya se tiene el datagram packet
    			byte [] data = recv.getData(); //aqui no se entienden los datos
                        mensaje=new String(data);
                        elChat.Agregar_conversacion(mensaje);
                        Thread.sleep(100);
                        elChat.abajo();

		} 
    	}catch(IOException e){
    		e.printStackTrace();
    		System.exit(2);
    	}    catch (InterruptedException ex) {
                 Logger.getLogger(MulticastClient.class.getName()).log(Level.SEVERE, null, ex);
             }

	}//run
}//class


