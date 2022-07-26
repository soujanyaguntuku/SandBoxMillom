package millom.sandbox.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import java.io.IOException;
import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.Utility.JsonUtility;
import org.glassfish.jersey.client.JerseyInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MyClientServiceTest {
  private JerseyInvocation.Builder builder;
  @Mock
  private Client client;
  @InjectMocks
  private MyClientService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    WebTarget target = Mockito.mock(WebTarget.class);
    builder = Mockito.mock(JerseyInvocation.Builder.class);
    when(target.request(anyString())).thenReturn(builder);
    when(client.target(anyString())).thenReturn(target);
  }

  @Test
   void getApiQueryParams() {
    assertThat(service.getApiQueryParams("weather", "json", 1.0f, "msl")).
        isNotNull();
    assertThat(service.getApiQueryParams("weather", "json", 1.0f, "msl")).
        isEqualTo("?feed=weather&feedtype=json&ver=1.0&category=msl");
  }

  @Test
   void getApiQueryParamsInvalidString() {
    assertThat(service.getApiQueryParams("weather", "", 1.0f, "msl")).
        isNotEqualTo("?feed=weather&feedtype=json&ver=1.0&category=msl");
  }

  @Test
   void getJsonWeather() throws InvalidWeatherException, IOException {
    when(builder.get(String.class)).thenReturn(JsonUtility.getSampleResponse("response.json"));
    String weatherAsString = service.getJsonWeather("weather","json",1.0f,"msl");
    assertThat(weatherAsString).isNotNull();
    assertThat(JsonUtility.isValidJson(weatherAsString)).isTrue();
    assertThat(weatherAsString).contains("descriptions");
    assertThat(weatherAsString).contains("soles");
  }

  @Test
  void getJsonWeatherIncorrectURI() throws InvalidWeatherException, IOException {
    when(builder.get(String.class)).thenReturn("{\"descriptions\":null,\"soles\":null}");
    String weatherAsString = service.getJsonWeather("weather","json",1.0f,"");
    assertThat(weatherAsString).isNotNull().isEqualTo("{\"descriptions\":null,\"soles\":null}");
    assertThat(weatherAsString).isNotEqualTo(JsonUtility.getSampleResponse("response.json"));
  }
}