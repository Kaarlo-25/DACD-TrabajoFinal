package org.CaballeroNillukka.model;

public class Weather {

	//Constructure
	private final float temperature;
	private final int humidity;
	private final float rain;
	private final float windSpeed;
	private final float clouds;
	private final Location location;
	private final String predictionTime;
	private final String dataSource;
	private final String eventTime;
	public Weather(float temperature, int humidity, float rain, float windSpeed, float clouds, Location location, String predictionTime, String eventTime) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.rain = rain;
		this.windSpeed = windSpeed;
		this.clouds = clouds;
		this.location = location;
		this.predictionTime = predictionTime;
		this.eventTime = eventTime;
		this.dataSource = "Prediction-provider";
	}

	//Methods
	@Override
	public String toString() {
		return "Weather{" +
				"temperature=" + temperature +
				", hummidity=" + humidity +
				", rain=" + rain +
				", windSpeed=" + windSpeed +
				", clouds=" + clouds +
				", location=" + location +
				", predictionTime=" + predictionTime +
				", eventTime=" + eventTime +
				", dataSource='" + dataSource + '\'' +
				'}';
	}
}
