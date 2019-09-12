import java.util.Date;

public class TimedCommand {
	private String command;
	private Controller controller;
	private Date executionDate;
	
	public TimedCommand(Controller c, String command, Date executionDate) {
		controller = c;
		this.command = command;
		this.executionDate = executionDate;
	}
	
	public Date getExecutionDate() {
		return executionDate;
	}
	
	public void execute() {
		controller.handleCommand(command);
	}
	
	public boolean equals(TimedCommand tc) {
		return command.equals(tc.getCommand()) && executionDate.compareTo(tc.getExecutionDate()) == 0;
	}
	
	public String getCommand() {
		return command;
	}

	public boolean before(TimedCommand tc) {
		return executionDate.before(tc.getExecutionDate());
	}
}
