// LE SOCKET TCP SI CONNETTONO metodo connect(InetAddress, port)
// LE SOCKET UDP NON SI CONNETTONO

import java.net.*;

public class ServerUDP{
	public static void main(String[] args){

		DatagramSocket sServer;
		try{
			sServer = new DatagramSocket(50000);
			System.out.println("Indirzzo Server: " + sServer.getLocalAddress()
								+ "; Porta Server" + sServer.getLocalPort());

			int dim_buff = 100;
			byte[] buffer = new byte[dim_buff];
			DatagramPacket dpin = new DatagramPacket(buffer, dim_buff);
			while(true){
				sServer.receive(dpin);

				String rec = new String(buffer, 0, dpin.getLength());
				System.out.println("Ricevuto: "+rec);

				InetAddress ia = dpin.getAddress();
				int port = dpin.getPort();
				System.out.println("Indirizzo: "+ia+"; Porta: "+port);

				byte[] buffer2 = "ACK".getBytes();
				DatagramPacket risp = new DatagramPacket(buffer2, buffer2.length);
				risp.setSocketAddress(new InetSocketAddress(ia,port));
				sServer.send(risp);
			}

			//sServer.close();

			//Thread.sleep(120*1000);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}