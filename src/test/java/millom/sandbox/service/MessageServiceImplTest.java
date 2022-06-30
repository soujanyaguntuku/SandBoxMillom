package millom.sandbox.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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