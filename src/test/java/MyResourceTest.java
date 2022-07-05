
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import millom.sandbox.service.MessageService;
import millom.sandbox.service.MessageServiceImpl;
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
  private MessageService msgServiceImpl;

  @InjectMocks
  MyResource myResource = new MyResource();

  @BeforeEach
  void setUp() throws Exception{
    MockitoAnnotations.initMocks(this);
    msgServiceImpl = new MessageServiceImpl();
  }

  @Test
  @DisplayName("CONVERT EARTH DATE TO CURIOSITY SOL DATE")
  void testConvertCuriositySol(){
//    Response response = Response.
//        status(Response.Status.OK)
//        .entity(msgServiceImpl.convertCuriositySol("2022-06-15"))
//        .build();
    when(messageServiceMock.convertCuriositySol("2022-06-15")).thenReturn(3504);
    Response response = myResource.convertCuriositySol("2022-06-15");
    assertEquals(response.getStatus(), 200);
    assertEquals(response.getEntity(), 3504 );
  }

  @Test
  void TestConvertCuriositySolIfDateNull() {
    String date = msgServiceImpl.getTodayDate();
    int actual = msgServiceImpl.convertCuriositySol(date);

    when(messageServiceMock.getTodayDate()).thenReturn(date);
    when(messageServiceMock.convertCuriositySol(date)).thenReturn(actual);

    Response response = myResource.convertCuriositySol(null);
    assertEquals(response.getStatus(), 200);
    assertEquals(response.getEntity(), actual);
  }
  @Test
  void TestConvertCuriositySolThrowsException() {
    String date ="2022-";
    when(messageServiceMock.convertCuriositySol(date)).thenThrow(new RuntimeException());
    Response response = myResource.convertCuriositySol(date);
    assertEquals(response.getStatus(), 400);
    assertEquals(response.getEntity(), null );
  }
  private static URI getBaseURI(String path) {
    return UriBuilder.fromUri("http://localhost:8080/"+path).build();
  }
}