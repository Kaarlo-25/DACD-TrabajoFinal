package org.CaballeroNillukka.control;

import jakarta.jms.*;
import org.CaballeroNillukka.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.google.gson.Gson;


public class JMSWeatherStore implements EventPublisher {
	//Constructor
	private final String brokerURL;
	private final String subject;
	public JMSWeatherStore(String brokerURL, String subject) {
		this.brokerURL = brokerURL;
		this.subject = subject;
	}

	//Methods
	@Override
	public void publishWeatherData(Weather weather) {
		Gson gson = new Gson();
		String event = gson.toJson(weather);
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			javax.jms.Connection connection = connectionFactory.createConnection();
			connection.start();
			javax.jms.Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			javax.jms.Topic destination = session.createTopic(subject);
			javax.jms.MessageProducer producer = session.createProducer(destination);
			javax.jms.TextMessage message = session.createTextMessage(event);
			producer.send(message);
			session.close();
			connection.close();
		} catch (javax.jms.JMSException e) {
			throw new RuntimeException(e);
		}
	}

}
