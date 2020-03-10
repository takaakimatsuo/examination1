package jp.co.softbank.cxr.exam.common;

import static jp.co.softbank.cxr.exam.common.ErrorDetailsRequired.INVALID_RECIPE;

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
    return Timestamp.valueOf(
      LocalDateTime.parse(
        dateTime,
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      )
    );
  }

  /**
   * 文字列が数字に変換可能かチェック.
   *
   * @param string 任意の文字列
   */
  public static void resolveCost(String string) {
    try {
      Integer.parseInt(string);
    } catch (NumberFormatException e) {
      throw new InvalidUserInputException(INVALID_RECIPE);
    }
  }


}
