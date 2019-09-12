import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TimerHandler implements Runnable {
	private Controller controller;
	private Receiver receiver;
	private static String DATESTRING = "yyyy-MM-dd-HH-mm";
	private SimpleDateFormat sdf = new SimpleDateFormat(DATESTRING);
	private List<TimedCommand> tCQueue;
	private boolean running;
	
	public TimerHandler(Controller controller, Receiver receiver) {
		this.controller = controller;
		this.receiver = receiver;
		tCQueue = new LinkedList<TimedCommand>();
	}
	
	public void parseMessage(String message, String command) {
		String[] parts = message.split("!");
		switch (parts[0].charAt(0)){
			case ('a'): {
				addTimedCommand(parts[1],command);
				return;
			}
			case ('r'): {
				removeTimedCommand(parts[1],command);
				return;
			}
		}
	}
	
	private void addTimedCommand(String time, String command) {
		addInOrder(new TimedCommand(controller, command, generateDate(time)));
		receiver.UpdateClients(sendQueueContent());
		checkRunning();
	}
	
	private void removeTimedCommand(String time, String command) {
		TimedCommand tc = new TimedCommand(controller, command, generateDate(time));
		for (TimedCommand tq : tCQueue) {
			if (tc.equals(tq)) {
				tCQueue.remove(tq);
				break;
			}
		}
		receiver.UpdateClients(sendQueueContent());
	}
	
	private void addInOrder(TimedCommand tc) {
		for (int i=0; i<tCQueue.size(); i++) {
			if (tc.before(tCQueue.get(i))) {
				tCQueue.add(i, tc);
				return;
			}
		}
		tCQueue.add(tc);
	}
	
	private Date generateDate(String time) {
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void checkRunning() {
		if (!running) {
			Thread t = new Thread(this);
			t.start();
		}
	}

	public void run() {
		running = true;
		while (running && !tCQueue.isEmpty()) {
			if (tCQueue.get(0).getExecutionDate().before(new Date())) {
				TimedCommand tc = tCQueue.remove(0);
				receiver.UpdateClients(sendQueueContent());
				tc.execute();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		running = false;
	}
	
	public String sendQueueContent() {
		String res = "";
		boolean first = true;
		for(TimedCommand tc : tCQueue) {
			if (!first) {
				res+=":";
			}
			res+=(sdf.format(tc.getExecutionDate())+";"+tc.getCommand());
			first = false;
		}
		return res;
	}
}
