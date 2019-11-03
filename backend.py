from flask import Flask, request
from flask_cors import CORS
import socket
import requests

from stepmotor import A4988

app = Flask(__name__)
cors = CORS(app, resources={r"/api/*": {"origins": "*"}})



@app.route('/')
def index():
	return "Hello world"

@app.route('/api/door', methods=['POST'])
def door():
	print("REQUEST RECEIVED")
	pw = request.form.get("pw")
	duration = 5
	try:
		result = requests.post("http://192.168.8.139/door", data = {'pw':pw, 'duration':duration})
		print(result)
		if result.ok:
			return "SNELL, De deur is open voor {} seconden!".format(duration)
		else:
			return "Het wachtwoord is incorrect."
	except requests.exceptions.ConnectionError:
		return "De Arduino kon niet bereikt worden :("
	
@app.route('/api/gordijn', methods=['POST'])
def gordijn():
	print("GORDIJN REQUEST RECEIVED")
	action = request.form.get("action")
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	s.connect(("localhost", 8804))
	s.send(action.encode())
	s.close()	
	return action

@app.route('/test')
def test():
	motor = A4988(27, 22, 17)
	motor.up()


if __name__ == '__main__':
	app.run(debug=True, host='0.0.0.0')
