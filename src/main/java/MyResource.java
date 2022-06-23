import static java.lang.Math.ceil;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import millom.sandbox.Service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api")
public class MyResource {
  private static final Logger logger
      = (Logger) LoggerFactory.getLogger(MainApp.class);

  @Inject
  private MessageService msgService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    //Logs every request with SLF4J.
    logger.info("Example log from "+ MyResource.class.getSimpleName() + " class, get method: hello()");
    return "hello";
  }

  @Path("/healthz")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHelloHk2() {
    //Logs request with SLF4J.
    logger.info("Example log from "+ MyResource.class.getSimpleName() +" class, get method : gethelloHk2()");
    return msgService.getHello();
  }

  // Earth date to Curiosity sol conversion end point
  @Path("/sol")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public int convertCuriositySol(@QueryParam("date") String date) {
    logger.info("Example log from "+ MyResource.class.getSimpleName() +" class, get method : convertCuriositySol()");
    return msgService.convertCuriositySol(date);
  }
}
