import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class InputHandler extends Thread {
	private Socket client;
	private MessageHandler messageHandler;
	private BufferedReader input;
	private PrintWriter output;
	private Receiver receiver;
	
	public InputHandler(Socket client, MessageHandler messageHandler, Receiver receiver) {
		this.client = client;
		this.messageHandler = messageHandler;
		this.receiver = receiver;
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sendMessageBack(messageHandler.sendTimerHandler());
	}
	
	public void run() {
		try {
			boolean connection = true;
			while (connection) {
				String message = input.readLine();
				if (message != null) {
					System.out.println(message);
					if (message.equals("leave")) {
						receiver.removeClient(this);
						connection = false;
					} else {
						handleMessage(message);
					}
				}
			}
			System.out.println("client left");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessageBack(String message) {
		output.println(message);
	}
	
	public void handleMessage(String message) {
		messageHandler.handleMessage(message);	
	}
}
