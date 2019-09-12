import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class HalfStepMotor extends UniPolarStepMotor {
	private int step;
	
	public HalfStepMotor(GpioPinDigitalOutput pin1, GpioPinDigitalOutput pin2, GpioPinDigitalOutput pin3, GpioPinDigitalOutput pin4, int sleeptime) {
		super(pin1,pin2,pin3,pin4, sleeptime);
		step = 0;
	}
	
	public void moveRight() {
		switch(step) {
		case 0: 
			pink.high();
			break;
		case 1:
			blue.low();
			break;
		case 2:
			yellow.high();
			break;
		case 3:
			pink.low();
			break;
		case 4:
			orange.high();
			break;
		case 5:
			yellow.low();
			break;
		case 6:
			blue.high();
			break;
		case 7:
			orange.low();
			break;			
		}
		step = (step+1)%8;
		
		try {
			Thread.sleep(SLEEPTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void moveLeft() {
		switch(step) {
		case 0:
			orange.high();
			break;
		case 1:
			pink.low();
			break;
		case 2:
			blue.high();
			break;
		case 3:
			yellow.low();
			break;
		case 4:
			pink.high();
			break;
		case 5:
			orange.low();
			break;
		case 6:
			yellow.high();
			break;
		case 7:
			blue.low();
			break;
		}
		if (step == 0) {
			step = 7;
		} else {
			step--;
		}
		
		try {
			Thread.sleep(SLEEPTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void on() {
		switch(step) {
		case 0:
			blue.high();
			break;
		case 1:
			blue.high();
			pink.high();
			break;
		case 2:
			pink.high();
			break;
		case 3:
			pink.high();
			yellow.high();
			break;
		case 4:
			yellow.high();
			break;
		case 5:
			yellow.high();
			orange.high();
			break;
		case 6:
			orange.high();
			break;
		case 7:
			orange.high();
			blue.high();
		}
	}
	
	public void off() {
		switch(step) {
		case 0:
			blue.low();
			break;
		case 1:
			blue.low();
			pink.low();
			break;
		case 2:
			pink.low();
			break;
		case 3:
			pink.low();
			yellow.low();
			break;
		case 4:
			yellow.low();
			break;
		case 5:
			yellow.low();
			orange.low();
			break;
		case 6:
			orange.low();
			break;
		case 7:
			orange.low();
			blue.low();
		}
	}
}

