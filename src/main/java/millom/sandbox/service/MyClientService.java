package millom.sandbox.service;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

public class MyClientService {
//  private static final String REST_URI
//      = "https://mars.nasa.gov/rss/api/?feed=weather&feedtype=json&ver=1.0&category=msl";

  private static final String REST_BASE_URI
      = "https://mars.nasa.gov/rss/api/";

  private static final String API_QUERY_PARAMS = "?feed=%s&feedtype=%s&ver=%.1f&category=%s";
  private Client client;

  public MyClientService(Client client) {
    this.client = client;
  }

  public MyClientService() {
    this.client = ClientBuilder.newClient();
  }

  public String getApiQueryParams(String feed, String feedType, float version, String category) {
    return String.format(API_QUERY_PARAMS, feed, feedType, version, category);
  }

  public String getJsonWeather(String feed, String feedType, float version, String category) {
    try {
      return client
           .target(REST_BASE_URI + this.getApiQueryParams(feed,  feedType,  version,  category))
           .request(MediaType.APPLICATION_JSON)
           .get(String.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
