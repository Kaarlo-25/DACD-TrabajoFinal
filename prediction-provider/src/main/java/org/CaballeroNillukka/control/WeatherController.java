package org.CaballeroNillukka.control;

import org.CaballeroNillukka.model.Location;
import org.CaballeroNillukka.model.Weather;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController {
	//Constructor
	private final List<Location> locationsList;
	private final WeatherProvider weatherProvider;
	private final EventPublisher weatherStore;
	public WeatherController(List<Location> locationsList, WeatherProvider weatherProvider, EventPublisher weatherStore) {
		this.locationsList = locationsList;
		this.weatherProvider = weatherProvider;
		this.weatherStore = weatherStore;
	}

	//Methods
	public void execute() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < 8; i++) {
					List<Weather> weathers = weatherProvider.getWeatherData(locationsList.get(i));
					System.out.printf("+ %s: \n\t- Success obtaining the data.\n", locationsList.get(i).getName());
					for (Weather weather : weathers) {
						weatherStore.publishWeatherData(weather);
					}
					System.out.println("\t- Success sending the data message.");
				}
			}
		};
		long periodicity = 6 * 60 * 60 * 1000;
		timer.scheduleAtFixedRate(task, 0, periodicity);
	}
}

