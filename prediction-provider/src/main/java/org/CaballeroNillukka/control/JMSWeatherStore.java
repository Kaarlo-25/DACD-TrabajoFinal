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
			Connection connection = (Connection) connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic(subject);
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage(event);
			producer.send(message);
			//connection.close();
		} catch (JMSException e) {
			System.out.println("ERROR: "+e);
			throw new RuntimeException(e);
		} catch (javax.jms.JMSException e) {
			throw new RuntimeException(e);
		}
	}

}
