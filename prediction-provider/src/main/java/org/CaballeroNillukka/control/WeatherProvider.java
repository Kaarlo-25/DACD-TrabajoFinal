package org.CaballeroNillukka.control;

import org.CaballeroNillukka.model.Location;
import org.CaballeroNillukka.model.Weather;
import java.util.List;

public interface WeatherProvider {
	List<Weather> getWeatherData(Location location);

}
