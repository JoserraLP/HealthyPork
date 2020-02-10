package com.healthypork.main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.espertech.esper.client.deploy.DeploymentException;
import com.espertech.esper.client.deploy.ParseException;
import com.healthypork.subscribers.Subscriber;
import com.healthypork.utils.PatternDetector;

public class HealthyPorkMain {

	public static void main(String[] args) throws IOException, ParseException, DeploymentException,
	InterruptedException, ClassNotFoundException {
		HealthyPorkMain test = new HealthyPorkMain();
		PatternDetector pd = new PatternDetector("resources/Statements.epl");
		pd.start();
		try {
			Subscriber subs = new Subscriber(new URI("tcp://localhost:1883"));
		} catch (MqttException e) {

			e.printStackTrace();
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}
		synchronized (test) {
			test.wait();
		}
	}
}