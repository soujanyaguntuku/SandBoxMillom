package millom.sandbox.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import millom.sandbox.CustomException.DateFormatIncorrectException;

public class MessageServiceImpl implements MessageService {

  // format for ISO 8601
  private static final DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final LocalDate curiosityLandingDate = LocalDate.parse("2012-08-06",
      isoFormat);

  @Override
  public String getHello() {
    return "Hello World Jersey and HK2 ";
  }

  @Override
  public int convertCuriositySol(String date) throws DateFormatIncorrectException {
    try {
      LocalDate earthDate = LocalDate.parse(date, isoFormat);
      float diffInDays = ChronoUnit.DAYS.between(curiosityLandingDate, earthDate);
      return (int) Math.ceil(diffInDays * 86400 / 88775.245);
    } catch (DateTimeParseException e) {
      throw new DateFormatIncorrectException(String.format("Date is not in ISO 8601 format. %s", e.getMessage()));
    }
  }

  public String getTodayDate() {
    return isoFormat.format(LocalDateTime.now());
  }
}

