package jp.co.softbank.cxr.exam.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

  /**
   * 文字列を Timestamp 型に変換.
   *
   * @param dateTime 日時を表す文字列
   * @return Timestamp型のに日時
   */
  public static Timestamp toSqlTimestamp(String dateTime) {
    return Timestamp.valueOf(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }

}
