#include <SPI.h>
#include <WiFi101.h>
#include <ArduinoHttpClient.h>

const int pinMQ = A3;
const int pinNoise = A1;

/////// Wifi Settings ///////
char ssid[] = "Gloin";
char pass[] = "Gloin2014";
const char serverAddress[] = "192.168.0.103";  // server address
int port = 8080;
WiFiClient wifi;
HttpClient client = HttpClient(wifi, serverAddress, port);
int status;
int statusCode = 0;
String response;

void setup()
{
  
  Serial.begin(9600);
  
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to Network named: ");
    Serial.println(ssid);             
    // Connect to WPA/WPA2 network:
    status = WiFi.begin(ssid, pass);
  }
  
} 

void calibra(int nsegs)
{
  int tcalibra = 100 * nsegs; // 1000 = 1 segundo
  long tsini; // Tiempo de inicio del arduino
  int vact, vmax=0, vmin=1023; // Voltajes actual, maximo y minimo
  
  tsini = millis(); // millis() es el tiempo en milisegundos desde el encendido del Arduino

  ////////Calidad del aire////////

  int airQuality = analogRead(0);       // Obtener la informacion desde el pin analogico 0
  
  Serial.println("Calibrando sensor MQ135...");

  if (isnan(airQuality)) {
    Serial.println("Fallo al leer del sensor MQ135!");
    return;
  }
  
  Serial.print("AirQua=");
  Serial.print(airQuality, DEC);          
  Serial.println(" PPM");  
  
  // Establecer la dirección del servicio
  String path = "/airquality";
  String contentType = "application/json";
  String postData = "{\"amount\":";
  postData += airQuality;
  postData += "}";
  
  // Enviar la petición al servicio correspondiente
  client.post(path, contentType, postData);
  
  // Obtener el código de estado de respuesta
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  Serial.println("Respuesta desde el servidor");
  Serial.println(response);

  ////////Ruido////////

  Serial.println("Calibrando sensor de ruido...");

  int noise = analogRead (pinNoise) ; // Leer el valor de sonido

  if (isnan(noise)) {
    Serial.println("Fallo al leer del sensor de ruido!");
    return;
  }
  
  Serial.print("Sound=");
  Serial.print(noise, DEC);   
  Serial.println("");       
  
  // Establecer la dirección del servicio
  path = "/noise";
  postData = "{\"amount\":";
  postData += noise;
  postData += "}";
  
  // Enviar la petición al servicio correspondiente
  client.post(path, contentType, postData);
  
  // Obtener el código de estado de respuesta
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  Serial.println("Respuesta desde el servidor");
  Serial.println(response);

  delay(1000);
}

void loop()
{   
  calibra(7);
}
