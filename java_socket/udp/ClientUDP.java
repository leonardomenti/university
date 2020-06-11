// LE SOCKET TCP SI CONNETTONO metodo connect(InetAddress, port)
// LE SOCKET UDP NON SI CONNETTONO

import java.net.*;
import java.io.*;

public class ClientUDP{
	public static void main(String[] args){

		DatagramSocket sClient;
		try{
			sClient = new DatagramSocket(50001);
			System.out.println("Indirzzo Client: " + sClient.getLocalAddress()
								+ "; Porta Client" + sClient.getLocalPort());
			if(args.length!=2)
				throw new IllegalArgumentException("Numero sbagliato di parametri");
			else{

				String srv = args[0];
				int port = Integer.parseInt(args[1]);
				InetAddress ia = InetAddress.getByName(srv);
				System.out.println("Indirizzo Server: " + ia + "; Porta Server: "+port);
				InetSocketAddress isa = new InetSocketAddress(ia,port);

				System.out.println("Digitare stop per terminare la comunicazione oppure una frase da comunicare");
				InputStreamReader tastiera = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(tastiera);
				String frase = br.readLine();
				while(!frase.equals("stop")){
					// Invio al server
					byte[] buffer = frase.getBytes();
					DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
					dp.setSocketAddress(isa);
					sClient.send(dp);

					//Ricezione risposta dal server
					int dim_buff = 100;
					byte[] buffer2 = new byte[dim_buff];
					DatagramPacket dpin = new DatagramPacket(buffer2, dim_buff);
					sClient.receive(dpin);

					String rec = new String(buffer2, 0, dpin.getLength());
					InetAddress ia2 = dpin.getAddress();
					int port2 = dpin.getPort();
					System.out.println("Ricevuto: "+rec+" dal server: "+ia2+":"+port2);

					System.out.println("Digitare 'stop' per terminare la comunicazione oppure una frase da comunicare");
					frase = br.readLine();
				}
				
				//Thread.sleep(120*1000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}