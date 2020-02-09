/*
 * Author: Elecrow engle
 * Date:7/12/2017
 * IDE V1.8.2 
 * Email:engle@elecrow.com
 * Function:
 */
// Pin 13 has an LED connected on most Arduino boards.
// give it a name:
const int LED2_Green = 2;
const int LED5_Yellow = 5;
const int LED10_Red = 10;
 
// the setup routine runs once when you press reset:
void setup() {                
  // initialize the digital pin as an output.
  pinMode(LED2_Green, OUTPUT);   
  pinMode(LED5_Yellow, OUTPUT);  
  pinMode(LED10_Red,OUTPUT);
}
 
// the loop routine runs over and over again forever:
void loop() {
  digitalWrite(LED2_Green, HIGH);      // turn the LED on (HIGH is the voltage level)
  delay(100);                  // wait for a second
  digitalWrite(LED2_Green, LOW);       // turn the LED off by making the voltage LOW
  delay(100);

  digitalWrite(LED5_Yellow, HIGH);      // turn the LED on (HIGH is the voltage level)
  delay(100);                  // wait for a second
  digitalWrite(LED5_Yellow, LOW);       // turn the LED off by making the voltage LOW
  delay(100);
  digitalWrite(LED10_Red, HIGH);      // turn the LED on (HIGH is the voltage level)
  delay(100);                  // wait for a second
  digitalWrite(LED10_Red, LOW);       // turn the LED off by making the voltage LOW
  
}
