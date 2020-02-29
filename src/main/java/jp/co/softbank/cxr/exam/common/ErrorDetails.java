package jp.co.softbank.cxr.exam.common;

public class ErrorDetails {

  /**
   * 指定されたレシピ ID が不正な場合.
   *
   * */
  public static final ErrorDetail RECIPE_NOT_FOUND
      = ErrorDetail.builder()
                   .message("No Recipe found")
                   .build();
}
