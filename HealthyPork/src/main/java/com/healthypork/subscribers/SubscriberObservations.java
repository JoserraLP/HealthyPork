package com.healthypork.subscribers;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProviderManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client
 * blocking API.
 */
public class SubscriberObservations implements MqttCallback {

	private final int qos = 1;
	private String topic = "observations";
	private String topic_filtered = "observations_filtered";
	private MqttClient client;
	@SuppressWarnings("unused")
	private EPRuntime runtimeEngine;
	private HashMap<String, Boolean> status = new HashMap<String, Boolean>();

	public SubscriberObservations(String uri) throws MqttException, URISyntaxException {
		this(new URI(uri));

	}

	public SubscriberObservations(URI uri) throws MqttException {
		String host = String.format("tcp://%s:%d", uri.getHost(), uri.getPort());
		// String[] auth = this.getAuth(uri);
		String username = "root";
		String password = "root";
		String clientId = "MQTT-HP-Observations";
		runtimeEngine = EPServiceProviderManager.getDefaultProvider().getEPRuntime();
		if (!uri.getPath().isEmpty()) {
			this.topic = uri.getPath().substring(1);
		}

		MqttConnectOptions conOpt = new MqttConnectOptions();
		conOpt.setCleanSession(true);
		conOpt.setUserName(username);
		conOpt.setPassword(password.toCharArray());

		this.client = new MqttClient(host, clientId, new MemoryPersistence());
		this.client.setCallback(this);
		this.client.connect(conOpt);

		this.client.subscribe(this.topic, qos);
		
		this.status.put("light", false);
		this.status.put("fan", false);
		this.status.put("sprinkler", false);
		this.status.put("windows", false);
		this.status.put("heater", false);
		
	}

	@SuppressWarnings("unused")
	private String[] getAuth(URI uri) {
		String a = uri.getAuthority();
		String[] first = a.split("@");
		return first[0].split(":");
	}

	public void sendMessage(String topic, String payload) throws MqttException {
		MqttMessage message = new MqttMessage(payload.getBytes());
		message.setQos(qos);
		this.client.publish(topic, message); // Blocking publish
	}

	/**
	 * @see MqttCallback#connectionLost(Throwable)
	 */
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost because: " + cause);
		System.exit(1);
	}

	/**
	 * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	/**
	 * @see MqttCallback#messageArrived(String, MqttMessage)
	 */
	public void messageArrived(String topic, MqttMessage message) throws MqttException {
		String messageReceived = new String(message.getPayload());
		switch (messageReceived) {
		case "turnOnLight":
			if (!this.status.get("light")) {
				this.sendMessage(topic_filtered, "turnOnLight");
				this.status.put("light", true);
			}
			break;
		case "turnOffLight":
			if (status.get("light")) {
				this.sendMessage(topic_filtered, "turnOffLight");
				status.put("light", false);
			}
			break;
		case "turnOnFan":
			if (!this.status.get("fan")) {
				this.sendMessage(topic_filtered, "turnOnFan");
				this.status.put("fan", true);
			}
			break;
		case "turnOffFan":
			if (this.status.get("fan")) {
				this.sendMessage(topic_filtered, "turnOffFan");
				this.status.put("fan", false);
			}
			break;
		case "turnOnSprinkler":
			if (!this.status.get("sprinkler")) {
				this.sendMessage(topic_filtered, "turnOnSprinkler");
				this.status.put("sprinkler", true);
			}
			break;
		case "turnOffSprinkler":
			if (this.status.get("sprinkler")) {
				this.sendMessage(topic_filtered, "turnOffSprinkler");
				this.status.put("sprinkler", false);
			}
			break;
		case "openWindows":
			if (!this.status.get("windows")) {
				this.sendMessage(topic_filtered, "openWindows");
				this.status.put("windows", true);
			}
			break;
		case "closeWindows":
			if (this.status.get("windows")) {
				this.sendMessage(topic_filtered, "closeWindows");
				this.status.put("windows", false);
			}
			break;
		case "turnOnHeating":
			if (!this.status.get("heater")) {
				this.sendMessage(topic_filtered, "turnOnSprinkler");
				this.status.put("heater", true);
			}
			break;
		case "turnOffHeating":
			if (this.status.get("heater")) {
				this.sendMessage(topic_filtered, "turnOffSprinkler");
				this.status.put("heater", false);
			}
			break;
		default:
			break;
		}

	}

}