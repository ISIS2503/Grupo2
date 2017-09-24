import json
import requests
from kafka import KafkaConsumer
print('comenzo')
x = str('piso1.nivel1.area1')
consumer = KafkaConsumer(x, group_id='mesures', bootstrap_servers=['localhost:8090'])


def publish(pVariable, pValor, pUnidad, pFecha, pUbicacion):
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

	publish("Temperatura", temperatura, unidadT, sensetime, x)
	publish("Monoxido", monoxido, unidadM, sensetime, x)
	publish("Ruido", ruido, unidadR, sensetime, x)
	publish("Iluminacion", iluminacion, unidadI, sensetime, x)


