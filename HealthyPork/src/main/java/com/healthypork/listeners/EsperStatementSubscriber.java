package com.healthypork.listeners;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.healthypork.utils.Constants;

public class EsperStatementSubscriber{
	
    private String topic = "observations";
    private MqttClient client;
    private int qos = 1;
	
	public EsperStatementSubscriber() throws MqttException {	
		String host = String.format(Constants.MQTT_URL);
        //String[] auth = this.getAuth(uri);
        String username = "root";
        String password = "root";
        String clientId = "MQTT-HP-Esper";


        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.connect(conOpt);
	}

	public void update(String obsName) throws MqttPersistenceException, MqttException {	
		System.out.println(obsName);
		MqttMessage message = new MqttMessage(obsName.getBytes());
		message.setQos(qos);
		this.client.publish(this.topic, message); // Blocking publish
	}
}
