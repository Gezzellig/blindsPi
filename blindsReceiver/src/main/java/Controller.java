import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Controller {
	private final GpioController gpio = GpioFactory.getInstance();
	private Blind leftBlind, rightBlind;
	
	public Controller(int maxMovement, int stepSleepTime, int dirSleepTime, int pulseTime) {
		leftBlind = new Blind(maxMovement, new A4988(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "Step", PinState.LOW),
													 gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "Dir", PinState.LOW),
													 gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "Sleep", PinState.LOW),
													 stepSleepTime, dirSleepTime, pulseTime));
		rightBlind = new Blind(maxMovement, new A4988(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Step", PinState.LOW),
													  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Dir", PinState.LOW),
													  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "Sleep", PinState.LOW),
													  stepSleepTime, dirSleepTime, pulseTime));
	}
	
	public void handleCommand(String command) {
		String[] parts = command.split(",");
		switch(parts[0].charAt(0)) {
			case('l'): {
				moveBlind(leftBlind,parts[1]);
				return;
			}
			case('r'): {
				moveBlind(rightBlind,parts[1]);
				return;
			}
			case('b'): {
				moveBlind(leftBlind,parts[1]);
				moveBlind(rightBlind,parts[1]);
				return;
			}
		}
	}
	
	private void moveBlind(Blind b, String s) {
		switch(s.charAt(0)){
			case ('u'): {
				b.moveUp();
				return;
			}
			case ('s'): {
				b.stopRunning();
				return;
			}
			case ('d'): {
				b.moveDown();
				return;
			}
		}
	}
}
