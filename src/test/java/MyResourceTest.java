import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDateTime;
import millom.sandbox.Service.MessageService;
import millom.sandbox.Service.MessageServiceImpl;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class MyResourceTest {
  @Mock
  private MessageService messageServiceMock;

  @InjectMocks
  MyResource myResource = new MyResource();

  @BeforeEach
  void setUp() throws Exception{
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("CONVERT EARTH DATE TO CURIOSITY SOL DATE")
  void testConvertCuriositySol(){
    when(messageServiceMock.convertCuriositySol("2022-06-15")).thenReturn(3504);
    assertEquals(3504, myResource.convertCuriositySol("2022-06-15"));
    verify(messageServiceMock).convertCuriositySol("2022-06-15");
  }

  @Test
  void TestConvertCuriositySolIfDateNull() {
    // when(messageServiceMock.convertCuriositySol("2022-06-15")).thenReturn(3504);
    MessageService msgService = new MessageServiceImpl();
    String date = msgService.getTodayDate();
    int actual = msgService.convertCuriositySol(date);

    when(messageServiceMock.getTodayDate()).thenReturn(date);
    when(messageServiceMock.convertCuriositySol(date)).thenReturn(actual);

    assertEquals(actual, myResource.convertCuriositySol(null));

  }
  private static URI getBaseURI(String path) {
    return UriBuilder.fromUri("http://localhost:8080/"+path).build();
  }
}