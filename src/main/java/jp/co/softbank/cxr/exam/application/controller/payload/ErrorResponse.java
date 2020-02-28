/*
 *  (c)Copyright Since 2019, SOFTBANK Corp. All rights reserved.
 */

package jp.co.softbank.cxr.exam.application.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * エラーレスポンスのクラス.
 *
 */
@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

  @JsonProperty("message")
  private String message;

  @JsonProperty("error_code")
  private String errorCode;

}
