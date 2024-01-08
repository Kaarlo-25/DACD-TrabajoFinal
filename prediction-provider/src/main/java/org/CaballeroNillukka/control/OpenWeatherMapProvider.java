package org.CaballeroNillukka.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.CaballeroNillukka.model.Location;
import org.CaballeroNillukka.model.Weather;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class OpenWeatherMapProvider implements WeatherProvider {
	//Constructor
	private final String apiKey;
	public OpenWeatherMapProvider(String apiKey) {
		this.apiKey = apiKey;
	}

	//Methods
	@Override
	public List<Weather> getWeatherData(Location location) {
		String templateURL = "https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&cnt=40&appid=%s";
		String requestString = String.format(templateURL, location.getLatitude(), location.getLongitude(), apiKey);
		try {
			String jsonResponse = Jsoup.connect(requestString).ignoreContentType(true).execute().body();
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
			JsonArray listArray = jsonObject.getAsJsonArray("list");
			List<Weather> predictionFiveDays = new ArrayList<>();
			for (int i = 0; i < listArray.size(); i++) {
				JsonObject data = listArray.get(i).getAsJsonObject();
				if (isPrediction(data)) {
					Weather weather = fromJson2Weather(data, location);
					predictionFiveDays.add(weather);
					if (predictionFiveDays.size() == 5) {
						break;
					}
				}
			}
			return predictionFiveDays;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private boolean isPrediction(JsonObject data) {
		int predictionDateTime = data.get("dt").getAsInt();
		Instant instant = Instant.ofEpochSecond(predictionDateTime);
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
		LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
		return localDateTime.getHour() == 12 && localDateTime.getMinute() == 0;
	}
	private Weather fromJson2Weather(JsonObject data, Location location) {
		JsonObject mainData = data.getAsJsonObject("main");
		JsonObject rainData = data.getAsJsonObject("rain");
		JsonObject cloudsData = data.getAsJsonObject("clouds");
		JsonObject windData = data.getAsJsonObject("wind");

		float temperature = (float) mainData.get("temp").getAsDouble();
		int humidity = mainData.get("humidity").getAsInt();
		float rain = (float) ((rainData != null && rainData.has("3h")) ? rainData.get("3h").getAsDouble() : 0.0);
		int clouds = cloudsData.get("all").getAsInt();
		float windSpeed = (float) windData.get("speed").getAsDouble();
		String predictionTime = String.valueOf(data.get("dt_txt")).replace("\"", "");
		Instant instant = Instant.now();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String eventTime = formatter.format(localDateTime);
		return new Weather(temperature, humidity, rain, windSpeed, clouds, location, predictionTime, eventTime);

	}
}