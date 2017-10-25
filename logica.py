import json
import requests
from kafka import KafkaConsumer
import arrow
import pandas as pd
import time
import paho.mqtt.client as mqt
import json
from flask import jsonify
import sys

# constantes =============================================================================================================================


temperaturaInf = 21.5
temperaturaSup = 27.0

ruidoInf = 80
ruidoSup = 85

monoxidoInf = 0
monoxidoSup = 350

iluminacionInf = 100
iluminacionSup = 500



# clase ======================================================================================================================================

class medicion:
    def __init__(self, pVariable, pUnidad, pValor, pFecha, pUbicacion):
        self.variable=pVariable
        self.unidad=pUnidad
        self.valor=pValor
        self.fecha=pFecha
        self.ubicacion=pUbicacion

    def toJSON(self):
        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)



# JMETER ========================================================================================================================================================

def mqtt(topic, mensaje):
    mqttc = mqt.Client('nuevo')
    mqttc.connect("172.24.42.22", 8083)
    mqttc.publish(topic, mensaje)



def anteriores(lista, tipoVariable, medicion, inf, sup):
    if len(lista)<10:
        lista.insert(0, medicion)

    else:
        suma = sum( i for i in lista)
        promedio = suma/10
        lista[:]=[]
        if not (inf<=medicion<=sup):
            mensajeA = 'Sensor '+tipoVariable+' fuera de rango. promeido actual '+str(promedio)
            mqtt('alertaRango.nivel1.area1', mensajeA)

def publishToBack(pVariable, pValor, pUnidad, pFecha, pUbicacion):
    q = '"'
    url = 'http://localhost:8080/measure'
    payload = {
        "variable": pVariable,
        "unidad": pUnidad,
        "valor": pValor,
        "unidad": pUnidad,
        "fecha": pFecha,
        "ubicacion": pUbicacion
    }
    print(payload)
    response = requests.post(url, data=json.dumps(payload), headers={'Content-type': 'application/json'})
    print("Response Status Code: " + str(response.status_code))


def jmeter(lista, medicion):
    tipo = medicion.variable
    valor = float(medicion.valor)
    fecha = medicion.fecha
    unidad = medicion.unidad
    ubicacion = medicion.ubicacion
    mensaje = tipo
    print(mensaje, file=sys.stderr)
    anteriores(lista, tipo, valor, temperaturaInf, temperaturaSup)
    print(lista, file=sys.stderr)
    publishToBack(tipo, valor, unidad, fecha, ubicacion)


# servidor ================================================================================================================================================
"""

from flask import Flask
from flask import request
app = Flask(__name__)

meters = list()
@app.route("/<variable>/<unidad>/<valor>/<fecha>/<ubicacion>", methods = ['POST'])
def index(variable, unidad, valor, fecha, ubicacion):
  if request.method == 'POST':
    print('llego')
    x =medicion(variable, unidad, valor, fecha, ubicacion)
    jmeter(meters, x)
    response = app.response_class(
    response=x.toJSON(),
    status=200,
    mimetype='application/json'
    )
  return response

if __name__ == "__main__":
    app.run(debug=True, port=8000, host='172.24.42.22')

"""
# funciones =======================================================================================================================



def milis(ti):
    t = pd.Timestamp(ti)
    return time.mktime(t.timetuple())




# Body =====================================================================================================================



print('comenzo')
x = str('nivel1.area1')
consumer = KafkaConsumer(x, group_id='hola', bootstrap_servers=['localhost:8090'])

fechainicial = str(arrow.utcnow().shift(minutes=-1))

fechaAnterior = milis(fechainicial)
print(fechaAnterior, "que pasaaa")

ultimasT = list()
ultimasM = list()
ultimasR = list()
ultimasI = list()



for mensaje in consumer:
    json_data = json.loads(mensaje.value.decode('utf-8'))
    print(mensaje)
    print('==============================================================================')
    print()
    sensetime = str(json_data['sensetime'])
    mesures = json_data['Mesures']
    temperatura= str(mesures['Temperatura'])
    unidadT = str(mesures['Unidad temperatura'])
    monoxido = str(mesures['Monoxido de carbono'])
    unidadM = str(mesures['Unidad monoxido'])
    ruido = str(mesures['Ruido'])
    unidadR = str(mesures['Unidad ruido'])
    iluminacion = str(mesures['Iluminacion'])
    unidadI = str(mesures['Unidad iluminacion'])
 
    stamp = fechaAnterior+300000
    times = milis(sensetime)

    if times>stamp:
        mqtt('alertaLinea.nivel1.area1', 'Sensor fuera de linea en '+x)
    
    fechaAnterior = times


    anteriores(ultimasT, unidadT, float(temperatura), temperaturaInf, temperaturaSup)
    anteriores(ultimasR, unidadR, float(ruido), ruidoInf, ruidoSup)
    anteriores(ultimasM, unidadM, float(monoxido), monoxidoInf, monoxidoSup)
    anteriores(ultimasI, unidadI, float(iluminacion), iluminacionInf, iluminacionSup)


    publishToBack("Temperatura", temperatura, unidadT, sensetime, x)
    publishToBack("Monoxido", monoxido, unidadM, sensetime, x)
    publishToBack("Ruido", ruido, unidadR, sensetime, x)
    publishToBack("Iluminacion", iluminacion, unidadI, sensetime, x)


