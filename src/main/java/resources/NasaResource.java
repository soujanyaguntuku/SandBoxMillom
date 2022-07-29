package resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import millom.sandbox.CustomException.InvalidWeatherException;
import millom.sandbox.pojos.Sol;
import millom.sandbox.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/nasaapi")
public class NasaResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(NasaResource.class);

  @Inject
  private WeatherService weatherService;

  @Path("/mars/weather/")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMarsWeather(
      @QueryParam("feed") String feed,
      @QueryParam("feedtype") String feedType,
      @QueryParam("version") float version,
      @QueryParam("category") String category) {

    Response errorResponse = validate(feed, feedType, version, category);
    if (errorResponse != null) {
      return errorResponse;
    }
    try {
      LOGGER.info(
          String.format("Task-3: Success message from Nasa API -- %s class and  get method: %s()",
              Thread.currentThread().getStackTrace()[1].getClassName(),
              Thread.currentThread().getStackTrace()[1].getMethodName()));
      return Response.
          status(Status.OK)
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

  @Path("/earthdate/marsweather/")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMarsWeatherForEarthDate(
      @QueryParam("feed") String feed,
      @QueryParam("feedtype") String feedType,
      @QueryParam("version") float version,
      @QueryParam("category") String category,
      @QueryParam("date") String date) {

    Response errorResponse = validate(feed, feedType, version, category);
    if (errorResponse != null) {
      return errorResponse;
    }
    if (date == null || date.length() == 0 || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      return getErrorResponse("date");
    }
    try {
      LOGGER.info(
          String.format("Task-4: Success message from Nasa API -- %s class and  get method: %s()",
              Thread.currentThread().getStackTrace()[1].getClassName(),
              Thread.currentThread().getStackTrace()[1].getMethodName()));
      Sol sol = weatherService.getMarsWeatherForDate(feed, feedType, version, category, date);
      if (sol == null) {
        return Response.
            status(Status.BAD_REQUEST)
            .entity("The given date doesn't have any information in the mars weather report.")
            .build();
      }
      return Response.
          status(Response.Status.OK)
          .entity(sol)
          .build();
    } catch (InvalidWeatherException e) {
      LOGGER.info(String.format("Task-4: Exception occurred in -- %s class and  get method: %s()",
          Thread.currentThread().getStackTrace()[1].getClassName(),
          Thread.currentThread().getStackTrace()[1].getMethodName()));
      return Response.
          status(Status.BAD_REQUEST)
          .entity(e.getMessage())
          .build();
    }
  }

  private Response validate(String feed, String feedType, float version, String category) {

    if (feed == null || !feed.equals("weather")) {
      return getErrorResponse("feed");
    }
    if (feedType == null || !feedType.equals("json")) {
      return getErrorResponse("feedType");
    }
    String versionToString = String.valueOf(version);

    if (!versionToString.matches("^(\\d)*(?:\\.\\d)?$")) {
      return getErrorResponse("version");
    }
    if (category == null || category.length() < 2 || category.length() > 5) {
      return getErrorResponse("category");
    }
    return null;
  }

  private Response getErrorResponse(String inputField) {
    return Response.
        status(Status.INTERNAL_SERVER_ERROR)
        .entity(String.format("The input values are not valid for %s.", inputField))
        .build();
  }
}
