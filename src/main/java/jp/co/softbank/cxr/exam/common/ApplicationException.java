package jp.co.softbank.cxr.exam.common;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.NonFinal;


/**
 * アプリケーション例外を表現するクラス.
 *
 */

@Getter
@Builder
@NonFinal
public class ApplicationException extends RuntimeException {

  private final ErrorDetail errorDetail;

  public ApplicationException(ErrorDetail errorDetail) {
    super();
    this.errorDetail = errorDetail;
  }


}