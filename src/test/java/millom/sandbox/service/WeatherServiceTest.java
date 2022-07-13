package millom.sandbox.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import millom.sandbox.mapper.NasaMapper;
import millom.sandbox.mapper.NasaMapperTest;
import millom.sandbox.pojos.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import resources.NasaResourceTest;

@RunWith(MockitoJUnitRunner.class)
class WeatherServiceTest {
  @Mock
  private MyClientService myClientService;
  @Mock
  private NasaMapper nasaMapper;
  @InjectMocks
  private WeatherService nasaResource = new WeatherService(myClientService,nasaMapper);


  //public static final Weather WEATHER_EMPTY_DATA =

  @BeforeEach
  private void setUp() throws Exception{
    MockitoAnnotations.initMocks(this);
  }

  @Test
   void getWeatherMapping() {
    when(myClientService.getJsonWeather("weather","json",1.0f,"msl")).thenReturn(NasaMapperTest.SAMPLE_RESPONSE);
   when(nasaMapper.deserializeWeather(NasaMapperTest.SAMPLE_RESPONSE)).thenReturn(
       NasaResourceTest.WEATHER_DATA);
    Weather response = nasaResource.getWeatherMapping("weather","json",1.0f,"msl");
    assertThat(response).isNotNull();
    assertThat(response.getSoles()).hasSize(2);
    assertThat(response.getDescriptions().getLsDescEn()).
        isEqualTo("\n\t\t\t\tA Martian year lasts about two Earth's year, which is the time\n\t\t\t\tMars takes to orbit the Sun. Solar longitude is an angle that\n\t\t\t\tgives the position of Mars on its orbit.\t\t\t\t  \n\t\t\t");
  }
}