import java.net.*;
import java.io.*;

public class Client{
	public static void main(String[] args){

		try{
			Socket client = new Socket();
			InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName("localhost"),50000);
			client.connect(isa);

			System.out.println("-----CLIENT-----");
			System.out.println("IP: "+client.getLocalAddress().getHostAddress()+"	PORT: "+client.getLocalPort());

			System.out.println("-----SERVER-----");
			System.out.println("IP: "+client.getInetAddress().getHostAddress()+"	PORT: "+client.getPort());

			System.out.println("Digitare 'stop' per terminare la comunicazione");

			InputStreamReader tastiera = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(tastiera);

			String frase = br.readLine();
			OutputStream os = client.getOutputStream();

			while(!frase.equals("stop")){
				os.write(frase.getBytes());
				frase = br.readLine();
			}
			System.out.println("Mi disconnetto, prima invio: "+frase);
			os.write(frase.getBytes());
			client.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}