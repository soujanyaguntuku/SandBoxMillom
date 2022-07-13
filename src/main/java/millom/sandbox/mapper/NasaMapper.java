package millom.sandbox.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import millom.sandbox.pojos.Weather;

public class NasaMapper {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  static {
    MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
  }

  public Weather deserializeWeather(String response){
    try {
       return MAPPER.readValue(response,Weather.class);
      //return w;
    } catch (JsonProcessingException e) {
      throw new RuntimeException( "Error while parsing the Json" + e.getMessage());
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Content is null" + e.getMessage());
    }
    catch (Exception e) {
      throw new RuntimeException( e.getMessage());
    }
  }
  public boolean isValidJson(String json) {
    try {
      MAPPER.readTree(json);
    } catch (JacksonException e) {
      return false;
    }
    return true;
  }

}
