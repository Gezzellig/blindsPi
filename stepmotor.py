import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)


class output_pin:
    def __init__(self, pin_num, initial_state=GPIO.LOW):
        self.pin_num = pin_num
        self.state = initial_state
        GPIO.setup(pin_num, GPIO.OUT, initial=self.state)

    def high(self):
        GPIO.output(self.pin_num, GPIO.HIGH)


class A4988:
    def __init__(self, step_pin, dir_pin, sleep_pin):
        self.step_pin = step_pin

        GPIO.setup(dir_pin, GPIO.OUT, initial=GPIO.LOW)
        GPIO.setup(sleep_pin, GPIO.OUT, initial=GPIO.LOW)

    def up(self):
        print('going up')
        GPIO.output(self.step_pin, GPIO.HIGH)


def main():
    right_blind = A4988(9, 7, 8)
    #left_blind = A4988(2, 3, 0)
    right_blind.up()
    GPIO.cleanup()

if __name__ == '__main__':
    main()
