var options = {
    clientId: 'mqtthp'
}

var mqtt = require('mqtt');
var client = mqtt.connect('mqtt://localhost:1883', options);

var options={
    retain:true,
    qos:1};

/**
 * MQTT
 */
client.on('connect', function(){
    if (client.connected == true){
        console.log('Succesfull connected to MQTT');
            setInterval(() => {
                let number = (Math.random()*400);
                client.publish('airquality', (number).toString(), 1);
                number = (Math.random()*60);
                client.publish('noise', (number).toString(), 1);
                number = (Math.random()*50);
                client.publish('luminosity', (number).toString(), 1);
                number = (Math.random()*100);
                client.publish('humidity', (number).toString(), 1);
                number = (Math.random()*60);
                client.publish('temperature', (number).toString(), 1);
                number = (Math.random()*60);
                client.publish('weather_temp', (number).toString(), 1);
                number = (Math.random()*60);
                client.publish('weather_temp_min', (number).toString(), 1);
                number = (Math.random()*60);
                client.publish('weather_temp_max', (number).toString(), 1);
                number = (Math.random()*60);
                client.publish('weather_temp_feels', (number).toString(), 1);
                number = (Math.random()*100);
                client.publish('weather_humidity', (number).toString(), 1);
            }, 250);
    
    }
});

client.on('error', function(error){
    console.log('Error, cannot connect to MQTT ' + error);
});
