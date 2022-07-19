import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.core.Response;
import millom.sandbox.CustomException.DateFormatIncorrectException;
import millom.sandbox.service.MessageService;
import millom.sandbox.service.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MyResourceTest {

  @Mock
  private MessageService messageServiceMock;
  private MessageService msgServiceImpl;

  @InjectMocks
  MyResource myResource;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    msgServiceImpl = new MessageServiceImpl();
  }

  @Test
  @DisplayName("CONVERT EARTH DATE TO CURIOSITY SOL DATE")
  void testConvertCuriositySol() throws DateFormatIncorrectException {
    when(messageServiceMock.convertCuriositySol("2022-06-15")).thenReturn(3504);
    Response response = myResource.convertCuriositySol("2022-06-15");
    assertEquals(response.getStatus(), 200);
    assertEquals(response.getEntity(), 3504);

  }

  @Test
  void TestConvertCuriositySolIfDateNull() throws DateFormatIncorrectException {
    String date = msgServiceImpl.getTodayDate();
    int actual = msgServiceImpl.convertCuriositySol(date);

    when(messageServiceMock.getTodayDate()).thenReturn(date);
    when(messageServiceMock.convertCuriositySol(date)).thenReturn(actual);
    Response response = myResource.convertCuriositySol(null);
    assertEquals(response.getStatus(), 200);
    assertEquals(response.getEntity(), actual);
  }

  @Test
  void TestConvertCuriositySolThrowsException() throws DateFormatIncorrectException {
    String date = "2022-";
    when(messageServiceMock.convertCuriositySol(date)).thenThrow(new RuntimeException());
    Response response = myResource.convertCuriositySol(date);
    assertEquals(response.getStatus(), 400);
    assertNull(response.getEntity());
  }
}