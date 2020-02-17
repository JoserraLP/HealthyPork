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
const char serverAddress[] = "192.168.0.110";  // server address
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

void calibration(int nsegs)
{
  int tcalibra = 100 * nsegs; // 1000 = 1 second
  long tsini; // Start time Arduino
  int vact, vmax=0, vmin=1023; // Actual, maximum and minimum voltage
  
  tsini = millis(); // time in ms since the arduino is started

  ////////Luminosity////////

  // Get the higher value of the light
  //Serial.println("Calibrating LDR sensor...");
  while (millis()-tsini < tcalibra) {
    vact = analogRead(pinLDR);
    if(vact>vmax) vmax=vact;
    if(vact<vmin) vmin=vact;
  }

  //Serial.print(vmax);

  // Stablish the service direction
  String path = "/luminosity";
  String contentType = "application/json";
  String postData = "{\"amount\":";
  postData += vmax;
  postData += "}";
  
  
  // Send the request to the service
  client.post(path, contentType, postData);
  
  // Get the response status
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  //Serial.println("Response from the server");
  //Serial.println(response);

  delay(1000);
  
  
  ////////Temperature/Humidity////////

  // Serial.println("Calibrating DHT sensor...");
  float humidity = dht.readHumidity();
  float temperature = dht.readTemperature();

  if (isnan(humidity) || isnan(temperature)) {
    // Serial.println("Failed when reading DHT sensor!!");
    return;
  }

  // Stablish the service direction
  path = "/humidity";
  postData = "{\"amount\":";
  postData += humidity;
  postData += "}";
  // Serial.print(humidity);

  
  // Send the request to the service
  client.post(path, contentType, postData);
  
  // Get the response status
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  // Serial.println("Response from the server");
  // Serial.println(response);

  // Stablish the service direction
  path = "/temperature";
  postData = "{\"amount\":";
  postData += temperature;
  postData += "}";
  // Serial.print(temperature);

  
  // Send the request to the service 
  client.post(path, contentType, postData);
  
  // Get the response status
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  // Serial.println("Response from the server");
  // Serial.println(response);

  // Serial.println("-----------------------");
  delay(1000);
  
}

void loop()
{   
  calibration(7);
}
