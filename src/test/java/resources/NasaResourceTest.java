package resources;

import static millom.sandbox.Utility.WeatherDataUtility.SOL1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.core.Response;
import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.Utility.WeatherDataUtility;
import millom.sandbox.pojos.Weather;
import millom.sandbox.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NasaResourceTest {

  @Mock
  private WeatherService weatherServiceMock;
  @InjectMocks
  private NasaResource nasaResource;

  @BeforeEach
  private void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("GET ALL MARS WEATHER")
  void getMarsWeather() throws InvalidWeatherException {
    when(weatherServiceMock.getWeatherMapping("weather", "json", 1.0f, "msl"))
        .thenReturn(WeatherDataUtility.WEATHER_DATA);
    Response response = nasaResource.getMarsWeather("weather", "json", 1.0f, "msl");
    verify(weatherServiceMock).getWeatherMapping("weather", "json", 1.0f, "msl");
    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isEqualTo(WeatherDataUtility.WEATHER_DATA);
    assertThat(((Weather) response.getEntity()).getSoles()).hasSize(2);
  }

  @Test
  @DisplayName("GET ALL MARS WEATHER THROWS INVALID JSON DATA EXCEPTION")
  void getMarsWeatherThrowsException() throws InvalidWeatherException {
    when(weatherServiceMock.getWeatherMapping("weather", null, 1.0f, "msl"))
        .thenThrow(new InvalidWeatherException("invalid json data"));
    Response response = nasaResource.getMarsWeather("weather", null, 1.0f, "msl");
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(response.getEntity()).isNotNull();
  }

  @Test
  @DisplayName("GET MARS WEATHER FOR A GIVEN DATE")
  void getMarsWeatherForEarthDate() throws InvalidWeatherException {
    when(weatherServiceMock.getMarsWeatherForDate("weather", "json", 1.0f, "msl", "2022-07-01"))
        .thenReturn(SOL1);
    Response response = nasaResource.getMarsWeatherForEarthDate("weather", "json", 1.0f, "msl",
        "2022-07-01");
    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isNotNull();
    assertThat(response.getEntity()).extracting("season").isEqualTo("Month 9");
    assertThat(response.getEntity()).extracting("ls").isEqualTo("257");
    assertThat(response.getEntity()).extracting("minGtsTemp").isEqualTo("-85");
  }

  @Test
  @DisplayName("GET MARS WEATHER FOR A GIVEN DATE, INPUT DATE IN NOT PRESENT IN THE MARS WEATHER LIST")
  void getMarsWeatherForEarthDateNotPresent() throws InvalidWeatherException {
    String date = "2011-07-01";
    when(weatherServiceMock.getMarsWeatherForDate("weather", "json", 1.0f, "msl", date))
        .thenReturn(null);
    Response response = nasaResource.getMarsWeatherForEarthDate("weather", "json", 1.0f, "msl",
        date);
    assertThat(response.getStatus()).isEqualTo(404);
    assertThat(response.getEntity()).
        isEqualTo("The given date doesn't have any information in the mars weather report.");
  }

  @Test
  @DisplayName("GET MARS WEATHER FOR A GIVEN DATE AND FEED MISMATCH")
  void getMarsWeatherForEarthDateInvalidFeed() {
    Response response = nasaResource.getMarsWeatherForEarthDate("weathr", "json", 1.0f, "msl",
        "2022-02-01");
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(response.getEntity()).isEqualTo("The input values are not valid for feed.");
  }

  @Test
  @DisplayName("GET MARS WEATHER FOR A GIVEN DATE AND INVALID DATE FORMAT")
  void getMarsWeatherForInvalidDateFormat() {
    Response response = nasaResource.getMarsWeatherForEarthDate("weathEr", "json", 1.0f, "msl",
        "2022-02-1");
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(response.getEntity()).isEqualTo("The input values are not valid for feed.");
  }

}