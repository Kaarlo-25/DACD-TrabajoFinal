package org.CaballeroNillukka.control;

import org.CaballeroNillukka.model.Hotel;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static List<Hotel> hotelsList = new ArrayList<>();
	public static String brokerURL = "tcp://localhost:61616";
	public static String brokerSubject = "Xotelo.Bookings";

	public static void main(String[] args) {
		hotelsList = loadHotels();
		XoteloHotelProvider hotelProvider = new XoteloHotelProvider();
		HotelPublisher hotelPublisher = new HotelPublisher(brokerURL, brokerSubject);
		HotelController hotelController = new HotelController(hotelProvider, hotelPublisher);
		if (hotelsList != null){
			hotelController.execute(hotelsList);
		} else{
			System.out.println("No hotels available.\n");
		}

	}
	public static List<Hotel> loadHotels(){
		String locationsFile = "hotels.tsv";
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(locationsFile);
		if (inputStream != null) {
			try {
				int charactersRead;
				StringBuilder fileLine = new StringBuilder();
				while ((charactersRead = inputStream.read()) != -1) {
					char character = (char) charactersRead;
					fileLine.append(character);
					if (String.valueOf(character).equals("\n")){
						String hotelName = List.of(fileLine.toString().split("\t")).get(0);
						String island = List.of(fileLine.toString().split("\t")).get(1);
						String hotelKey = List.of(fileLine.toString().split("\t")).get(2).replaceAll("[\n\r\t]", "");
						Hotel hotel = new Hotel(hotelName, island, hotelKey);
						hotelsList.add(hotel);
						fileLine = new StringBuilder();
					}
				}
				inputStream.close();
				System.out.println("Success getting hotels.\n");
				return hotelsList;
			} catch (IOException e) {
				System.out.println(e+"Failure reading Locations.tsv file.\n");
			}
		} else {
			System.out.println("It is not possible to find Locations.tsv file.\n");
		}
		return null;
	}
}