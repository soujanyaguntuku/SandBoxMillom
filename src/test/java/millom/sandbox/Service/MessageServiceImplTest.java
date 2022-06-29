package millom.sandbox.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

class MessageServiceImplTest {
  MessageServiceImpl messageServiceImpl;

  @BeforeEach
  void setUp() {
    messageServiceImpl = new MessageServiceImpl();
  }

  @Test
  void TestConvertCuriositySol() {
    assertEquals(3504, messageServiceImpl.convertCuriositySol("2022-06-15"));
  }

  @Test
  void convertCuriositySolThrowsExceptionIfDateInvalidFormat() {
    String date = "20-06-15";
    assertThrows(RuntimeException.class, () -> messageServiceImpl.convertCuriositySol(date));
  }
}