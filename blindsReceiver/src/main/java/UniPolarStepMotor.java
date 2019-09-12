import com.pi4j.io.gpio.GpioPinDigitalOutput;

public abstract class UniPolarStepMotor extends Motor {

	protected final GpioPinDigitalOutput blue, pink, yellow, orange;
	protected final int SLEEPTIME;
	
	public UniPolarStepMotor(GpioPinDigitalOutput pin1, GpioPinDigitalOutput pin2, GpioPinDigitalOutput pin3, GpioPinDigitalOutput pin4, int sleeptime) {
		this.blue = pin1;
		this.pink = pin2;
		this.yellow = pin3;
		this.orange = pin4;
		this.SLEEPTIME = sleeptime;
	}
}
