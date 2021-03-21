import threading
import time
import RPi.GPIO as GPIO

#GPIO.cleanup()
GPIO.setmode(GPIO.BCM)


class OutputPin:
    def __init__(self, pin_num, initial_state=GPIO.LOW):
        self.pin_num = pin_num
        self.state = initial_state
        GPIO.setup(pin_num, GPIO.OUT, initial=self.state)
        self.pin_high = initial_state == GPIO.HIGH

    def high(self):
        GPIO.output(self.pin_num, GPIO.HIGH)
        self.pin_high = True

    def low(self):
        GPIO.output(self.pin_num, GPIO.LOW)
        self.pin_high = False

    def pulse(self):
        self.high()
        self.low()

    def is_high(self):
        return self.pin_high

    def is_low(self):
        return not self.pin_high


class A4988:
    DIR_CHANGE_TIME = 0.5
    STEP_TIME = 0.003
    POWER_TIME = 0.005

    def __init__(self, step_pin_num, dir_pin_num, sleep_pin_num):
        self.step_pin = OutputPin(step_pin_num)
        # Low is right rotation, High is left rotation
        self.dir_pin = OutputPin(dir_pin_num)
        # Low is sleep mode, High is powered mode
        self.sleep_pin = OutputPin(sleep_pin_num)

    def on(self):
        print("on")
        self.sleep_pin.high()
        time.sleep(self.POWER_TIME)

    def off(self):
        self.sleep_pin.low()
        time.sleep(self.POWER_TIME)

    def set_dir_right(self):
        if self.dir_pin.is_high():
            self.dir_pin.low()
            time.sleep(self.DIR_CHANGE_TIME)

    def step_right(self):
        self.set_dir_right()
        self.step_pin.pulse()
        time.sleep(self.STEP_TIME)

    def set_dir_left(self):
        if self.dir_pin.is_low():
            self.dir_pin.high()
            time.sleep(self.DIR_CHANGE_TIME)

    def step_left(self):
        self.set_dir_left()
        self.step_pin.pulse()
        time.sleep(self.STEP_TIME)

    def status(self):
        return self.sleep_pin.is_high()


class Blind:
    MAX_STEP = 16000 #int(input("Fill in the max step:"))
    print("MAX_STEP: {}".format(MAX_STEP))
    cur_step = 0
    stop_moving = False

    def __init__(self, step_motor):
        self.step_motor = step_motor

    def ensure_not_moving(self):
        while self.is_moving():
            print("waiting for stopping of moving")
            self.stop_moving = True
            time.sleep(0.1)
        self.stop_moving = False

    def up(self):
        self.ensure_not_moving()
        self.step_motor.on()
        while not self.stop_moving and self.cur_step > 0:
            self.step_motor.step_right()
            self.cur_step -= 1
        self.step_motor.off()

    def down(self):
        self.ensure_not_moving()
        self.step_motor.on()
        while not self.stop_moving and self.cur_step < self.MAX_STEP:
            self.step_motor.step_left()
            self.cur_step += 1
        self.step_motor.off()

    def stop(self):
        self.ensure_not_moving()
        print('cur_step: {}'.format(self.cur_step))

    def is_moving(self):
        return self.step_motor.status()

    def debug_up(self):
        self.cur_step = 0
        print('DEBUG UP: Set cur_step to 0')

    def debug_down(self):
        self.cur_step = self.MAX_STEP
        print('DEBUG DOWN: Set cur_step to MAX_STEP')




class BlindControl:
    def __init__(self):
        right_step_motor = A4988(27, 22, 17)
        self.right_blind = Blind(right_step_motor)

    def right_up(self):
        threading.Thread(target=self.right_blind.up).start()

    def right_down(self):
        threading.Thread(target=self.right_blind.down).start()

    def right_stop(self):
        self.right_blind.stop()

    def debug_up(self):
        self.right_blind.debug_up()

    def debug_down(self):
        self.right_blind.debug_down()



