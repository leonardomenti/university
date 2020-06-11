import java.net.*;
import java.io.*;

public class Service extends Thread{

	private int id;
	private Socket client;

	public Service(int i, Socket s){
		id = i;
		client = s;
	}

	public void run(){
		try{
			byte[] buffer = new  byte[100];
			InputStream is = client.getInputStream();
			int letti = is.read(buffer);
			String frase = new String(buffer,0,letti);
			while(!frase.equals("stop")){
				System.out.println("(Thread"+id+") Ricevuto: "+frase+" da: "+client.getRemoteSocketAddress());
				is = client.getInputStream();
				buffer = new byte[100];
				letti = is.read(buffer);
				frase = new String(buffer,0,letti);
			}
			System.out.println("(Thread"+id+")" +client.getRemoteSocketAddress()+" disconnesso");
			client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}