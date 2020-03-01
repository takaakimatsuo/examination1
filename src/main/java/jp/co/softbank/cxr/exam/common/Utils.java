package jp.co.softbank.cxr.exam.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static jp.co.softbank.cxr.exam.common.ErrorDetailsRequired.INVALID_RECIPE;

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

  /**
   * 文字列が数字に変換可能かチェック.
   *
   * @param cost レシピのコスト
   */
  public static void resolveCost(String cost) {
    try {
      Integer.parseInt(cost);
    } catch (NumberFormatException e) {
      throw new InvalidUserInputException(INVALID_RECIPE);
    }
  }


}
