package blindsSenderBackEnd;

import java.io.BufferedReader;
import java.io.IOException;

public class InputHandler extends Thread{
	private BufferedReader input;
	private boolean running;
	private TimedCommandContainer tcc;
	
	public InputHandler(BufferedReader input, TimedCommandContainer tcc) {
		this.input = input;
		this.tcc = tcc;
	}
	
	public void run() {
		running = true;
		try {
			while (running) {	
				String message = input.readLine();
				if (message != null) {
					handleMessage(message);
				}
			}
		} catch (java.net.SocketException e) {
			System.out.println("Connection lost");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopRunning() {
		running = false;
	}
	
	public void handleMessage(String message) {
		tcc.updateCommands(message);
	}
}
