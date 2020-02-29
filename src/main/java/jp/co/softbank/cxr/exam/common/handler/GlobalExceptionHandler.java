package jp.co.softbank.cxr.exam.common.handler;

import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;

import jp.co.softbank.cxr.exam.application.payload.ErrorResponse;
import jp.co.softbank.cxr.exam.common.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 例外のハンドリングを行うクラス.
 *
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  /**
   * ApplicationException 例外を Json として返却する.
   *
   * @param e 発生した ApplicationException 例外
   * @return エラー詳細を格納したエラーメッセージ
   */
  @ExceptionHandler(ApplicationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {

    ErrorResponse errorResponse = new ErrorResponse(e.getErrorDetail().getMessage());
    if (e.getErrorDetail().equals(RECIPE_NOT_FOUND)) {
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
