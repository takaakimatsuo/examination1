package jp.co.softbank.cxr.exam.common;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.NonFinal;


/**
 * ユーザ入力例外を表現するクラス.
 *
 */
@Getter
@NonFinal
@Builder
public class InvalidUserInputException extends RuntimeException {

  private final ErrorDetailRequired errorDetail;

  public InvalidUserInputException(ErrorDetailRequired errorDetail) {
    super();
    this.errorDetail = errorDetail;
  }
}
