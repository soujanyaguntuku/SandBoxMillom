package millom.sandbox.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.Utility.JsonUtility;
import millom.sandbox.pojos.Weather;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NasaMapperTest {
  private final static NasaMapper nasaMapper = new NasaMapper();

  @Test
  @DisplayName("TEST JSON STRING MAPPING CORRECT TO THE WEATHER OBJECT")
  void deserializeWeather() throws InvalidWeatherException {
    Weather response = nasaMapper.deserializeWeather(JsonUtility.SAMPLE_RESPONSE);
    assertThat(response).isNotNull();
    assertThat(response.getSoles()).hasSize(2);
    assertThat(response.getDescriptions().getLsDescEn()).
        isEqualTo("\n\t\t\t\tA Martian year lasts about two Earth's year, which is the time\n\t\t\t\tMars takes to orbit the Sun. Solar longitude is an angle that\n\t\t\t\tgives the position of Mars on its orbit.\t\t\t\t  \n\t\t\t");
  }

  @Test
   void deserializeWeatherStringAsEmpty() {
    assertThatThrownBy(()->nasaMapper.deserializeWeather("")).isInstanceOf(InvalidWeatherException.class)
        .hasMessageStartingWith("Error while mapping the Json");
  }

  @Test
  @DisplayName("TEST JSON STRING AS NULL")
   void deserializeWeatherStringAsNull() {
    assertThatThrownBy(()->nasaMapper.deserializeWeather(null)).isInstanceOf(
            InvalidWeatherException.class)
        .hasMessageStartingWith("Content is null");
  }

  @Test
  @DisplayName("TEST FOR JSON PROPERTY NAME MISMATCH")
  void deserializeWeatherPropertyNameMisMatch() throws InvalidWeatherException {
    Weather response = nasaMapper.deserializeWeather(JsonUtility.RESPONSE_PROPERTY_MISMATCH);
    assertThat(response).extracting("descriptions").isNull();
    assertThat(response).extracting("soles").isNotNull();
  }

  @Test
  @DisplayName("TEST FOR JSON PROPERTY MISSING")
   void deserializeWeatherPropertyMissing() throws InvalidWeatherException {
    Weather response = nasaMapper.deserializeWeather(JsonUtility.RESPONSE_MISSING_JSON_PROPERTY);
    assertThat(response).extracting("descriptions").isNull();
    assertThat(response).extracting("soles").isNotNull();
  }
}