

int ledPin = 5;   

void setup() {
  
}

void loop() {
  //fade in from min to max in decrements of 5 points :
  for (int fadeValue = 0 ; fadeValue <= 255; fadeValue += 5) {
    
    analogWrite(ledPin, fadeValue);
    // wait for 30 milliseconds to see the fading effect
    delay(30);
  }

  // fade out from max to min in increments of 5 points:
  for (int fadeValue = 255 ; fadeValue >= 0; fadeValue -= 5) {
   
    analogWrite(ledPin, fadeValue);
    // wait for 30 milliseconds to see the dimming effect
    delay(30);
  }
}
