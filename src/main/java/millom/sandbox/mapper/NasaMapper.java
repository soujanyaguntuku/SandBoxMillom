package millom.sandbox.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.pojos.Weather;

public class NasaMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
  }

  public Weather deserializeWeather(String response) throws InvalidWeatherException {
    try {
      return MAPPER.readValue(response, Weather.class);
    } catch (JsonProcessingException e) {
      throw new InvalidWeatherException("Error while mapping the Json" + e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new InvalidWeatherException("Content is null" + e.getMessage());
    }
  }

}
