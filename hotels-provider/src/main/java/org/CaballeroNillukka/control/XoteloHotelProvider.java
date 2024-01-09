package org.CaballeroNillukka.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.CaballeroNillukka.model.Booking;
import org.CaballeroNillukka.model.Hotel;
import org.CaballeroNillukka.model.Rate;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XoteloHotelProvider implements HotelProvider {
	@Override
	public Booking getBooking(Hotel hotel) {
		try {
			for (int i = 1; i <= 5; i++) {
				String requestURL = createRequestURL(hotel, i);
				String jsonResponse = Jsoup.connect(requestURL).ignoreContentType(true).execute().body();
				Gson gson = new Gson();
				JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
				JsonObject resultList = (JsonObject) jsonObject.get("result");
				JsonArray ratesList = resultList.getAsJsonArray("rates");
				List<Rate> ratesAvailable = new ArrayList<>();
				for (int j = 0; j < ratesList.size(); j++) {
					JsonObject rate = ratesList.get(j).getAsJsonObject();
					ratesAvailable.add(fromJson2Rate(rate));
				}
				String checkin = String.valueOf(resultList.get("chk_in")).replaceAll("\"", "");
				String checkout = String.valueOf(resultList.get("chk_out")).replaceAll("\"", "");
				Instant instant = Instant.now();
				LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String timestamp = formatter.format(localDateTime);
				Booking booking = new Booking(hotel, checkin, checkout, ratesAvailable, timestamp);
				System.out.println("\t- Success getting bookings data.\n");
				return booking;
			}
			return null;
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	private String createRequestURL(Hotel hotel, int i) {
		if (LocalTime.now().isAfter(LocalTime.of(17, 0, 0))){
			i+=1;
		}
		String templateURL = "https://data.xotelo.com/api/rates?hotel_key=%s&currency=EUR&chk_in=%s&chk_out=%s";
		LocalDate localDate = Instant.now().plus(Duration.ofDays(i-1)).atZone(java.time.ZoneOffset.UTC).toLocalDate();
		String checkin = localDate.format(DateTimeFormatter.ISO_DATE);
		localDate = Instant.now().plus(Duration.ofDays(i)).atZone(java.time.ZoneOffset.UTC).toLocalDate();
		String checkout = localDate.format(DateTimeFormatter.ISO_DATE);
		return String.format(templateURL, hotel.getHotelKey(), checkin, checkout);
	}

	private Rate fromJson2Rate(JsonObject rate){
		String code = String.valueOf(rate.get("code"));
		String webpage = String.valueOf(rate.get("name"));
		float price = Float.parseFloat(String.valueOf(rate.get("rate")));
		float tax = Float.parseFloat(String.valueOf(rate.get("tax")));
		return new Rate(code, webpage, price, tax);
	}
}
