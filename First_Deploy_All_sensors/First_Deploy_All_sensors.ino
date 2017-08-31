
#include <string.h>
#include <DHT.h>
#define DHTPIN 2
#define DHTTYPE DHT11
#include <math.h>
#include <Wire.h>
#include "TSL2561.h"
DHT dht(DHTPIN, DHTTYPE);
TSL2561 tsl(TSL2561_ADDR_FLOAT); 

//------------------------------------------------------------------------------

 int cO2Pin = 2;
 int soundPin = 3;
 int lightPin= 4;

 float temp = 0;
 float co2 = 0;
 float sound = 0;
 float light = 0;
 
 //-----------------------------------------------------------------------------------------
 void setup() { 
     Serial.begin(9600);
     dht.begin();
     tsl.begin();
     tsl.setGain(TSL2561_GAIN_16X);
     tsl.setTiming(TSL2561_INTEGRATIONTIME_13MS);
     // se fija la unidad de trabajo del sensor de temperatura.
 }
 // ejecuta el procesamiento del sensor
 //-----------------------------------------------------------------------------------
 void loop() {

 temp = dht.readTemperature();
 co2 = analogRead(cO2Pin);
 sound = analogRead(soundPin);
 sound = 20*log10(sound/0.00513);
 //----------------------------
 uint32_t lum = tsl.getFullLuminosity();
 uint16_t ir, full;
 ir = lum >> 16;
 full = lum & 0xFFFF;
 light = tsl.calculateLux(full, ir);
 //----------------------------

 Serial.print(temp);
 Serial.print("\t");
 Serial.print("C");
 Serial.print(co2);
 Serial.print("\t");
 Serial.print("ppm");
 Serial.print(sound);
 Serial.print("\t");
 Serial.print("DB");
 Serial.print(light);
 Serial.print("\t");
 Serial.print("Lux");

 Serial.println("");
 
 delay(1000);
 }  
