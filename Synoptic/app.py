import logging

import eventlet
from flask import Flask, render_template
from flask_mqtt import Mqtt
from flask_socketio import SocketIO
from flask_bootstrap import Bootstrap

eventlet.monkey_patch()

app = Flask(__name__)
app.config['TEMPLATES_AUTO_RELOAD'] = True
app.config['MQTT_BROKER_URL'] = 'localhost'
app.config['MQTT_BROKER_PORT'] = 1883
app.config['MQTT_CLIENT_ID'] = 'flask_mqtt'
app.config['MQTT_CLEAN_SESSION'] = True
app.config['MQTT_KEEPALIVE'] = 5

qos = 1

mqtt = Mqtt(app)
socketio = SocketIO(app)
bootstrap = Bootstrap(app)

@app.route('/')
def index():
    return render_template('index.html')

@socketio.on('subscribe_all')
def handle_subscribe_all():
    mqtt.subscribe('observations_filtered', 1)
    mqtt.subscribe('airquality', 1)
    mqtt.subscribe('noise', 1)
    mqtt.subscribe('luminosity', 1)
    mqtt.subscribe('humidity', 1)
    mqtt.subscribe('temperature', 1)
    mqtt.subscribe('weather_temp', 1)
    mqtt.subscribe('weather_temp_min', 1)
    mqtt.subscribe('weather_temp_max', 1)
    mqtt.subscribe('weather_temp_feels', 1)
    mqtt.subscribe('weather_humidity', 1)

@mqtt.on_message()
def handle_mqtt_message(client, userdata, message):
    data = dict(
        topic = message.topic,
        payload = str(message.payload.decode('UTF-8')))
    socketio.emit('mqtt_message', data=data)

@mqtt.on_log()
def handle_logging(client, userdata, level, buf):
    # print(level, buf)
    pass

if __name__ == '__main__':
    socketio.run(app, host='localhost', port=5000, use_reloader=False, debug=True)
