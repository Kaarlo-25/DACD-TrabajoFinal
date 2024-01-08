package control;

public class Main {
	public static String brokerURL = "tcp://localhost:61616";
	public static String brokerSubject = "prediction.Weather";
	public static String eventsPath = "events-store/prediction.Weather/%s/%s.events";
	public static String clientID;
	public static void main(String[] args){
		clientID = "anonymous"; //args[0];
		JMSReceiver eventsSuscriptor = new JMSReceiver(brokerURL, brokerSubject, clientID);
		FileStorer eventsStorer = new FileStorer(eventsPath);
		eventsSuscriptor.startListening(eventsStorer);
	}
}
