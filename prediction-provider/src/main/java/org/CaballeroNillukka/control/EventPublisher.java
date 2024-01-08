package org.CaballeroNillukka.control;

import org.CaballeroNillukka.model.Weather;

public interface EventPublisher {
	void publishWeatherData(Weather weather);
}
