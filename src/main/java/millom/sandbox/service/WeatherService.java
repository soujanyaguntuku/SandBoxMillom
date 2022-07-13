package millom.sandbox.service;

import millom.sandbox.mapper.NasaMapper;
import millom.sandbox.pojos.Weather;

public class WeatherService {
  MyClientService myClientService = new MyClientService();
  NasaMapper nasaMapper = new NasaMapper();

  public WeatherService(MyClientService myClientService, NasaMapper nasaMapper) {
    this.myClientService = myClientService;
    this.nasaMapper = nasaMapper;
  }
  public WeatherService(){
  }

  public Weather getWeatherMapping(String feed, String feedType, float version, String category){
    try {
      String marsWeatherAsString = myClientService.getJsonWeather(feed, feedType, version, category);
      return nasaMapper.deserializeWeather(marsWeatherAsString);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
