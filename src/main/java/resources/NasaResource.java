package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/nasaapi")
public class NasaResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(NasaResource.class);

  private final WeatherService weatherService;

  public NasaResource(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  public NasaResource() {
    this(new WeatherService());
  }

  @Path("/mars/weather/")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMarsWeather(@QueryParam("feed") String feed,
      @QueryParam("feedtype") String feedType,
      @QueryParam("version") float version, @QueryParam("category") String category) {
    try {
      LOGGER.info(
          String.format("Task-3: Success message from Nasa API -- %s class and  get method: %s()",
              Thread.currentThread().getStackTrace()[1].getClassName(),
              Thread.currentThread().getStackTrace()[1].getMethodName()));
      return Response.
          status(Response.Status.OK)
          .entity(weatherService.getWeatherMapping(feed, feedType, version, category))
          .build();
    } catch (InvalidWeatherException e) {
      LOGGER.info(String.format("Task-3: Exception occurred in -- %s class and  get method: %s()",
          Thread.currentThread().getStackTrace()[1].getClassName(),
          Thread.currentThread().getStackTrace()[1].getMethodName()));
      return Response.
          status(Status.INTERNAL_SERVER_ERROR)
          .entity(e.getMessage())
          .build();
    }
  }
}
