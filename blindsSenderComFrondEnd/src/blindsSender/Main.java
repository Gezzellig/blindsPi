package blindsSender;

import blindsSenderBackEnd.Controller;

public class Main {
	
	public static void main(String []args) {
		Controller controller = new Controller(new ComIpLoader());
		new OptionMenu(controller);
	}
}
