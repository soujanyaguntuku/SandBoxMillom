package resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import millom.sandbox.CustomException.DateFormatIncorrectException;
import millom.sandbox.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api")
public class MyResource {
  private static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);

  @Inject
  private MessageService msgService;
  @Path("/hello")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    //Logs every request with SLF4J.
    LOGGER.info(String.format("Success message from %s class and  get method: %s()",
        Thread.currentThread().getStackTrace()[1].getClassName(),
        Thread.currentThread().getStackTrace()[1].getMethodName()));
    return "hello";
  }

  @Path("/healthz")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHelloHk2() {
    //Logs request with SLF4J.
    LOGGER.info(String.format("Success message from %s class and  get method: %s()",
        Thread.currentThread().getStackTrace()[1].getClassName(),
        Thread.currentThread().getStackTrace()[1].getMethodName()));
    return msgService.getHello();
  }

  // Earth date to Curiosity sol conversion end point
  @Path("/sol")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response convertCuriositySol(@QueryParam("date") String date) {
    if(date == null){
      // assign current date
      date = msgService.getTodayDate();
    }

    try {
      LOGGER.info(String.format("Success message from %s class and  get method: %s()",
          Thread.currentThread().getStackTrace()[1].getClassName(),
          Thread.currentThread().getStackTrace()[1].getMethodName()));
      return Response.
          status(Response.Status.OK)
          .entity(msgService.convertCuriositySol(date))
          .build();
    } catch (DateFormatIncorrectException e) {
      LOGGER.info(String.format("Exception occurred from %s class and  get method: %s()",
          Thread.currentThread().getStackTrace()[1].getClassName(),
          Thread.currentThread().getStackTrace()[1].getMethodName()));
      return Response
          .status(Status.BAD_REQUEST)
          .entity(e.getMessage())
          .build();
    }
  }
}
