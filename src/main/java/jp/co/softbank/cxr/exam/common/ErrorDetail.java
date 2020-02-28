package jp.co.softbank.cxr.exam.common;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorDetail {

  private String errorCode;

  private String message;


}
