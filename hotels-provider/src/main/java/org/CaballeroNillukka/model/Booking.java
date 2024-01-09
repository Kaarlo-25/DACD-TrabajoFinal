package org.CaballeroNillukka.model;

import java.util.List;

public class Booking {
	//Constructor
	private final Hotel hotel;
	private final String checkin;
	private final String checkout;
	private final String timestamp;
	private final List<Rate> rate;
	private final String dataSource;
	public Booking(Hotel hotel, String checkin, String checkout, List<Rate> rate, String timestamp) {
		this.hotel = hotel;
		this.checkin = checkin;
		this.checkout = checkout;
		this.rate = rate;
		this.timestamp = timestamp;
		this.dataSource = "Xotelo";
	}

	//Methods
	@Override
	public String toString() {
		return "Booking{" +
				"hotel=" + hotel +
				", checkin='" + checkin + '\'' +
				", checkout='" + checkout + '\'' +
				", timestamp=" + timestamp +
				", rate=" + rate +
				", dataSource='" + dataSource + '\'' +
				'}';
	}
}
