const int LED2_Green = 2;
const int LED5_Yellow = 5;
const int LED10_Red = 10;
 

void setup() {                
  // initialize the digital pin as an output.
  pinMode(LED2_Green, OUTPUT);   
  pinMode(LED5_Yellow, OUTPUT);  
  pinMode(LED10_Red,OUTPUT);
}
 

void loop() {
  digitalWrite(LED2_Green, HIGH);      // turn the green LED on
  delay(4000);                  // wait for  4 seconds
  digitalWrite(LED2_Green, LOW);       // turn the green LED off by making the voltage LOW
  

  digitalWrite(LED5_Yellow, HIGH);      // turn the yellow LED on
  delay(3000);                  // wait for 3 seconds
  digitalWrite(LED5_Yellow, LOW);       // turn the yellow LED off by making the voltage LOW
  
  digitalWrite(LED10_Red, HIGH);      // turn the red LED on
  delay(5000);                  // wait for 5 seconds
  digitalWrite(LED10_Red, LOW);       // turn the red LED off by making the voltage LOW
  
}
