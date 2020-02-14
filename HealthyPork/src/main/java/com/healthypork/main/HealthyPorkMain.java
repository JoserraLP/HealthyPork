package com.healthypork.main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.espertech.esper.client.EPSubscriberException;
import com.espertech.esper.client.deploy.DeploymentException;
import com.espertech.esper.client.deploy.ParseException;
import com.healthypork.subscribers.SubscriberObservations;
import com.healthypork.subscribers.SubscriberAirQuality;
import com.healthypork.utils.PatternDetector;

public class HealthyPorkMain {

	public static void main(String[] args) throws IOException, ParseException, DeploymentException,
	InterruptedException, ClassNotFoundException, EPSubscriberException, MqttException {
		HealthyPorkMain test = new HealthyPorkMain();
		
		PatternDetector pd = new PatternDetector("resources/Statements.epl");
		pd.start();
		try {
			SubscriberAirQuality subs = new SubscriberAirQuality(new URI("tcp://localhost:1883"));
			SubscriberObservations subs_1 = new SubscriberObservations(new URI("tcp://localhost:1883"));
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