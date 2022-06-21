import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import millom.sandbox.Service.MessageService;

@Path("/hello")
public class MyResource {

  @Inject
  private MessageService msgService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String helloAzure() {
    return "hello";
  }

  @Path("/hk2/aws")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String helloAws() {
    return msgService.getHello();
  }

  @Path("/hk2/try")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String helloTry() {
    return " Hello fisrt try";
  }

}
