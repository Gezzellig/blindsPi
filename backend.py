from flask import Flask, request
from flask_cors import CORS
import socket
import requests

from stepmotor import BlindControl

app = Flask(__name__)
cors = CORS(app, resources={r"/api/*": {"origins": "*"}})

blind_control = BlindControl()


@app.route('/')
def index():
	return "Hello world", 200


def open_door(pw):
	print("REQUEST RECEIVED")
	duration = 5
	try:
		result = requests.post("http://192.168.8.139/door", data={'pw': pw, 'duration': duration})
		print(result)
		if result.ok:
			return "SNELL, De deur is open voor {} seconden!".format(duration), 200
		else:
			return "Het wachtwoord is incorrect.", 403
	except requests.exceptions.ConnectionError:
		return "De Arduino kon niet bereikt worden :(", 405


@app.route('/api/door/quick/<pw>', methods=['GET'])
def door_quick(pw):
	return open_door(pw)


@app.route('/api/door', methods=['GET'])
def door():
	pw = request.form.get("pw")
	print(pw)
	return open_door(pw)


@app.route('/api/gordijn/<action>', methods=['GET'])
def gordijn(action):
	print('GORDIJN REQUEST RECEIVED: {}'.format(action))
	splitted = action.split(',')
	if splitted[0] == "debug":
		if splitted[1] == "down":
			blind_control.debug_down()
			return "Debug succeeded", 200

	real_action = splitted[1]
	if real_action == "up":
		print('up')
		blind_control.right_up()
	elif real_action == "stop":
		print('stop')
		blind_control.right_stop()
		print("DONDEE")
	elif real_action == "down":
		print('down')
		blind_control.right_down()
	else:
		return 'Unknown action: {}'.format(action), 400
	return "Action approved", 200


if __name__ == '__main__':
	app.run(debug=True, host='0.0.0.0')
