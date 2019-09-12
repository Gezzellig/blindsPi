import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Receiver extends Thread{
	private boolean running;
	private MessageHandler messageHandler;
	private ServerSocket tcpSocket;
	private List<InputHandler> clientList = new ArrayList<>();
	
	public Receiver(Controller controller) {
		messageHandler = new MessageHandler(controller, new TimerHandler(controller, this));
		try {
			this.tcpSocket = new ServerSocket(8804);
			tcpSocket.setSoTimeout(10000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void UpdateClients(String message) {
		for (InputHandler ih : clientList) {
			ih.sendMessageBack(message);
		}
	}
	
	public void removeClient(InputHandler client) {
		clientList.remove(client);
	}
	
	@Override
	public void run() {
		this.running = true;
		int i = 1;
		while (running) {
			try {
				Socket client = tcpSocket.accept();
				InputHandler handler = new InputHandler(client, messageHandler, this);
				System.out.println("New client connecting");
				clientList.add(handler);
				handler.start();
			}  catch (SocketTimeoutException e) {
				System.out.print("waiting on clients "+Integer.toString(i)+"\r");
				i++;
			}  catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			tcpSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Receiver is down.");
	}
	
	@Override 
	public void interrupt() {
		running = false;
	}
	
	@Override
	public String toString() {
		String address = "I do not know...";
		try {
			address = InetAddress.getLocalHost().getHostAddress()+":"+tcpSocket.getLocalPort();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return address;
	}
}