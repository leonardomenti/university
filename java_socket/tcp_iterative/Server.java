// Server connection-oriented iterativo, serve un client alla volta.
// in questo caso ne serve al max. 2 e poi finisce

import java.net.*;
import java.io.*;

public class Server{
	public static void main(String[] args){

		try{
			ServerSocket ss = new ServerSocket();
			Socket client = new Socket();

			ss.bind(new InetSocketAddress(InetAddress.getByName("192.168.1.139"),50000));
			
			for(int i=0;i<2;i++){
				client = ss.accept(); // client è la socket lato server, cioè il Source è il server. 
				// Viene creata dalla accept() della ServerSocket
				System.out.println("-----SERVER-----");
				System.out.println("IP: "+client.getLocalAddress().getHostAddress()+"	PORT: "+client.getLocalPort());

				System.out.println("-----CLIENT-----");
				System.out.println("IP: "+client.getInetAddress().getHostAddress()+"	PORT: "+client.getPort());
				
				int dim_buffer = 100;
				byte[] buffer = new byte[dim_buffer];

				InputStream fromCl = client.getInputStream(); // bloccante
				int letti = fromCl.read(buffer);
				String stampa = new String(buffer,0,letti);
				System.out.println("Stringa ricevuta: "+stampa);

				while(!stampa.equals("stop")){
					fromCl = client.getInputStream(); // bloccante
					letti = fromCl.read(buffer);
					stampa = new String(buffer,0,letti);
					System.out.println("Stringa ricevuta: "+stampa);

				}
				System.out.println("Client disconnesso");
				System.out.println();
				System.out.println();

			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}