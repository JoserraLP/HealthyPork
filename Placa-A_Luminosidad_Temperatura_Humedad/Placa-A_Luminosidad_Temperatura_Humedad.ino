#include <WiFi101.h>
#include <ArduinoHttpClient.h>
#include <DHT.h>
#include <SPI.h>

#define DHTTYPE DHT11   // DHT 11
//#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321
//#define DHTTYPE DHT21   // DHT 21 (AM2301)

const int pinLDR = A2;
const int pinDHT = 5;

/////// Wifi Settings ///////
char ssid[] = "Gloin";
char pass[] = "Gloin2014";
const char serverAddress[] = "192.168.0.103";  // server address
int port = 8080;
WiFiClient wifi;
HttpClient client = HttpClient(wifi, serverAddress, port);
int status = WL_IDLE_STATUS;
int statusCode = 0;
String response;
DHT dht(pinDHT, DHTTYPE);

void setup()
{
  pinMode(pinLDR, INPUT);
  dht.begin();
  
  //Serial.begin(9600);

  while (status != WL_CONNECTED) {
    //Serial.print("Attempting to connect to Network named: ");
    //Serial.println(ssid);             
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

  ////////Luminosidad////////

  // Obtener el valor mas alto de voltaje
  //Serial.println("Calibrando sensor LDR...");
  while (millis()-tsini < tcalibra) {
    vact = analogRead(pinLDR);
    if(vact>vmax) vmax=vact;
    if(vact<vmin) vmin=vact;
  }

  //Serial.print(vmax);

  // Establecer la dirección del servicio
  String path = "/luminosity";
  String contentType = "application/json";
  String postData = "{\"amount\":";
  postData += vmax;
  postData += "}";
  
  
  // Enviar la petición al servicio correspondiente
  client.post(path, contentType, postData);
  
  // Obtener el código de estado de respuesta
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  //Serial.println("Respuesta desde el servidor");
  //Serial.println(response);
  
  
  ////////Temperatura/Humedad////////

  // Serial.println("Calibrando sensor DHT...");
  // Leer la humedad y la temperatura tarda 250 ms
  float humidity = dht.readHumidity();
  float temperature = dht.readTemperature();

  if (isnan(humidity) || isnan(temperature)) {
    // Serial.println("Fallo al leer del sensor DHT!!");
    return;
  }

  // Establecer la dirección del servicio
  path = "/humidity";
  postData = "{\"amount\":";
  postData += humidity;
  postData += "}";
  // Serial.print(humidity);

  
  // Enviar la petición al servicio correspondiente
  client.post(path, contentType, postData);
  
  // Obtener el código de estado de respuesta
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  // Serial.println("Respuesta desde el servidor");
  // Serial.println(response);

  // Establecer la dirección del servicio
  path = "/temperature";
  postData = "{\"amount\":";
  postData += temperature;
  postData += "}";
  // Serial.print(temperature);

  
  // Enviar la petición al servicio correspondiente
  client.post(path, contentType, postData);
  
  // Obtener el código de estado de respuesta
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  // Serial.println("Respuesta desde el servidor");
  // Serial.println(response);

  // Serial.println("-----------------------");

  
}

void loop()
{   
  calibra(7);
}
