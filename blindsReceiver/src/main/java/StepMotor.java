import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class StepMotor extends UniPolarStepMotor {
	private GpioPinDigitalOutput currentPowered1, currentPowered2;
	
	public StepMotor(GpioPinDigitalOutput pin1, GpioPinDigitalOutput pin2, GpioPinDigitalOutput pin3, GpioPinDigitalOutput pin4, int sleepTime) {
		super(pin1,pin2,pin3,pin4, sleepTime);
		currentPowered1 = blue;
		currentPowered2 = pink;
	}
	
	public void moveRight() {
		if (currentPowered2 == blue) {
			setCurrentPoweredRight(pink);
		} else if (currentPowered2 == pink) {
			setCurrentPoweredRight(yellow);
		} else if (currentPowered2 == yellow) {
			setCurrentPoweredRight(orange);
		} else if (currentPowered2 == orange) {
			setCurrentPoweredRight(blue);
		}
		try {
			Thread.sleep(SLEEPTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void moveLeft() {
		if (currentPowered1 == blue) {
			setCurrentPoweredLeft(orange);
		} else if (currentPowered1 == pink) {
			setCurrentPoweredLeft(blue);
		} else if (currentPowered1 == yellow) {
			setCurrentPoweredLeft(pink);
		} else if (currentPowered1 == orange) {
			setCurrentPoweredLeft(yellow);
		}
		try {
			Thread.sleep(SLEEPTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void on() {
		currentPowered1.high();
		currentPowered2.high();
	}
	
	public void off() {
		currentPowered1.low();
		currentPowered2.low();
	}
	
	private void setCurrentPoweredLeft(GpioPinDigitalOutput pin) {
		currentPowered2.low();
		currentPowered2 = currentPowered1;
		currentPowered1 = pin;
		currentPowered1.high();
	}
	
	private void setCurrentPoweredRight(GpioPinDigitalOutput pin) {
		currentPowered1.low();
		currentPowered1 = currentPowered2;
		currentPowered2 = pin;
		currentPowered2.high();
	}
}
