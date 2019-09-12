package blindsSenderBackEnd;

import java.util.Observable;

public class TimedCommandContainer extends Observable{
	private String[] commands;
	private Controller controller;
	
	public TimedCommandContainer(Controller controller) {
		this.controller = controller;
		this.commands = new String[0];
	}
	
	public void updateCommands(String message) {
		if (message.equals("")) {
			commands = new String[0];
		} else {
			commands = message.split(":");
		}
	 	changed();
	}
	
	private void changed() {
		setChanged();
		notifyObservers();
	}
	
	public void remove(String s) {
		controller.removeTimedCommand(s);
	}
	
	public String[] getCommands() {
		return commands;
	}
}
