package blindsSenderBackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Sender{
	private Socket s;
	private PrintWriter output;
	private InputHandler in;
	private boolean connection = false;
	private TimedCommandContainer tcc;
	private int port;
	
	public Sender(int port, TimedCommandContainer tcc, IpLoader il) {
		this.port = port;
		this.tcc = tcc;
			
		il.connect(this);
		/*String ipAdress = il.getIpAddressFromFile();
		if (ipAdress == "") return;
		while (!connectToReceiver(ipAdress)) {
			ipAdress = IpLoader.retryIp(ipAdress, lil);
			if (ipAdress == null) return;
		}*/
	}
	
	public boolean connectToReceiver(String address) {
		s = new Socket();
		try {
			s.connect(new InetSocketAddress(address, port), 1000);
			output = new PrintWriter(s.getOutputStream(), true);
			in = new InputHandler(new BufferedReader(new InputStreamReader(s.getInputStream())), tcc);
			in.start();
			connection = true;
			return true;
		} catch (IOException e) {
			connection = false;
			s = null;
			return false;
		}
	}
	
	public void sendMessage(String message) {
		if (connection) {
			output.println(message);
		} else {
			System.out.println("There is no Connection");
		}
	}
	
	public void closeConnection() {
		sendMessage("leave");
		in.stopRunning();
		connection = false;
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean connection() {
		return connection;
	}
}
