package millom.sandbox.service;

import jakarta.inject.Inject;
import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.mapper.NasaMapper;
import millom.sandbox.pojos.Weather;

public class WeatherService {
  @Inject
  private MyClientService myClientService;
  @Inject
  private NasaMapper nasaMapper ;

  public Weather getWeatherMapping(String feed, String feedType, float version, String category)
      throws InvalidWeatherException {
    String marsWeatherAsString = myClientService.getJsonWeather(feed, feedType, version, category);
    return nasaMapper.deserializeWeather(marsWeatherAsString);
  }
}
