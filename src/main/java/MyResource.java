import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import millom.sandbox.Service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/get")
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
  public String gethelloHk2() {

    //Logs request with SLF4J.
    logger.info("Example log from "+ MyResource.class.getSimpleName() +" class, get method : gethelloHk2()");
    return msgService.getHello();
  }

}
