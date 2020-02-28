package jp.co.softbank.cxr.exam.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorDetail {

  @JsonProperty("error_code")
  private String errorCode;

  @JsonProperty("message")
  private String message;


}
