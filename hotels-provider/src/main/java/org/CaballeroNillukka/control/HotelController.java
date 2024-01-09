package org.CaballeroNillukka.control;

import org.CaballeroNillukka.model.Hotel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HotelController {
	private final XoteloHotelProvider hotelProvider;
	private final HotelPublisher hotelPublisher;
	public HotelController(XoteloHotelProvider hotelProvider, HotelPublisher hotelPublisher) {
		this.hotelProvider = hotelProvider;
		this.hotelPublisher = hotelPublisher;
	}
	public void execute(List<Hotel> hotelsList){
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				for (Hotel hotel : hotelsList){
					hotelPublisher.publishBookingData(hotelProvider.getBooking(hotel));
				}
			}
		};
		long periodicity = 6 * 60 * 60 * 1000;
		timer.scheduleAtFixedRate(task, 0, periodicity);

	}
}
