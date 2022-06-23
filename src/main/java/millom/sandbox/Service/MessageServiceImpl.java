package millom.sandbox.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class MessageServiceImpl implements MessageService {

  private static final LocalDate curiosityLandingDate = LocalDate.parse("2012-08-06",
      DateTimeFormatter.ofPattern("yyyy-MM-dd"));

  @Override
  public String getHello() {
    return "Hello World Jersey and HK2 ";
  }

  @Override
  public int convertCuriositySol(String date) {
    float diff_In_Days ;
    LocalDate earthDate;
    // format for ISO 8601
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    if(date == null){
      // assign current date
      date = format.format(LocalDateTime.now());
    }
    try {
      earthDate = LocalDate.parse(date, format);
      diff_In_Days = ChronoUnit.DAYS.between(curiosityLandingDate, earthDate);

    } catch (DateTimeParseException e) {
      throw new RuntimeException(e);
    }
    return (int) Math.round(diff_In_Days * 86400 / 88775.245);
  }

}

