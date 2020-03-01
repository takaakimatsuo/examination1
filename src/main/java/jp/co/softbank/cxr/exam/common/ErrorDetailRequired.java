package jp.co.softbank.cxr.exam.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class ErrorDetailRequired {

  @JsonProperty("message")
  private String message;

  @JsonProperty("required")
  private String required;

}
