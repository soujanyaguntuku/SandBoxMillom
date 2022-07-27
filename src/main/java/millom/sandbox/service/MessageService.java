package millom.sandbox.service;


import millom.sandbox.CustomException.DateFormatIncorrectException;

public interface MessageService {
  String getHello();
  int convertCuriositySol(String date) throws DateFormatIncorrectException;
  String getTodayDate();
}

