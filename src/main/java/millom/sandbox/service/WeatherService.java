package millom.sandbox.service;

import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.mapper.NasaMapper;
import millom.sandbox.pojos.Weather;

public class WeatherService {

  private final MyClientService myClientService;
  private final NasaMapper nasaMapper;

  public WeatherService(MyClientService myClientService, NasaMapper nasaMapper) {
    this.myClientService = myClientService;
    this.nasaMapper = nasaMapper;
  }

  public WeatherService() {
    this(new MyClientService(), new NasaMapper());
  }

  public Weather getWeatherMapping(String feed, String feedType, float version, String category)
      throws InvalidWeatherException {
    String marsWeatherAsString = myClientService.getJsonWeather(feed, feedType, version, category);
    return nasaMapper.deserializeWeather(marsWeatherAsString);
  }
}
