package millom.sandbox.service;

import jakarta.inject.Inject;
import java.util.Optional;
import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.mapper.NasaMapper;
import millom.sandbox.pojos.Sol;
import millom.sandbox.pojos.Weather;

public class WeatherService {

  @Inject
  private MyClientService myClientService;
  @Inject
  private NasaMapper nasaMapper;

  public Weather getWeatherMapping(String feed, String feedType, float version, String category)
      throws InvalidWeatherException {
    String marsWeatherAsString = myClientService.getJsonWeather(feed, feedType, version, category);
    return nasaMapper.deserializeWeather(marsWeatherAsString);
  }

  public Sol getMarsWeatherForDate(String feed, String feedType, float version, String category,
      String date)
      throws InvalidWeatherException {

    Weather fullMarsWeather = getWeatherMapping(feed, feedType, version, category);

    Optional<Sol> sol = fullMarsWeather.getSoles().stream()
        .filter((s) -> s.getTerrestrialDate().equals(date))
        .findFirst();
    return sol.orElse(null);
  }

}
