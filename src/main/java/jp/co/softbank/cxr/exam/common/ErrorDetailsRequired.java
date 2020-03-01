package jp.co.softbank.cxr.exam.common;

public class ErrorDetailsRequired {


  /**
   * 不正なレシピを登録しようとした場合.
   *
   * */
  public static final ErrorDetailRequired INVALID_RECIPE
      = ErrorDetailRequired.builder()
                   .message("Recipe creation failed!")
                   .required("title, making_time, serves, ingredients, cost")
                   .build();
}
