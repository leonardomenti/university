import java.net.*;
import java.io.*;

public class Server{
	public static void main(String[] agrs){

		try{
			ServerSocket ss = new ServerSocket(50000);
			System.out.println("-----SERVER-----");
			System.out.println("IP: "+ss.getInetAddress()+"		PORT: "+ss.getLocalPort());
			int i=1;
			while(true){
				Socket client = ss.accept();

				System.out.println("Accepted: "+client.getRemoteSocketAddress());
				Thread t = new Service(i,client);
				t.start(); 
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}