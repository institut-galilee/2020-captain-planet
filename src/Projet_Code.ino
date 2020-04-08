/*Code de projet IOT , Groupe captain_planet
 * 
 * 
 * 
 * 
 */
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
const char * API_KEY = "H8FPZCNB49A0MNIT"; 
unsigned long CHANNEL = 1022022 ;                 
const char *ssid =  "HONOR 20 Lite";                                    
const char *pass =  "HonorableThief";
const char* server = "api.thingspeak.com";
WiFiClient client;

//Les pins
 #define DHTPIN 14
 int redPin = 5;
 int greenPin = 3;
 int yellowPin =18 ;
 int D33 = 33;
 int buzzerPin=18;

 //les constants
 float m = -0.3376; //Slope 
 float b = 0.7165; //Y-Intercept 

#define DHTTYPE DHT22   // DHT 11 

DHT dht = DHT(DHTPIN, DHTTYPE);



void setup() {
  //RGB pins
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(yellowPin, OUTPUT);

  //analog input pin for the mq135
  pinMode(D33,INPUT);

  //input pin for the buzzer
  pinMode(buzzerPin,OUTPUT);
  
  
  Serial.begin(9600);
  // Setup sensor:
  dht.begin();

  WiFi.mode(WIFI_STA);
  ThingSpeak.begin(client);  
}

void loop() {
  digitalWrite(redPin,LOW);
  digitalWrite(yellowPin,LOW);
  digitalWrite(greenPin,LOW);
  //digitalWrite(buzzerPin, HIGH);
  
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
    readTemperatureAndHumidity();
    float t = readTemperatureDHT22();
    float h = readHumidityDHT22();
    /*int x = analogRead(D33);
    Serial.println(x);*/
    float a = readPPMData();

     

   /* ThingSpeak.setField(1, a);
    ThingSpeak.setField(2, t);
    ThingSpeak.setField(3, h);*/
    
    int airRes = ThingSpeak.writeField(CHANNEL,1,a,API_KEY);
    delay(20000);
    int tempRes = ThingSpeak.writeField(CHANNEL,2,t,API_KEY);
    delay(20000);
    int humRes = ThingSpeak.writeField(CHANNEL,3,h,API_KEY);
    delay(20000);
    
    // Write to the ThingSpeak channel
     
    //check for errors
    if (tempRes == 200) {
      Serial.println("temperature updated");
    }
    else {
      Serial.println("Problem updating temperature field. HTTP error code " + String(tempRes));
    }
   if (humRes == 200) {
      Serial.println("humidity updated");
    }
    else {
      Serial.println("Problem updating humdity field. HTTP error code " + String(humRes));
    }

    if (airRes == 200){
      Serial.println("air quality updated");
    }else {
      Serial.println("Problem updating air quality field. HTTP error code " + String(airRes));
    }

    delay(10000);
    
    
  
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

void greenFlash(){
 digitalWrite(redPin,LOW);
 digitalWrite(yellowPin,LOW);
 digitalWrite(greenPin,HIGH);
  
}
void yellowFlash(){
 digitalWrite(redPin,LOW);
 digitalWrite(greenPin,LOW);
 digitalWrite(yellowPin,HIGH);
 
}
void redFlash(){
digitalWrite(yellowPin,LOW);
 digitalWrite(greenPin,LOW);
 digitalWrite(redPin,HIGH);
}


float readHumidityDHT22(){
  float h = dht.readHumidity();
  return h ;
  
}
float readTemperatureDHT22(){
  float t = dht.readTemperature();
  return t ;
}

  float calculateR0(){
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
  return R0 ;
}


float readPPMData(){
  float sensor_volt; //Define variable for sensor voltage 
  float RS_gas; //Define variable for sensor resistance  
  float ratio; //Define variable for ratio
  int sensorValue = analogRead(D33); //Read analog values of sensor  
  Serial.print("SENSOR RAW VALUE = ");
  Serial.println(sensorValue);
  sensor_volt = sensorValue*(5.0/1023.0); //Convert analog values to voltage 
  Serial.print("Sensor value in volts = ");
  Serial.println(sensor_volt);
  RS_gas = ((5.0*10.0)/sensor_volt)-10.0; //Get value of RS in a gas
  Serial.print("Rs value = ");
  Serial.println(RS_gas);
  
  ratio = RS_gas/calculateR0();  // Get ratio RS_gas/RS_air
  
  Serial.print("Ratio = ");
  Serial.println(ratio);
  float ppm_log = (log10(ratio)-b)/m; //Get ppm value in linear scale according to the the ratio value  
  float ppm = pow(10, ppm_log); //Convert ppm value to log scale 
  if(ppm < 100){
    greenFlash();
  }else{
    if(ppm>100 && ppm<200){
      yellowFlash();
    }else if(ppm>200){
      redFlash();
    }
  }
  Serial.print("Our desired PPM = ");
  Serial.println(ppm);
  double percentage = ppm/10000; //Convert to percentage 
  Serial.print("Value in Percentage = "); //Load screen buffer with percentage value
  Serial.println(percentage); 
  delay(1000);
  return ppm;
}

