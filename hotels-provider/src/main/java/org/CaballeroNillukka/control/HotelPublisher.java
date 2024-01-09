package org.CaballeroNillukka.control;

import com.google.gson.Gson;
import org.CaballeroNillukka.model.Booking;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class HotelPublisher implements HotelPublisherInterface{
	private final String brokerURL;
	private final String subject;
	public HotelPublisher(String brokerURL, String subject) {
		this.brokerURL = brokerURL;
		this.subject = subject;
	}

	@Override
	public void publishBookingData(Booking booking) {
		Gson gson = new Gson();
		String event = gson.toJson(booking);
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue destination = session.createQueue(subject);
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage(event);
			producer.send(message);
			session.close();
			connection.close();
			System.out.println("\t- Success sending message to ActiveMQ.\n");
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}
