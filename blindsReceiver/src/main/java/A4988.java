import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class A4988 extends Motor {
	protected final GpioPinDigitalOutput step, dir, sleep;
	protected final int STEPSLEEPTIME, DIRSLEEPTIME, PULSETIME;
	
	public A4988(GpioPinDigitalOutput step, GpioPinDigitalOutput dir, GpioPinDigitalOutput sleep, int stepSleeptime, int dirSleeptime, int pulseTime) {
		this.step = step;
		this.dir = dir;
		this.sleep = sleep;
		this.STEPSLEEPTIME = stepSleeptime;
		this.DIRSLEEPTIME = dirSleeptime;
		this.PULSETIME = pulseTime;
	}
	
	@Override
	public void moveRight() {
		try {
			if (dir.isHigh()) {
				dir.low();
				Thread.sleep(DIRSLEEPTIME);
			}
			pulse();
			Thread.sleep(STEPSLEEPTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void moveLeft() {
		try {
			if (dir.isLow()) {
				dir.high();
				Thread.sleep(DIRSLEEPTIME);
			}
			pulse();
			Thread.sleep(STEPSLEEPTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void on() {
		sleep.high();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void off() {
		sleep.low();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void pulse() throws InterruptedException {
		step.high();
		Thread.sleep(PULSETIME);
		step.low();
	}

}
