package blindsSender;

import java.io.File;

import javax.swing.JOptionPane;

import blindsSenderBackEnd.IpLoader;
import blindsSenderBackEnd.Sender;

public class ComIpLoader extends IpLoader{
	private static String FILEPATH = "ipAddress.txt";
	
	@Override
	protected File getFile() {
		return new File(FILEPATH);
	}

	@Override
	public File newFile() {
		return getFile();
	}

	@Override
	public void connect(Sender sender) {
		String ipAddress = getIpAddressFromFile();
		if (ipAddress != "") {
			if (sender.connectToReceiver(ipAddress)) {
				System.out.println("connected from file");
				return;
			}
		}
		
		ipAddress = inputDialog("Enter the ip address of the framboos", "localhost");
		if (ipAddress == null) return;
		while (!sender.connectToReceiver(ipAddress)) {
			ipAddress = inputDialog("Couldn't connect, please try again", ipAddress);
			System.out.println("WRONG");
			if (ipAddress == null) return;
		}
		
		System.out.println("Connect via entering ipaddress, stored in file");
		this.saveIpAddressInFile(ipAddress);
		return;
	}
	
	public String inputDialog(String question, String attempt) {
		return (String) JOptionPane.showInputDialog(question, attempt);
	}
}
