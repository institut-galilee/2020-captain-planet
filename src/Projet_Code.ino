/*Code de projet IOT , Groupe captain_planet
 * 
 * 
 * 
 * 
 */
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include "Arduino.h"
#include <WiFi.h>
#include <ThingSpeak.h>

//Parametres de connexion avec ThingSpeak
const char * API_KEY = "BA8YEDX40DJHOP0M"; 
unsigned long CHANNEL = 1015166 ;                 
const char *ssid =  "HONOR 20 Lite";                                    
const char *pass =  "HonorableThief";
const char* server = "api.thingspeak.com";
WiFiClient client;

//Les pins
 #define DHTPIN 14
 int redPin = 27;
 int greenPin = 26;
 int bluePin = 25;
 int D33 = 33;

#define DHTTYPE DHT22   // DHT 11 

DHT dht = DHT(DHTPIN, DHTTYPE);



void setup() {
  //RGB pins
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);

  //analog input pin for the mq135
  pinMode(D33,INPUT);
  
  
  Serial.begin(9600);
  // Setup sensor:
  dht.begin();

  WiFi.mode(WIFI_STA);
  ThingSpeak.begin(client);  
}

void loop() {
  float t = readTemperatureDHT22();
  float h = readHumidityDHT22();
  int x = analogRead(D33);
  Serial.println(x);
  ReadMQ135Data();
  
/*
 * Connexion au plateforme thingspeak
 */
   if (WiFi.status() != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    while (WiFi.status() != WL_CONNECTED) {
      WiFi.begin(ssid, pass);
      Serial.print(".");
      delay(5000);
    }
    Serial.println("\nConnected.");
  }
    
  
    ThingSpeak.setField(1, t);
    ThingSpeak.setField(2, h);
   //int writeField(unsigned long channelNumber, unsigned int field, float value, const char * writeAPIKey)
   int tempRes = ThingSpeak.writeField(CHANNEL,1,t,API_KEY);
   int humRes = ThingSpeak.writeField(CHANNEL,2,h,API_KEY);
    
    // Write to the ThingSpeak channel
     
    //check for errors
    if (tempRes == 200) {
      Serial.println("temperature updated");
    }
    else {
      Serial.println("Problem updating temperature channel. HTTP error code " + String(tempRes));
    }
   if (humRes == 200) {
      Serial.println("humidity updated");
    }
    else {
      Serial.println("Problem updating humdity channel. HTTP error code " + String(humRes));
    }

    delay(30000);
    
    
  
 //readTemperatureAndHumidity();
 //lightRedRGB();
 //ReadMQ135Data();
}



void readTemperatureAndHumidity(){
   // Wait a few seconds between measurements:
  delay(2000);
  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  // Read the humidity in %:
  float h = dht.readHumidity();
  // Read the temperature as Celsius:
  float t = dht.readTemperature();
  // Read the temperature as Fahrenheit:
  float f = dht.readTemperature(true);
  // Check if any reads failed and exit early (to try again):
  if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  // Compute heat index in Fahrenheit (default):
  float hif = dht.computeHeatIndex(f, h);
  // Compute heat index in Celsius:
  float hic = dht.computeHeatIndex(t, h, false);
  Serial.print("Humidity: ");
  Serial.print(h);
  Serial.print(" % ");
  Serial.print("Temperature: ");
  Serial.print(t);
  Serial.print(" \xC2\xB0");
  Serial.print("C | ");
  Serial.print(f);
  Serial.print(" \xC2\xB0");
  Serial.print("F ");
  Serial.print("Heat index: ");
  Serial.print(hic);
  Serial.print(" \xC2\xB0");
  Serial.print("C | ");
  Serial.print(hif);
  Serial.print(" \xC2\xB0");
  Serial.println("F");
}

void lightRedRGB(){
digitalWrite(redPin,HIGH);
  
}
void lightYellowRGB(){
 digitalWrite(redPin,HIGH);
 digitalWrite(greenPin,HIGH);  
}
void lightGreenRGB(){
digitalWrite(greenPin,HIGH);  
}


float readHumidityDHT22(){
  float h = dht.readHumidity();
  return h ;
  
}
float readTemperatureDHT22(){
  float t = dht.readTemperature();
  return t ;
}

void ReadMQ135Data(){
  float sensor_volt; //Define variable for sensor voltage 
  float RS_air; //Define variable for sensor resistance
  float R0; //Define variable for R0
  float sensorValue=0.0; //Define variable for analog readings 
  Serial.print("Sensor Reading = ");
  Serial.println(analogRead(D33));
  
  for(int x = 0 ; x < 500 ; x++) //Start for loop 
  {
    sensorValue = sensorValue + analogRead(D33); //Add analog values of sensor 500 times 
  }
  sensorValue = sensorValue/500.0; //Take average of readings
  Serial.print("Average = ");
  Serial.println(sensorValue);
  sensor_volt = sensorValue*(5.0/1023.0); //Convert average to voltage 
  RS_air = ((5.0*10.0)/sensor_volt)-10.0; //Calculate RS in fresh air 
  R0 = RS_air/3.7; //Calculate R0 
 
  Serial.print("R0 = "); //Display "R0"
  Serial.println(R0); //Display value of R0 
  delay(1000); //Wait 1 second 
}
