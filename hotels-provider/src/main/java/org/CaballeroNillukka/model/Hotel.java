package org.CaballeroNillukka.model;

public class Hotel {
	//Constructor
	private final String hotelName;
	private final String island;
	private final String hotelKey;
	public Hotel(String hotelName, String island, String hotelKey) {
		this.hotelName = hotelName;
		this.island = island;
		this.hotelKey = hotelKey;
	}
	public String getHotelKey() {
		return hotelKey;
	}

	//Methods
	@Override
	public String toString() {
		return "Hotel{" +
				"hotelName='" + hotelName + '\'' +
				", island='" + island + '\'' +
				", hotelKey='" + hotelKey + '\'' +
				'}';
	}
}
