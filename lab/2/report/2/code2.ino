int pin = A0; // pin entrée LDR
int n = 0;

void setup() 
{
  Serial.begin(9600); 
}

void loop() 
{
  n = analogRead(pin); // lit la valeur du capteur
  Serial.println(n); //affiche valeur
  delay(1000);
}
