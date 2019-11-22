import threading
from datetime import datetime, timedelta


def create_alarm(alarm_datetime, function):
    if alarm_datetime <= datetime.now():
        return False
    delay = (alarm_datetime - datetime.now()).total_seconds()
    threading.Timer(delay, function).start()
    return True


def test():
    print("test")


if __name__ == '__main__':
    now = datetime.now()
    delta = timedelta(seconds=5)
    alarm_datetime = now + delta
    print(create_alarm(alarm_datetime, test))

