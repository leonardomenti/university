import java.net.*;
import java.io.*;

public class Client{
	public static void main(String[] args){

		try{
			Socket client = new Socket();
			//client.bind(new InetSocketAddress(InetAddress.getByName(args[0]),Integer.parseInt(args[1])));

			InetAddress iaServer = InetAddress.getByName("192.168.1.139");
			InetSocketAddress isa = new InetSocketAddress(iaServer,50000);

			client.connect(isa);

			System.out.println("-----SERVER-----");
			System.out.println("IP: "+client.getInetAddress().getHostAddress()+"	PORT: "+client.getPort());

			System.out.println("-----CLIENT-----");
			System.out.println("IP: "+client.getLocalAddress().getHostAddress()+"	PORT: "+client.getLocalPort());

			System.out.println("Digitare 'stop' per terminare la comunicazione");

			InputStreamReader tastiera = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(tastiera);
			String frase = br.readLine();

			OutputStream os = client.getOutputStream();
			while(!frase.equals("stop")){
				System.out.println("Invio al server: "+ frase);
				
				os.write(frase.getBytes(),0,frase.length());
				frase = br.readLine();
			}
			System.out.println("Fine comunicazione + Invio al server: "+ frase);
			os.write(frase.getBytes());
			client.close();
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}