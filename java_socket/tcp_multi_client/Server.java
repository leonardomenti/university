import java.net.*;
import java.io.*;
import java.util.*;

public class Server{
	public static void main(String[] args){
		try{
			ServerSocket ss = new ServerSocket();
			ss.bind(new InetSocketAddress(InetAddress.getByName("localhost"),50000));
			System.out.println("-----SERVER-----");
			System.out.println("IP: "+ss.getInetAddress().getHostAddress()+"		PORT: "+ss.getLocalPort());

			int max_clients = 3;
			ArrayList <Socket> clientsQueue = new ArrayList <Socket> (max_clients);
			int index=0;

			byte[] buffer = new byte[100];

			while(true){
				try{
					//Server Accept Phase
					ss.setSoTimeout(10000);
					for(;index<max_clients;index++){
						Socket new_client = ss.accept(); // generazione socket lato server x gestire client
						clientsQueue.add(new_client);
						System.out.println("Accepeted Client -> IP: "+new_client.getInetAddress()+"		PORT: "+new_client.getPort());

					}
				}catch(SocketTimeoutException ste){
					System.out.println("\nTimeout expired ! ! !\n");

				}catch(Exception e){
					e.printStackTrace();
				}

				// Gestione RR dei clients
				int i=0;
				while(index>0){
					System.out.println("Now serving: "+clientsQueue.get(i).getRemoteSocketAddress());
					buffer = new byte[100];
					try{
						Socket serving = clientsQueue.get(i);
						serving.setSoTimeout(5000); // 5s per ogni client
						InputStream is = serving.getInputStream();
						int letti = is.read(buffer);
						String frase = new String(buffer,0,letti);
						while(!frase.equals("stop")){
							System.out.println("Ricevuto: "+frase+" da: "+serving.getRemoteSocketAddress());
							is = serving.getInputStream();
							buffer = new byte[100];
							letti = is.read(buffer);
							frase = new String(buffer,0,letti);
						}
						System.out.println("Disconnesione client       Ricevuto: "+frase);
						serving.close();
						clientsQueue.remove(i);
						index--;
					}catch(SocketTimeoutException ste){
						System.out.println("\nTimeout expired ! ! !\n");
					}catch(Exception e){
						try{
							clientsQueue.get(i).close();
							clientsQueue.remove(i);
							index--;
						}catch(Exception e2){
							e2.printStackTrace();
						}
					}
					
					i = index==0? 0 : (i+1)%index; 
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}