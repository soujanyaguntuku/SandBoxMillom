package millom.sandbox.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import millom.sandbox.CustomException.DateFormatIncorrectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageServiceImplTest {

  MessageServiceImpl messageServiceImpl;

  @BeforeEach
  void setUp() {
    messageServiceImpl = new MessageServiceImpl();
  }

  @Test
  void convertCuriositySolTest1() throws DateFormatIncorrectException {
    assertEquals(3504, messageServiceImpl.convertCuriositySol("2022-06-15"));
  }

  @Test
  void ConvertCuriositySolTest2() throws DateFormatIncorrectException {
    assertEquals(3516, messageServiceImpl.convertCuriositySol("2022-06-27"));
  }

  @Test
  void convertCuriositySolThrowsExceptionIfDateInvalidFormat() {
    assertThrows(DateFormatIncorrectException.class,
        () -> messageServiceImpl.convertCuriositySol("20-06-27"));
  }
}