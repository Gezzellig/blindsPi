public class MessageHandler {
	private Controller controller;
	private TimerHandler timerHandler;
	
	public MessageHandler(Controller c, TimerHandler th) {
		controller = c;
		timerHandler = th;
	}
	
	public void handleMessage(String message) {
		String[] parts = message.split(":");
		if (parts[0].equals("t")) {
			String[] parts2 = parts[1].split(";");
			timerHandler.parseMessage(parts2[0], parts2[1]);
		} else {
			controller.handleCommand(parts[0]);
		}
	}

	public String sendTimerHandler() {
		return timerHandler.sendQueueContent();
	}
}
