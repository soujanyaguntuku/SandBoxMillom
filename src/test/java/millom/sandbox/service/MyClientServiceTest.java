package millom.sandbox.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import millom.sandbox.mapper.NasaMapper;
import millom.sandbox.mapper.NasaMapperTest;
import org.glassfish.jersey.client.JerseyInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class MyClientServiceTest {
  private JerseyInvocation.Builder builder;
  NasaMapper nasaMapper;
  @Mock
  private Client client;
  @InjectMocks
  private MyClientService service = new MyClientService(client);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    nasaMapper = new NasaMapper();
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
   void getJsonWeather() {
    when(builder.get(String.class)).thenReturn(NasaMapperTest.SAMPLE_RESPONSE);
    String weatherAsString = service.getJsonWeather("weather","json",1.0f,"msl");
    assertThat(weatherAsString).isNotNull();
    //check returning string is in json format.
    assertThat(nasaMapper.isValidJson(weatherAsString)).isTrue();
    assertThat(weatherAsString).contains("descriptions");
    assertThat(weatherAsString).contains("soles");
  }

  @Test
  void getJsonWeatherIncorrectURI() {
    when(builder.get(String.class)).thenReturn("{\"descriptions\":null,\"soles\":null}");
    String weatherAsString = service.getJsonWeather("weather","json",1.0f,"");
    assertThat(weatherAsString).isNotNull().isEqualTo("{\"descriptions\":null,\"soles\":null}");
    assertThat(weatherAsString).isNotEqualTo(NasaMapperTest.SAMPLE_RESPONSE);
  }
}