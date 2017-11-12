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
import redis
import hashlib

# redis ===============================================================================================================================

r = redis.Redis(host='157.253.229.201')

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
        print(lista)
    else:
        suma = sum( i for i in lista)
        promedio = suma/10
        lista[:]=[]
        if not (inf<=medicion<=sup):
            mensajeA = 'Sensor '+tipoVariable+' fuera de rango. promeido actual '+str(promedio)
            mqtt('alertaRango.nivel1.area1', mensajeA)

def publishToBack(pVariable, pValor, pUnidad, pFecha, pUbicacion, pToken):
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
    response = requests.post(url, data=json.dumps(payload), headers={'Content-type': 'application/json', 'Authorization': pToken})
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
    #publishToBack(tipo, valor, unidad, fecha, ubicacion)


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

def abc():
    domain = 'arquisoft2017-jsprieto10.auth0.com' # Your Auth0 Domain
    api_identifier = "uniandes.edu.co/thermalcomfort" # API Identifier of your API
    client_id = "lbNhjDP3VUlX4JklDT3ePmS5wiaX5w52" # Client ID of your Non Interactive Client
    client_secret = "PiCHm39eZ9PtVIdXeLeKtprR8clbKSGXZNptQ3l46TG1k5o9vftuGs6sHugTLc3F" # Client Secret of your Non Interactive Client
    grant_type = "client_credentials" # OAuth 2.0 flow to use

    base_url = "https://{domain}".format(domain=domain) + "/oauth/token"
    payload = {
        'client_id': client_id,
        'client_secret': client_secret,
        'audience': api_identifier,
        'grant_type': grant_type
    }
    response = requests.post(base_url, data=json.dumps(payload), headers={'Content-type': 'application/json'})
    return str(response.json())
    

# Body =====================================================================================================================



print('comenzo')
x = str('nivel1.area1')
consumer = KafkaConsumer(x, group_id='hola', bootstrap_servers=['localhost:8090'])

print('paso')
ultimasT = list()
ultimasM = list()
ultimasR = list()
ultimasI = list()



for mensaje in consumer:

    json_data = json.loads(mensaje.value.decode('utf-8'))
    credenciales = json_data['credenciales']
    username = str(credenciales['usuario'])
    password = str(credenciales['password'])
    passwordtobytes = password.encode()
    passR = r.get(username).decode("utf-8")
    hexa = hashlib.md5(passwordtobytes).hexdigest()
    print(passR, 'and', hexa)
    if passR == hexa:
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
     
        stamp = time.time()-300000
        times = milis(sensetime)

        if times>stamp:
            mqtt('alertaLinea.nivel1.area1', 'Sensor fuera de linea en '+x)


        anteriores(ultimasT, unidadT, float(temperatura), temperaturaInf, temperaturaSup)
        anteriores(ultimasR, unidadR, float(ruido), ruidoInf, ruidoSup)
        anteriores(ultimasM, unidadM, float(monoxido), monoxidoInf, monoxidoSup)
        anteriores(ultimasI, unidadI, float(iluminacion), iluminacionInf, iluminacionSup)


        publishToBack("Temperatura", temperatura, unidadT, sensetime, x, abc())
        publishToBack("Monoxido", monoxido, unidadM, sensetime, x, abc())
        publishToBack("Ruido", ruido, unidadR, sensetime, x, abc())
        publishToBack("Iluminacion", iluminacion, unidadI, sensetime, x, abc())
