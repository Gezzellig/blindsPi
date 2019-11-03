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
    MAX_STEP = 10000
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
        print(self.cur_step)
        self.ensure_not_moving()
        self.step_motor.on()
        while not self.stop_moving and self.cur_step > 0:
            self.step_motor.step_right()
            self.cur_step -= 1
        self.step_motor.off()

    def down(self):
        print(self.cur_step)
        self.ensure_not_moving()
        self.step_motor.on()
        while not self.stop_moving and self.cur_step < self.MAX_STEP:
            self.step_motor.step_left()
            self.cur_step += 1
        self.step_motor.off()

    def is_moving(self):
        print("test moving")
        return self.step_motor.status()


def main():
    step_motor = A4988(27, 22, 17)
    #left_blind = A4988(3, 4, 2)


    right_blind = Blind(step_motor)
    threading.Thread(target=right_blind.down).start()
    time.sleep(1)
    threading.Thread(target=right_blind.down).start()
    time.sleep(3)
    threading.Thread(target=right_blind.up).start()


if __name__ == '__main__':
    main()
