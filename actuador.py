import paho.mqtt.client as mqtt
import time
def on_connect(client, userdata, flags, rc):
  #print("Connected with result code "+str(rc))
  client.subscribe("alertaRango.nivel1.area1")



def publicar():
  mqttc = mqtt.Client('nuevo')
  mqttc.connect("172.24.42.22", 8083)
  mqttc.publish("actuador.nivel1.area1", 'apagado')
  mqttc.disconnect()
  print('imprime')



def on_message(client, userdata, msg):
  x = len(str(msg.payload))-1
  lista.append((str(msg.payload)[2:x]))
  #print(variable, 'interna')
  client.disconnect()

lista=list('0')
variable=''

class Actuador:

  def __init__(self, pUbicacion):
    self.estado=False
    self.ubicacion=pUbicacion

  def darEstado(self):
    return self.estado

  def cambiarEstado(self, parametro):
    self.estado=  parametro


actuador=Actuador("nivel1.area1")
print(actuador.darEstado())


while True:
  client = mqtt.Client("subscribtor")
  client.connect("172.24.42.22", 8083)
  client.on_connect = on_connect
  client.on_message = on_message

  
  variable=lista.pop()
  print(variable)
  if (variable != '0'):
    actuador.cambiarEstado(True)
    print(actuador.darEstado())
    print('se esta ocupado')
    time.sleep(3600)
    publicar()
    print('desperto')
    actuador.cambiarEstado(False)


  client.loop_forever()
