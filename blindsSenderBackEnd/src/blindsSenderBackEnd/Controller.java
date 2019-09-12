package blindsSenderBackEnd;

import java.util.Date;

public class Controller{
	private Sender sender;
	private OutputHandler output;
	private TimedCommandContainer tcc;
	
	public Controller(IpLoader il) {
		tcc = new TimedCommandContainer(this);
		sender = new Sender(8804, tcc, il);
		output = new OutputHandler(sender);
	}
	
	public void onClose() {
		if (connection()) {
			sender.closeConnection();
		}
	}
	
	public void close() {
		onClose();
		System.exit(1);
	}
	
	public void logOut(IpLoader il) {
		il.removeIpFile();
		close();
	}
	
	public boolean connection(){
		return sender.connection();
	}
	
	public TimedCommandContainer getTcc() {
		return tcc;
	}
	
	/*redirect commands to OutputHandler*/
	public void lUp() {
		output.lUp();
	}
	public void lStop() {
		output.lStop();
	}
	public void lDown() {
		output.lDown();
	}
	public void bUp() {
		output.bUp();
	}
	public void bStop() {
		output.bStop();
	}
	public void bDown() {
		output.bDown();
	}
	public void rUp() {
		output.rUp();
	}
	public void rStop() {
		output.rStop();
	}
	public void rDown() {
		output.rDown();
	}
	public void tUp(String which, Date date) {
		output.tUp(which, date);
	}
	public void tDown(String which, Date date) {
		output.tDown(which, date);
	}
	public void removeTimedCommand(String command) {
		output.removeTimedCommand(command);
	}
}
