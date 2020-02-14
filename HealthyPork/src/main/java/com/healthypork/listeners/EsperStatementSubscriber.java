package com.healthypork.listeners;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.espertech.esper.client.EPServiceProviderManager;

public class EsperStatementSubscriber{
	
    private String topic = "observations";
    private MqttClient client;
	
	public EsperStatementSubscriber() throws MqttException {	
		String host = String.format("tcp://localhost:1883");
        //String[] auth = this.getAuth(uri);
        String username = "root";
        String password = "root";
        String clientId = "MQTT-Java-Example";


        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.connect(conOpt);
	}

	public void update(String obsName) throws MqttPersistenceException, MqttException {	
		System.out.println(obsName);
        //MqttMessage message = new MqttMessage(obsName.getBytes());
        //this.client.publish(this.topic, message); // Blocking publish
	}
}
