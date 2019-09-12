package blindsSenderBackEnd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputHandler {
	private Sender sender;
	private static String UP = "up", STOP = "stop", DOWN = "down", LEFT = "l", BOTH = "b", RIGHT = "r", TIMER = "t", DATESTRING = "yyyy-MM-dd-HH-mm", ADD = "a", REMOVE = "r";
	private SimpleDateFormat sdf = new SimpleDateFormat(DATESTRING);
	
	public OutputHandler(Sender sender) {
		this.sender = sender;
	}
	
	public void lUp() {
		sender.sendMessage(LEFT+","+UP);
	}
	
	public void lStop() {
		sender.sendMessage(LEFT+","+STOP);
	}

	public void lDown() {
		sender.sendMessage(LEFT+","+DOWN);
	}
	
	public void bUp() {
		sender.sendMessage(BOTH+","+UP);
	}
	
	public void bStop() {
		sender.sendMessage(BOTH+","+STOP);
	}

	public void bDown() {
		sender.sendMessage(BOTH+","+DOWN);
	}
	
	public void rUp() {
		sender.sendMessage(RIGHT+","+UP);
	}
	
	public void rStop() {
		sender.sendMessage(RIGHT+","+STOP);
	}

	public void rDown() {
		sender.sendMessage(RIGHT+","+DOWN);
	}
	
	public void tUp(String which, Date date) {
		if (date.after(new Date())) {
			sender.sendMessage(setUpTimerMessage(which,date)+UP);
		}
	}
	
	public void tDown(String which, Date date) {
		if (date.after(new Date())) {
			sender.sendMessage(setUpTimerMessage(which,date)+DOWN);
		}
	}
	
	private String setUpTimerMessage(String which, Date date) {
		String message = TIMER+":"+ADD+"!";
		message += sdf.format(date)+";";
		message += selectSide(which)+",";
		return message;
	}
	
	private String selectSide(String which) {
		switch (which) {
			case "Both": return BOTH;
			case "Left": return LEFT;
			case "Right": return RIGHT;
		}
		return null;
	}
	
	public void removeTimedCommand(String command) {
		sender.sendMessage(TIMER+":"+REMOVE+"!"+command);
	}
}
