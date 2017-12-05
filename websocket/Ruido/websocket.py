import json
import threading
from threading import Lock
from flask import Flask, render_template
from flask_socketio import SocketIO, emit
from kafka import KafkaConsumer
import sys
 
# Set this variable to "threading", "eventlet" or "gevent" to test the
async_mode = None
 
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret_thermalcomfort'
socketio = SocketIO(app, async_mode=async_mode)
thread = None
thread_lock = Lock()
topic = list()
# Ruta del dashboard
@app.route('/data/<topi>')
def index(topi):
    print(topi, 'no',file=sys.stderr)
    topic.append(topi)
    print(topic, 'prueba',file=sys.stderr)
    return render_template('index_ws.html', async_mode=socketio.async_mode)
 
# Consumidor del topic de Kafka "alta.piso1.local1". Cada valor recibido se envía a través del websocket.
def background_thread_websocket():
    print(topic, 'se suscribio',file=sys.stderr)
    x = topic.pop()
    print('=========================================================================================',file=sys.stderr)
    consumer = KafkaConsumer(x, group_id='Ruido', bootstrap_servers=['localhost:8090'])
    for message in consumer:
        json_data = json.loads(message.value.decode('utf-8'))
        sensetime = json_data['sensetime']
        mesures = json_data['Mesures']
        sense = mesures['Ruido']
 
        payload = {
            'time': sensetime,
            'value': sense
        }
        socketio.emit('mesurements', str(payload),
                      namespace='/Ruido')
 
# Rutina que se ejecuta cada vez que se conecta un cliente de websocket e inicia el conmunidor de Kafka
@socketio.on('connect', namespace='/Ruido')
def test_connect():
    print(topic, 'pruebaaaa',file=sys.stderr)
    global thread
    with thread_lock:
        if thread is None:
            thread = socketio.start_background_task(target=background_thread_websocket)
    emit('mesurements', "Connected!!!")
 
# Iniciar el servicio en el puerto 8086
if __name__ == '__main__':
    socketio.run(app, port=8088, debug=True)