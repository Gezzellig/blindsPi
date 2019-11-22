from flask import Flask, request
from flask_cors import CORS
import requests
from datetime import datetime

import alarm
from stepmotor import BlindControl

app = Flask(__name__)
cors = CORS(app, resources={r'/api/*': {'origins': '*'}})

blind_control = BlindControl()


@app.route('/')
def index():
	return 'Hello world', 200


def open_door(pw):
	print('REQUEST RECEIVED')
	duration = 5
	try:
		result = requests.post('http://192.168.8.139/door', data={'pw': pw, 'duration': duration})
		print(result)
		if result.ok:
			return 'SNELL, De deur is open voor {} seconden!'.format(duration), 200
		else:
			return 'Het wachtwoord is incorrect.', 403
	except requests.exceptions.ConnectionError:
		return 'De Arduino kon niet bereikt worden :(', 405


@app.route('/api/door/quick/<pw>', methods=['GET'])
def door_quick(pw):
	return open_door(pw)


@app.route('/api/door', methods=['GET'])
def door():
	pw = request.args.get('pw')
	return open_door(pw)


@app.route('/api/gordijn/up', methods=['POST'])
def gordijn_up():
	print('up')
	blind_control.right_up()
	return 'Going up!', 200


@app.route('/api/gordijn/stop', methods=['POST'])
def gordijn_stop():
	print('stop')
	blind_control.right_stop()
	return 'Stopping', 200


@app.route('/api/gordijn/down', methods=['POST'])
def gordijn_down():
	print('down')
	blind_control.right_down()
	return 'Going down!', 200


@app.route('/api/gordijn/up/debug', methods=['POST'])
def gordijn_up_debug():
	print('debug up')
	blind_control.debug_up()
	return 'Debug up succeeded', 200


@app.route('/api/gordijn/down/debug', methods=['POST'])
def gordijn_down_debug():
	print('debug down')
	blind_control.debug_down()
	return 'Debug down succeeded', 200


@app.route('/api/gordijn/down/alarm', methods=['POST'])
def gordijn_down_alarm():
	alarm_datetime_str = request.form['alarm_datetime']
	alarm_datetime = datetime.strptime(alarm_datetime_str, '%Y-%m-%dT%H:%M')
	if alarm.create_alarm(alarm_datetime, blind_control.right_down):
		return '{}'.format(alarm_datetime), 200
	else:
		return '{}'.format(alarm_datetime), 400


if __name__ == '__main__':
	app.run(debug=True, host='0.0.0.0')
