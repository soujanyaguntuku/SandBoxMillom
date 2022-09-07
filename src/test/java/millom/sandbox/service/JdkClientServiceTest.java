package millom.sandbox.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import java.io.IOException;
import millom.sandbox.CustomException.JdkException;
import millom.sandbox.Utility.JsonUtility;
import org.glassfish.jersey.client.JerseyInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class JdkClientServiceTest {

  private JerseyInvocation.Builder builder;
  @Mock
  private Client client;
  @InjectMocks
  private JdkClientService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    WebTarget target = Mockito.mock(WebTarget.class);
    builder = Mockito.mock(JerseyInvocation.Builder.class);
    when(target.request(anyString())).thenReturn(builder);
    when(client.target(anyString())).thenReturn(target);
  }

  @Test
  void getJdkJsonReleaseNames() throws JdkException, IOException {
    when(builder.get(String.class)).thenReturn(JsonUtility.getSampleResponse("JdkJsonResources/releaseNamesArmPage0.json"));
    String releaseNamesAsString = service.getJdkJsonReleaseNames("arm",0);
    assertThat(releaseNamesAsString).isNotNull();
    assertThat(JsonUtility.isValidJson(releaseNamesAsString)).isTrue();
    assertThat(releaseNamesAsString).contains("releases");
  }
  @Test
  void getJdkJsonReleaseNamesIncorrectURI() throws IOException, JdkException {
    when(builder.get(String.class)).thenReturn("{\"releases\":[]}");
    String weatherAsString = service.getJdkJsonReleaseNames("arm",0);
    assertThat(weatherAsString).isNotNull().isEqualTo("{\"releases\":[]}");
    assertThat(weatherAsString).isNotEqualTo(JsonUtility.getSampleResponse("JdkJsonResources/releaseNamesArmPage0.json"));
  }

  @Test
  void getApiQueryParams() {
    assertThat(service.getApiQueryParams("arm", 1)).isNotNull();
    assertThat(service.getApiQueryParams("arm", 1)).
        isEqualTo("?architecture=arm&heap_size=normal&image_type=jdk&page=1&page_size=10&project=jdk&release_type=ga&sort_method=DEFAULT&sort_order=DESC&vendor=adoptopenjdk");
  }

  @Test
  void getApiQueryParamsInvalidString() {
    assertThat(service.getApiQueryParams("", 0)).
        isNotEqualTo("?architecture=arm&heap_size=normal&image_type=jdk&page=0&page_size=10&project=jdk&release_type=ga&sort_method=DEFAULT&sort_order=DESC&vendor=adoptopenjdk");
  }
}