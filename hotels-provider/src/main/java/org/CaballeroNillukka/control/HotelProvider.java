package org.CaballeroNillukka.control;

import org.CaballeroNillukka.model.Booking;
import org.CaballeroNillukka.model.Hotel;

public interface HotelProvider {
	Booking getBooking(Hotel hotel);
}
