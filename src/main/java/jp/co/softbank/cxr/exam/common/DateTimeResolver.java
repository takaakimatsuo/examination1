/*
 * (c)Copyright Since 2019, SOFTBANK Corp. All rights reserved.
 *
 */

package jp.co.softbank.cxr.exam.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 現在の日時を取得して返却するリゾルバ.
 */
@Component
public class DateTimeResolver {

  /**
   * 現在の日時を返す.
   *
   * @return 現在の日時
   */
  public LocalDateTime getCurrentTime() {
    return LocalDateTime.now();
  }
}
