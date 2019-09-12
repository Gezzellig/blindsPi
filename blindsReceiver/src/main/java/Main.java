import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	private static int stdMaxMovement = 39000;
	
	public static void main(String []args) {
		int maxMovement = askInt("Enter max Movement:", stdMaxMovement);
		int stepSleepTime = askInt("Enter Sleep time between steps:", 2);
		int dirSleepTime = askInt("Enter Sleep time between direction change:", 2);
		int pulseTime = askInt("Enter time duration of pulse:", 2);
		Controller controller = new Controller(maxMovement, stepSleepTime, dirSleepTime, pulseTime);
		Receiver receiver = new Receiver(controller);
		receiver.start();
		System.out.println("Everything is operational.");
		System.out.println("Press Ctrl-c to close the server");
	}
	
	public static int askInt(String message, int standard) {
		int res = standard;
		String s = "";
		boolean correct = false;
		System.out.println(message+"(empty means "+standard+")" );
		while (!correct) {
			try {
				correct = true;
		        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		        s = bufferRead.readLine();
		        res = Integer.parseInt(s);
		    } catch(NumberFormatException e) {
		    	if (!s.equals("")) {
		    		System.out.println("\""+s+"\" is not an integer, please retry");
		    		correct = false;
		    	}
		    } catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return res;
	}
}
