import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import millom.sandbox.service.MessageService;
import millom.sandbox.service.MessageServiceImpl;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import resources.NasaResource;

public class MainApp {

  public static final String BASE_URI = "http://localhost:8080/";

  // Starts Grizzly HTTP server
  public static HttpServer startServer() {

    // scan packages
    final ResourceConfig config = new ResourceConfig();

    config.register(MyResource.class);
    config.register(NasaResource.class);
   // config.register(AutoScanFeature.class);

    config.register(new AbstractBinder(){
      @Override
      protected void configure() {
        // map this service to this contract
        bind(MessageServiceImpl.class).to(MessageService.class);
      }
    });

    final HttpServer httpServer =
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);

    return httpServer;

  }
  public static void main(String[] args) {

    try {

      final HttpServer httpServer = startServer();

      // add jvm shutdown hook
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
          System.out.println("Shutting down the application...");

          httpServer.shutdownNow();

          System.out.println("Done, exit.");
        } catch (Exception e) {
          Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
        }
      }));

      System.out.println(String.format("Application started.%nStop the application using CTRL+C"));

      // block and wait shut down signal, like CTRL+C
      Thread.currentThread().join();

    } catch (InterruptedException ex) {
      Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}
