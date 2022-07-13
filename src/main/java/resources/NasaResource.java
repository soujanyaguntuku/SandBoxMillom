package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import millom.sandbox.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/nasaapi")
public class NasaResource {
  private static final Logger LOGGER
      = (Logger) LoggerFactory.getLogger(NasaResource.class);

  WeatherService weatherService = new WeatherService();

  @Path("/mars/weather/")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMarsWeather(@QueryParam("feed") String feed,@QueryParam("feedtype") String feedType,
      @QueryParam("version") float version,@QueryParam("category") String category){
    // get request
    //curl http://localhost:8080/nasaapi/mars/weather/\?feed\=weather\&feedtype\=json\&version\=1.0f\&category\=msl

    try {
      LOGGER.info("Task-3: Successful response from Nasa API --"+ NasaResource.class.getSimpleName() + " class, get method: getMarsWeather()");
      return Response.
          status(Response.Status.OK)
          .entity(weatherService.getWeatherMapping(feed,feedType,version,category))
          .build();
    } catch (Exception e) {
      LOGGER.info("Task-3: Exception occurred in  "+ NasaResource.class.getSimpleName() + " class, get method: getMarsWeather() Bad request sent");
      return Response.
          status(Status.BAD_REQUEST)
          .entity(e.getMessage())
          .build();
    }
  }
}
