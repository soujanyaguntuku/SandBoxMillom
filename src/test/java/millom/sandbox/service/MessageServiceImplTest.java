package millom.sandbox.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import millom.sandbox.CustomException.DateFormatIncorrectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MessageServiceImplTest {

  MessageServiceImpl messageServiceImpl;

  @BeforeEach
  void setUp() {
    messageServiceImpl = new MessageServiceImpl();
  }

  @ParameterizedTest
  @CsvSource(value = {"2022-06-15:3504", "2022-06-27:3516"}, delimiter = ':')
  void ConvertCuriositySolTest(String input, int expected) throws DateFormatIncorrectException {
    assertEquals(expected, messageServiceImpl.convertCuriositySol(input));
  }

  @Test
  void convertCuriositySolThrowsExceptionIfDateInvalidFormat() {
    assertThrows(DateFormatIncorrectException.class,
        () -> messageServiceImpl.convertCuriositySol("20-06-27"));
  }
}