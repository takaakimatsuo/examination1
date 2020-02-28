package jp.co.softbank.cxr.exam.common;

import lombok.Builder;


/**
 * アプリケーション例外を表現するクラス.
 *
 */

@Builder
public class ApplicationException extends RuntimeException {

  private final ErrorDetail errorDetail;

  public ApplicationException(ErrorDetail errorDetail) {
    super();
    this.errorDetail = errorDetail;
  }


}