int pin = 13;

void setup() 
{
  Serial.begin(9600);
  pinMode(pin, OUTPUT); //pin 13 comme sortie
}

void loop() 
{
  while (Serial.available() > 0) 
  {
      int i = Serial.parseInt();  //lit le int arrivant du serial stream
      tone(pin,i,1000); //envoie le signal sonore de i Hz pour 1 seconde
      Serial.print("Freq: "); //affiche message
      Serial.println(i);  
  } 
}
