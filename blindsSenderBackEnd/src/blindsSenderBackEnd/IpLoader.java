package blindsSenderBackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class IpLoader {
	protected abstract File getFile();
	protected abstract File newFile();
	public abstract void connect(Sender sender);
	public abstract void closeApplication();
	
	protected String getIpAddressFromFile() {
		File ipFile = getFile();
		if (ipFile.exists()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(ipFile));
				String ipAdress = in.readLine();
				in.close();
				return ipAdress;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	protected void saveIpAddressInFile(String ipAddress) {
		File ipFile = getFile();
		try {
			if (!ipFile.exists()) {
				ipFile = newFile();
			}
			PrintWriter writer = new PrintWriter(ipFile);
			writer.println(ipAddress);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeIpFile() {
		File ipFile = getFile();
		ipFile.delete();
	}
}
