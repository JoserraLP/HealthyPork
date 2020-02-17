#include <SPI.h>
#include <WiFi101.h>
#include <ArduinoHttpClient.h>

const int pinMQ = A3;
const int pinNoise = A1;

/////// Wifi Settings ///////
char ssid[] = "Gloin";
char pass[] = "Gloin2014";
const char serverAddress[] = "192.168.0.110"; // server address
int port = 8080;
WiFiClient wifi;
HttpClient client = HttpClient(wifi, serverAddress, port);
int status;
int statusCode = 0;
String response;

void setup()
{
  
  // Serial.begin(9600);
  
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to Network named: ");
    Serial.println(ssid);             
    // Connect to WPA/WPA2 network:
    status = WiFi.begin(ssid, pass);
  }
  
} 

void calibration(int nsegs)
{
  ////////Air quality////////

  int airQuality = analogRead(0);       // Read from analog 0
    
  // Serial.println("Calibrating MQ135 sensor...");

  if (isnan(airQuality)) {
    // Serial.println("Failed reading value from MQ135 sensor!");
    return;
  }

  /*
  Serial.print("AirQua=");
  Serial.print(airQuality, DEC);          
  Serial.println(" PPM");  
  */
  
  // Stablish the service direction
  String path = "/airquality";
  String contentType = "application/json";
  String postData = "{\"amount\":";
  postData += airQuality;
  postData += "}";
  
  // Send the request to the service
  client.post(path, contentType, postData);
  
  // Get the response status
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  //Serial.println("Response from the server");
  // Serial.println(response);

  delay(1000);

  ////////Noise////////

  // Serial.println("Calibrating noise sensor...");

  int noise = analogRead (pinNoise) ; // Read from analog pin

  if (isnan(noise)) {
    //Serial.println("Failed reading value from noise sensor!");
    return;
  }

  /*
  Serial.print("Sound=");
  Serial.print(noise, DEC);   
  Serial.println("");       
  */
  
  // Stablish the service direction
  path = "/noise";
  postData = "{\"amount\":";
  postData += noise;
  postData += "}";
  
  // Send the request to the service 
  client.post(path, contentType, postData);
  
  // Get the response status
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  // Serial.println("Response from the server");
  // Serial.println(response);

  delay(1000);
}

void loop()
{   
  calibration(7);
}
