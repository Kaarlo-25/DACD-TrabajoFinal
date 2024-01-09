package control;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSReceiver implements EventSuscriptor {
	// Constructor
	private final String brokerURL;
	private final String subject;
	private final String clientID;
	public JMSReceiver(String url, String subject, String clientID) {
		this.brokerURL = url;
		this.subject = subject;
		this.clientID = clientID;
	}

	public void startListening(EventStorer eventStorer) {
		try {
			MessageConsumer consumer = createSubscriber();
			consumer.setMessageListener(message -> {
				if (message instanceof TextMessage) {
					try {
						eventStorer.storeEvents(((TextMessage) message).getText());
						System.out.println("\n\t- Events received correctly.");
						System.out.println("\n\t- Events stored correctly in Downloads folder.\n");
					} catch (JMSException e) {
						System.err.println("ERROR: " + e.getMessage());
					}
				} else {
					System.out.println("Unknown message type");
				}
			});
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	private MessageConsumer createSubscriber() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection connection = connectionFactory.createConnection();
		connection.setClientID(clientID);
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(subject);
		return session.createDurableConsumer(topic, "Kaarlo");
	}
}
