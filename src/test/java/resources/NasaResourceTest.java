package resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import millom.sandbox.pojos.Descriptions;
import millom.sandbox.pojos.Sol;
import millom.sandbox.pojos.Weather;
import millom.sandbox.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NasaResourceTest {
  @Mock
  private WeatherService weatherServiceMock;
  @InjectMocks
  private NasaResource nasaResource = new NasaResource();

  @BeforeEach
  private void setUp() throws Exception{
    MockitoAnnotations.initMocks(this);
  }
  private static final Descriptions DESCRIPTIONS = new Descriptions.DescriptionsBuilder("\\t    \\n\\t\\t\\tThe information contained into this file is provided by Centro de Astrobiologia\\n\\t\\t\\t(CAB) and is intended for outreach purposes only. Any other use is discouraged.\\n\\t\\t\\tCAB will take no responsibility for any result or publication made base on the \\n\\t\\t\\tcontent of this file. To access REMS scientific data, visit PDS at http://pds.nasa.gov.\\n\\n\\t\\t\\tThe environmental magnitudes given into this file are obtained from the values\\n\\t\\t\\tread by the Rover Environmental Monitoring Station (REMS) on board the Mars Science\\n\\t\\t\\tLaboratory (MSL) rover on Mars. This file provides the environmental magnitudes at REMS\\n\\t\\t\\tlocation, so MSL rover influences those magnitudes (rover position,\\n\\t\\t\\trover temperature, rover orientation, rover shade, dust depositions on the rover, etc.)\\n\\n\\t\\t\\tREMS does not take measurements continuously and it takes measurements at different\\n\\t\\t\\ttimes from one day to another. This fact has influence on the variation of the values\\n\\t\\t\\tgiven in this file from one day to another .\\n\\n\\t\\t\\tFor different reasons (instrument maintenance, instrument calibration, instrument degradation, etc.), \\n\\t\\t\\tsome or all of the magnitudes in this file may not be available.\\n\\t\\t",
      "\\t\\t\\n\\t\\t\\tLa informaci�n contenida en este fichero es facilitada por el Centro de Astrobiolog�a\\n\\t\\t\\t(CAB) �nicamente para fines divulgativos. Cualquier otro uso queda desaconsejado.\\n\\t\\t\\tCAB no se har� responsable de ning�n resultado o publicaci�n basados en el contenido \\n\\t\\t\\tde este fichero. Para acceder a los datos cient�ficos de REMS, visite el PDS en\\n\\t\\t\\thttp://pds.nasa.gov.\\n\\n\\t\\t\\tLas magnitudes ambientales dadas es este fichero se obtienen de los valores le�dos\\n\\t\\t\\tpor la Estaci�n de Monitoreo Ambiental del Rover (REMS, por sus siglas en ingl�s)\\n\\t\\t\\tabordo del Laboratorio Cient�fico de Marte (MSL, por sus siglas en ingl�s). Este\\n\\t\\t\\tfichero da las magnitudes ambientales medidas por REMS, por lo que estas magnitudes \\n\\t\\t\\test�n influenciadas por el rover MSL (posici�n del rover, temperatura del\\n\\t\\t\\trover, orientaci�n del rover, sombras arrojadas por el rover, deposici�n de polvo sobre\\n\\t\\t\\tel rover, etc.)\\n\\n\\t\\t\\tREMS no est� tomando medidas continuamente y, adem�s, toma medidas a diferentes horas \\n\\t\\t\\tde un d�a para otro. Estos hechos tienen influencia en la variaci�n de los valores\\n\\t\\t\\tdados en este fichero para d�as sucesivos.\\n\\n\\t\\t\\tPor diferentes razones (mantenimiento del instrumento, calibraci�n del instrumento, degradaci�n\\n\\t\\t\\tdel instrumento, etc.) puede que alguna o todas las magnitudes de este fichero no est�n disponibles.\\n\\t\\t",
      "\n\t\t\tThe term sol is used to refer to the duration of a day on Mars. A sol is about 24 hours and 40 minutes. For Curiosity,\n\t\t\tsol 0 corresponds with its landing day on Mars.\n\t\t",
      "\n\t\t\tEl t�rmino sol hace referencia a la duraci�n de un d�a en Marte. Un sol dura aproximadamente 24 horas y 40 minutos.\n\t\t\tPara Curiosity, el sol 0 corresponde al d�a de su aterrizaje en Marte.\n\t\t",
      "\n\t\t\tOne day on Earth is about 24 hours, while a sol (Martian day) is about 24 hours and 40 minutes.\n\t\t\tSo one single sol starts during one Earth day and finishes during the next Earth day. This \n\t\t\tis the Earth day on current sol at midday.\n\t\t",
      "\n\t\t\tUn d�a en la Tierra dura aproximadamente 24 horas, mientras que un sol (d�a marciano) dura aproximadamente\n\t\t\t24 horas y 40 minutos. As� que un sol empieza durante un d�a terrestre, pero termina durante el siguiente.\n\t\t\tEl d�a terrestre dado es el que se corresponde con el mediod�a del sol actual.\n\t\t",
      "\n\t\t\t\tMars is farther from the Sun than Earth, it makes that Mars is colder than our planet.\n\t\t\t\tMoreover, Martian�s atmosphere, which is extremely tenuous, does not retain the heat; \n\t\t\t\thence the difference between day and night's temperatures is more pronounced than in our planet.\n\t\t\t",
      "\n\t\t\t\tMarte est� m�s lejos del Sol que la Tierra, por lo que Marte es m�s frio que nuestro planeta.\n\t\t\t\tAdem�s, lo atm�sfera marciana, que es muy tenue, no retiene bien el calor, \n\t\t\t\tpor lo que las diferencias de temperatura entre el d�a y la noche son m�s pronunciadas que\n\t\t\t\ten nuestro planeta.\n\t\t\t",
      "\n\t\t\t\tPressure is a measure of the total mass in a column of air above us. Because\n\t\t\t\tMartian�s atmosphere is extremely tenuous, pressure on Mars' surface is about\n\t\t\t\t160 times smaller than pressure on Earth. Average pressure on Martian surface \n\t\t\t\tis about 700 Pascals (100000 Pascals on Earth) Curiosity is into Gale crater,\n\t\t\t\twhich is about 5 kilometers (3 miles) depth. For this reason, pressure measured\n\t\t\t\tby REMS is usually higher than average pressure on the entire planet.\n\t\t\t",
      "\n\t\t\t\tLa presi�n mide la masa total en una columna de aire sobre nosotros. Debido a que\n\t\t\t\tla atm�sfera marciana es muy tenue, la presi�n en la superficie de Marte es unas\n\t\t\t\t160 veces m�s baja que en la Tierra. La presi�n media en la superficie de Marte \n\t\t\t\tes de unos 700 Pascales (en la Tierra es de 100000) El rover Curiosity est� en el \n\t\t\t\tinterior de un cr�ter, de unos 5 kil�metros (3 millas) de profundidad. Por esta\n\t\t\t\traz�n, la presi�n medida por REMS normalmente ser� mayor que la media en el planeta.\n\t\t\t",
      "\n\t\t\t\tMartian's atmosphere contains water vapor, and humidity measures the amount \n\t\t\t\tof water vapor present in the air. REMS records the relative humidity, \n\t\t\t\twhich is a measure of the amount of water vapor in the air compared \n\t\t\t\twith the amount of water vapor the air can hold at the temperature it happens\n\t\t\t\tto be when you measure it. Water on Mars is also present as water ice, \n\t\t\t\tfor instance, at Mars poles. Until today, however, proof of liquid water \n\t\t\t\tin present-day Mars remains elusive.\n\t\t\t",
      "\n\t\t\t\tLa atm�sfera marciana contiene vapor de agua y la humedad mide \n\t\t\t\tla cantidad de vapor de agua presente en el aire. REMS registra la humedad\n\t\t\t\trelativa, que es una medida de la cantidad de vapor de agua que \n\t\t\t\thay en el aire en comparaci�n con la m�xima cantidad de vapor de agua\n\t\t\t\tque puede contener a la misma temperatura. El agua en Marte est� presente \n\t\t\t\ttambi�n en forma de hielo, por ejemplo en los polos. Hasta la fecha, sin embargo, \n\t\t\t\tno se han encontrado pruebas de la existencia de agua l�quida en el presente marciano.\n\t\t\t",
      "\n\t\t\t\tNASA Viking landers and NASA Pathfinder rover showed that the average wind speed\n\t\t\t\ton their location was pretty weak: 1 to 4 meters/second (4 to 15 kilometers/hour - \n\t\t\t\t2.5 to 9 miles/hour). However, during a dust storm, winds can reach 30 meters/second \n\t\t\t\t(110 kilometers/hour - 68 miles/hour) or more.\n\t\t\t",
      "\n\t\t\t\tLas Vikings landers y el rover Pathfinder de la NASA mostraron que la velocidad media\n\t\t\t\tdel viento en su localizaci�n era bastante baja: de 1 a 4 metros/segundo (de 4 a \n\t\t\t\t15 kil�metros por hora - de 2.5 a 9 millas/hora) Sin embargo, durante una tormenta\n\t\t\t\tde polvo, la velocidad del viento en Marte puede alcanzar los 30 metros/segundo\n\t\t\t\t(110 kil�metros por hora - 68 millas/hora) o m�s.\n\t\t\t",
      "\n\t\t\t\tMartian's atmosphere is extremely tenuous, about 160 times thinner than Earth's,\n\t\t\t\tso heat from the Sun can easily escape. It makes that there are big differences\n\t\t\t\tbetween ground temperature and air temperature. Imagine you were on the Martian equator \n\t\t\t\tat noon, you would feel like summer at your feet, but winter in your head.\n\t\t\t",
      "\n\t\t\t\tLa atm�sfera marciana es muy tenue, unas 160 veces m�s delgada que la de la Tierra,\n\t\t\t\tlo que hace que el calor proveniente del Sol pueda escapar con facilidad. Esto hace\n\t\t\t\tque haya grandes diferencias entre la temperatura del suelo y la del aire. Imagina\n\t\t\t\tpor un momento que est�s en el ecuador marciano al mediod�a, sentir�as\n\t\t\t\ten tus pies una temperatura veraniega, sin embargo ser�a invierno para tu cabeza.\n\t\t\t",
      "\n\t\t\t\tLocal ultraviolet (UV) irradiance index is an indicator of the intensity of the ultraviolet\n\t\t\t\tradiation from the Sun at Curiosity location. UV radiation is a damaging agent for life. \n\t\t\t\tOn Earth, ozone layer prevents damaging ultraviolet light from reaching the surface, to \n\t\t\t\tthe benefit of both plants and animals. However, on Mars, due to the absence of ozone in the \n\t\t\t\tatmosphere, ultraviolet radiation reaches the Martian surface.\n\t\t\t",
      "\n\t\t\t\tEl �ndice de irradiaci�n ultravioleta local es un indicador de la intensidad de la radiaci�n\n\t\t\t\tultravioleta (UV) proveniente del Sol en la ubicaci�n del rover Curiosity. La radiaci�n UV es \n\t\t\t\tun agente da�ino para la vida. En la Tierra, la capa de ozono nos protege de ella. Sin embargo\n\t\t\t\ten Marte, debido a la ausencia de ozono en la atm�sfera, la radiaci�n ultravioleta alcanza\n\t\t\t\tla superficie.\n\t\t\t",
      "\n\t\t\t\tWeather on Mars is more extreme than Earth's. Mars is cooler and with\n\t\t\t\tbigger differences between day and night temperatures. Moreover, \n\t\t\t\tdust storms lash its surface. However, Mars' and Earth's climates have\n\t\t\t\timportant similarities, such as the polar ice caps or seasonal changes.\n\t\t\t\tAs on Earth, on Mars we can have sunny, cloudy or windy days, for example.\n\t\t\t",
      "\n\t\t\t\tEl clima en Marte es m�s extremo que en la Tierra. Marte es m�s frio y\n\t\t\t\tcon mayores diferencias entre las temperaturas del d�a y de la noche.\n\t\t\t\tAdem�s, fuertes tormentas de polvo azotan su superficie. Sin embargo,\n\t\t\t\texisten importantes similitudes entre el clima marciano y el terrestre,\n\t\t\t\tcomo la existencia de casquetes polares o los cambios de estaciones.\n\t\t\t\tComo ocurre en la Tierra, en Marte podemos tener d�as soleados, nublados\n\t\t\t\to ventosos, por ejemplo.\n\t\t\t",
      "\n\t\t\t\tA Martian year lasts about two Earth's year, which is the time\n\t\t\t\tMars takes to orbit the Sun. Solar longitude is an angle that\n\t\t\t\tgives the position of Mars on its orbit.\t\t\t\t  \n\t\t\t",
      "\n\t\t\t\tUn a�o marciano dura aproximadamente dos a�os terrestres, el tiempo\n\t\t\t\tque Marte necesita para dar una vuelta completa alrededor del Sol.\n\t\t\t\tLa longitud solar es un �ngulo que nos da la posici�n de Marte en su \n\t\t\t\t�rbita.\n\t\t\t",
      "\n\t\t\t\tA Martian year is divided in 12 months, as Earth's. However, Martian months are\n\t\t\t\tfrom 46 to 67 sols (Martian days) long. The longest one is the month 3 (67 sols),\n\t\t\t\tand the shortest one is the month 9 (46 sols) Martian months mark seasonal changes. \n\t\t\t\tIn the southern hemisphere (Curiosity rover location) the autumn starts in month 1; \n\t\t\t\tthe winter in month 4; the spring in month 7; and the summer in month 10.\n\t\t\t",
      "\n\t\t\t\tEl a�o marciano se divide en 12 meses, como en la Tierra. Sin embargo, los meses\n\t\t\t\ten Marte duran entre 46 y 67 soles (d�as marcianos). El m�s largo es el mes 3\n\t\t\t\t(67 soles) y el m�s corto es el mes 9 (46 soles). Los meses marcan los\n\t\t\t\tcambios de estaci�n. En el hemisferio sur (localizaci�n del rover Curiosity) el\n\t\t\t\toto�o comienza en el mes 1; el invierno, en el mes 4; la primavera, en el mes 7; y\n\t\t\t\tel verano, en el mes 10. \n\t\t\t\t\t\t\t\n\t\t\t",
      "\n\t\t\t\tThe duration of a Martian day (sol) is about 24 hours and 40 minutes. The duration\n\t\t\t\tof daylight varies along the Martian year, as on Earth.\n\t\t\t",
      "\n\t\t\t\tUn d�a marciano (sol) dura aproximadamente 24 horas y 40 minutos. La duraci�n\n\t\t\t\tdel d�a y de la noche var�a a lo largo del a�o, como ocurre en la Tierra.\n\t\t\t")
      .build();
  private static final Sol SOL1 = new Sol.SolBuilder("3341","2022-07-01","3520","257","Month 9",
      "-72","-11","875","Higher","--","--",
      "--", "Sunny","05:50","18:08","High",
      "-85","8").build();

  private static final Sol SOL2 = new Sol.SolBuilder("3344","2022-07-02","3521","258","Month 9",
      "-69","-14","882","Higher","--","--",
      "--", "Sunny","05:51","18:08","High",
      "High", "8").build();

  private static final List<Sol> SOLS = Arrays.asList(SOL1,SOL2);
  public static final Weather WEATHER_DATA = new Weather.WeatherBuilder(DESCRIPTIONS,SOLS).build();


  @Test
   void getMarsWeather() {

    when(weatherServiceMock.getWeatherMapping("weather","json",1.0f,"msl"))
        .thenReturn(WEATHER_DATA);
    Response response = nasaResource.getMarsWeather("weather","json",1.0f,"msl");
    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isEqualTo(WEATHER_DATA);
    assertThat(((Weather)response.getEntity()).getSoles()).hasSize(2);
  }

  @Test
   void getMarsWeatherThrowsException(){
    when(weatherServiceMock.getWeatherMapping("weather",null,1.0f,"msl"))
        .thenThrow(new RuntimeException());
    Response response = nasaResource.getMarsWeather("weather",null,1.0f,"msl");
    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(response.getEntity()).isNull();
  }
}