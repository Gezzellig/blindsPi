public class Blind implements Runnable{
	private Motor motor;
	private int maxMovement, moved;
	private char command;
	private boolean running;
	
	public Blind(int maxMovement, Motor motor) {
		this.motor = motor;
		this.maxMovement = maxMovement;
		this.moved = maxMovement;
	}
	
	public void moveUp() {
		command = 'u';
		Thread th = new Thread(this);
		th.start();
	}
	
	public void moveDown() {
		command = 'd';
		Thread th = new Thread(this);
		th.start();
	}
	
	public void run() {
		stopRunning();
		running = true;
		motor.on();
		switch (command) {
		case 'u':
			moveTotalUp();
			break;
		case 'd':
			moveTotalDown();
			break;
		}
		motor.off();
		running = false;
	}
	
	private void moveTotalUp() {
		while (moved < maxMovement && running) {
			moved++;	
			motor.moveRight();
		}
	}
	
	private void moveTotalDown() {
		while (moved > 0 && running) {
			moved--;
			motor.moveLeft();
		}
	}
	
	public void stopRunning() {
		while (running) {
			running = false;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
